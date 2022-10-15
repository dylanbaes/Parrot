package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class activity_create_entry extends AppCompatActivity {

    EditText subscriptionName;
    EditText subscriptionDescription;
    EditText subscriptionStartDate;
    EditText subscriptionEndDate;
    EditText cost;
    EditText paymentType;
    EditText subscriptionType;
    EditText category;

    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        subscriptionName = findViewById(R.id.create_entry_edit_text_subscription_name);
        subscriptionDescription = findViewById(R.id.create_entry_edit_text_description);
        subscriptionStartDate = findViewById(R.id.create_entry_edit_text_start_date);
        subscriptionEndDate = findViewById(R.id.create_entry_edit_text_end_date);
        cost = findViewById(R.id.create_entry_edit_text_cost);
        paymentType = findViewById(R.id.create_entry_edit_text_payment_type);
        subscriptionType = findViewById(R.id.create_entry_edit_text_subscription_type);
        category = findViewById(R.id.create_entry_edit_text_notification_date);

        cancel = findViewById(R.id.create_entry_cancel_submission);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_create_entry.this, MainActivity.class));
            }
        });

    }
}