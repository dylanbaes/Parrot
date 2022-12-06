package com.CSE3311.parrot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.Income;
import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ViewIncome extends AppCompatActivity {

    private TextView incomeName; // part-time, full-time ...
    private TextView description;
    private TextView paymentAmount;
    private TextView paymentType; // biweekly or monthly
    private TextView paymentDate;
    private TextView notificationDate;
    private ArrayList<Income> income;
    private Button delete;
    private AlertDialog.Builder editField;
    private EditText editFieldValue;
    User userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_view_income);
        editFieldValue = new EditText(ViewIncome.this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            //income = (ArrayList<Income>)extras.get("income");
            userInfo = (User)extras.get("userInfo");
        }
        Income entry = (Income)extras.get("income");

        incomeName = findViewById(R.id.income_name);
        description = findViewById(R.id.description);
        paymentAmount = findViewById(R.id.payment_amount);
        paymentType = findViewById(R.id.payment_type);
        paymentDate = findViewById(R.id.payment_date);
        notificationDate = findViewById(R.id.notification_date);
        delete = findViewById(R.id.deletebutton);
        delete.setText("DELETE");

        if (entry != null) {
            incomeName.setText(entry.getIncomeName());
            if (entry.getDescription()!=null) {
                description.setText(entry.getDescription());
            }
            paymentAmount.setText(entry.getPaymentAmount());
            paymentType.setText(entry.getPaymentType());
            if (entry.getPaymentDate()!= null) {
                paymentDate.setText(entry.getPaymentDate());
            }
            if (entry.getNotificationDate()!= null) {
                notificationDate.setText(entry.getNotificationDate());
            }

        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewIncome.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Entry");
                builder.setMessage("Would you like to delete this entry?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.deleteIncome(entry);
                                startActivity(new Intent(ViewIncome.this, MainActivity.class));
                                overridePendingTransition(0,0);
                                finish();
                                return;
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        incomeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewIncome.this);
                editField.setTitle("Edit Income Name:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setIncomeName(update);
                        userInfo.updateIncome(entry);
                        incomeName.setText(update);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewIncome.this);
                    }
                });
                editField.show();
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewIncome.this);
                editField.setTitle("Edit Description:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setDescription(update);
                        userInfo.updateIncome(entry);
                        description.setText(update);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewIncome.this);
                    }
                });
                editField.show();
            }
        });

        paymentAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewIncome.this);
                editField.setTitle("Edit Payment Amount:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setPaymentAmount(update);
                        userInfo.updateIncome(entry);
                        paymentAmount.setText(update);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewIncome.this);
                    }
                });
                editField.show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(ViewIncome.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });


    }

    private Income getEntry(String incomeName) {
        for (int i = 0; i < income.size(); i++) {
            if (income.get(i).getIncomeName().equals(incomeName)) {
                return income.get(i);
            }
        }
        return null;
    }
}
