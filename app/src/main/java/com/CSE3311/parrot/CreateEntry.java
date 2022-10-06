package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Calendar;

public class CreateEntry extends AppCompatActivity {
    private ParseUser user;
    private ParseObject subscriptions;

    private EditText subscriptionName,description,startDate,endDate,cost,paymentType,subscriptionType,notificationDate;
    private DatePickerDialog startDateDialog, endDateDialog, notificationDateDialog;
    private Button uploadImage, createEntry, cancelEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        user = ParseUser.getCurrentUser();
        subscriptions = new ParseObject("Subscriptions");

        // instantiate all the objects
        subscriptionName =(EditText) findViewById(R.id.create_entry_edit_text_subscription_name);
        description = (EditText) findViewById(R.id.create_entry_edit_text_description);
        startDate = (EditText) findViewById(R.id.create_entry_edit_text_start_date);
        endDate = (EditText) findViewById(R.id.create_entry_edit_text_end_date);
        cost = (EditText) findViewById(R.id.create_entry_edit_text_cost);
        paymentType = (EditText) findViewById(R.id.create_entry_edit_text_payment_type);
        subscriptionType = (EditText) findViewById(R.id.create_entry_edit_text_subscription_type);
        notificationDate = (EditText) findViewById(R.id.create_entry_edit_text_notification_date);
        uploadImage = (Button) findViewById(R.id.create_entry_button_upload_image);
        createEntry = (Button) findViewById(R.id.create_entry_submit_submission);
        cancelEntry = (Button) findViewById(R.id.create_entry_cancel_submission);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                startDateDialog = new DatePickerDialog(CreateEntry.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                startDateDialog.show();
            }
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
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                endDateDialog.show();
            }
        });

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
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                notificationDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                notificationDateDialog.show();
            }
        });

        createEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateEntry.this, MainActivity.class));
            }
        });


    }
}
