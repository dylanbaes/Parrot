package com.CSE3311.parrot;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewIncome extends AppCompatActivity {

    private TextView incomeName; // part-time, full-time ...
    private TextView description;
    private TextView paymentAmount;
    private TextView paymentType; // biweekly or monthly
    private TextView paymentDate;
    private TextView notificationDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.entry_view_income);

        incomeName = findViewById(R.id.income_name);
        description = findViewById(R.id.description);
        paymentAmount = findViewById(R.id.payment_amount);
        paymentType = findViewById(R.id.payment_type);
        notificationDate = findViewById(R.id.notification_date);


    }
}
