package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.sql.Time;
import java.util.Calendar;

public class CreateEntry extends AppCompatActivity {
    private ParseUser user;
    private User userInfo;

    private Spinner category;
    private EditText categoryName;
    private EditText description;
    private EditText startDate;
    private EditText endDate;
    private EditText cost;
    private EditText paymentType;
    private Spinner subscriptionType;
    private EditText notificationDate;

    private DatePickerDialog startDateDialog, endDateDialog, notificationDateDialog;
    private TimePickerDialog notificationTimeDialog;

    private Button uploadImage, createEntry, cancelEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        Bundle extra = getIntent().getExtras();

        user = ParseUser.getCurrentUser();
        userInfo = (User) extra.get("userInfo");

        // instantiate all the objects
        // spinner default value is Subscription
        category = (Spinner) findViewById(R.id.create_entry_edit_text_category);
        categoryName = (EditText) findViewById(R.id.create_entry_edit_text_category_name);
        description = (EditText) findViewById(R.id.create_entry_edit_text_description);
        startDate = (EditText) findViewById(R.id.create_entry_edit_text_start_date);
        endDate = (EditText) findViewById(R.id.create_entry_edit_text_end_date);
        cost = (EditText) findViewById(R.id.create_entry_edit_text_cost);
        paymentType = (EditText) findViewById(R.id.create_entry_edit_text_payment_type);
        subscriptionType = (Spinner) findViewById(R.id.create_entry_edit_text_subscription_type);
        notificationDate = (EditText) findViewById(R.id.create_entry_edit_text_notification_date);
        uploadImage = (Button) findViewById(R.id.create_entry_button_upload_image);
        createEntry = (Button) findViewById(R.id.create_entry_submit_submission);
        cancelEntry = (Button) findViewById(R.id.create_entry_cancel_submission);

        String[] categories = {"Subscription", "Bills", "Investments", "Income"};
        ArrayAdapter<String> selectCategories = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        selectCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(selectCategories);
        category.setSelection(0);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Income")) {
                    categoryName.setHint("Income Source");

                    endDate.setVisibility(View.GONE);
                    startDate.setHint("Pay Date");
                    startDate.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                    cost.setHint("Payment Amount");
                    paymentType.setVisibility(View.GONE);
                    uploadImage.setVisibility(View.GONE);
                } else if (parent.getItemAtPosition(position).equals("Subscription")) {
                    categoryName.setHint("Subscription Name");

                    endDate.setVisibility(View.VISIBLE);
                    startDate.setHint("Start Date");
                    startDate.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

                    cost.setHint("Cost");
                    paymentType.setVisibility(View.VISIBLE);
                    uploadImage.setVisibility(View.VISIBLE);
                } else if (parent.getItemAtPosition(position).equals("Investments")) {
                    categoryName.setHint("Investments/Stocks Lists");

                    endDate.setVisibility(View.GONE);
                    startDate.setHint("Buying Date");
                    startDate.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                    cost.setHint("Investment Amount");
                    paymentType.setVisibility(View.GONE);
                    uploadImage.setVisibility(View.GONE);
                } else if (parent.getItemAtPosition(position).equals("Bills")) {
                    categoryName.setHint("Bills");

                    endDate.setVisibility(View.VISIBLE);
                    startDate.setHint("Start Date");
                    startDate.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

                    cost.setHint("Cost");
                    paymentType.setVisibility(View.VISIBLE);
                    uploadImage.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        startDate.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            startDateDialog = new DatePickerDialog(CreateEntry.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        }
                    }, year, month, day);
            startDateDialog.show();
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                endDateDialog = new DatePickerDialog(CreateEntry.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                endDateDialog.show();
            }
        });

        String[] notificationType = {"Daily", "Weekly", "Bi-Weekly", "Monthly"};
        ArrayAdapter<String> selectNotificationType = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notificationType);
        selectNotificationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subscriptionType.setAdapter(selectNotificationType);
        subscriptionType.setSelection(0);
        subscriptionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notificationDate.setHint("Set a " + parent.getItemAtPosition((position)) + " Notification Date");
                if (parent.getItemAtPosition(position).equals("Daily")) {
                    notificationDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar cldr = Calendar.getInstance();
                            int hour = cldr.get(Calendar.HOUR_OF_DAY);
                            int minute = cldr.get(Calendar.MINUTE);
                            // date picker dialog
                            notificationTimeDialog = new TimePickerDialog(CreateEntry.this,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay,
                                                              int minute) {
                                            notificationDate.setText(hourOfDay + ":" + minute);
                                        }
                                    }, hour, minute, false);

                            notificationTimeDialog.show();
                        }
                    });

                } else {
                    notificationDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Calendar cldr = Calendar.getInstance();
                            int day = cldr.get(Calendar.DAY_OF_MONTH);
                            int month = cldr.get(Calendar.MONTH);
                            int year = cldr.get(Calendar.YEAR);
                            // date picker dialog
                            notificationDateDialog = new DatePickerDialog(CreateEntry.this,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                            notificationDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                        }
                                    }, year, month, day);

                            if (subscriptionType.getSelectedItem().equals("Weekly")) {
                                notificationDateDialog.setTitle("Please Select the day for a reminder");
                                notificationDateDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                                notificationDateDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() + 518400000);
                            } else if (subscriptionType.getSelectedItem().equals("Bi-Weekly")) {
                                notificationDateDialog.setTitle("Please Select the date for a reminder");
                                notificationDateDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                                notificationDateDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() + 1123200000);
                            } else if (subscriptionType.getSelectedItem().equals("Monthly")) {
                                notificationDateDialog.setTitle("Please Select the date for a reminder");
                            }
                            notificationDateDialog.show();
                        }
                    });
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        createEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseObject.registerSubclass(User.class);
                if (category.getSelectedItem().equals("Subscription")) {
                    Expense subscription = new Expense();
                    subscription.setCategoryType("Subscription");
                    subscription.setCategoryName(categoryName.getText().toString());
                    subscription.setDescription(description.getText().toString());
                    subscription.setStartDate(startDate.getText().toString());
                    subscription.setEndDate(endDate.getText().toString());
                    subscription.setCost(cost.getText().toString());
                    subscription.setPaymentType(paymentType.getText().toString());
                    subscription.setNotificationType(subscriptionType.getSelectedItem().toString());
                    subscription.setNotificationDate(notificationDate.getText().toString());
                    userInfo.addExpense(subscription);
                }


                startActivity(new Intent(CreateEntry.this, MainActivity.class));
            }
        });


    }
}
