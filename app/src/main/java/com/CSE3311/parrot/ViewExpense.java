package com.CSE3311.parrot;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CSE3311.parrot.Models.Expense;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {

    private TextView categoryName;
    private TextView description;
    private TextView paymentType;
    private TextView categoryType;
    private TextView startDate;
    private TextView endDate;
    private TextView notificationType;
    private TextView notificationDate;
    private TextView cost;
    private ArrayList<Expense> expense;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_view_expense);
        String uuid = "not set";

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            uuid = extras.getString("uuid");
            expense = (ArrayList<Expense>)extras.get("expenses");
        }

        Expense entry;

        entry = getEntry(uuid);


        categoryName = findViewById(R.id.category_name);
        description = findViewById(R.id.description);
        paymentType = findViewById(R.id.payment_amount);
        categoryType = findViewById(R.id.payment_type);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        notificationType = findViewById(R.id.notification_type);
        notificationDate = findViewById(R.id.notification_date);
        cost = findViewById(R.id.cost);

        if (entry!=null) {
            categoryName.setText(entry.getCategoryName());
            description.setText(entry.getDescription());
            paymentType.setText(entry.getPaymentType());
            categoryType.setText(entry.getCategoryType());
            startDate.setText(entry.getStartDate());
            endDate.setText((entry.getEndDate()));
            notificationType.setText(entry.getNotificationType());
            notificationDate.setText(entry.getNotificationDate());
            cost.setText(entry.getCost());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(ViewExpense.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });


    }

    private Expense getEntry(String uuid) {
        for (int i = 0; i < expense.size(); i++) {
            if (expense.get(i).getUuid().equals(uuid)) {
                return expense.get(i);
            }
        }
        return null;
    }


}
