package com.CSE3311.parrot;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.Income;

import java.util.ArrayList;

public class ViewIncome extends AppCompatActivity {

    private TextView incomeName; // part-time, full-time ...
    private TextView description;
    private TextView paymentAmount;
    private TextView paymentType; // biweekly or monthly
    private TextView paymentDate;
    private TextView notificationDate;
    private ArrayList<Income> income;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_view_income);
        String nameIncome= "not set";

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            nameIncome = extras.getString("incomeName");
            income = (ArrayList<Income>)extras.get("income");
        }

        Income entry = getEntry(nameIncome);

        incomeName = findViewById(R.id.income_name);
        description = findViewById(R.id.description);
        paymentAmount = findViewById(R.id.payment_amount);
        paymentType = findViewById(R.id.payment_type);
        notificationDate = findViewById(R.id.notification_date);

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
