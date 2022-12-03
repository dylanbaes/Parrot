package com.CSE3311.parrot;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewItem extends AppCompatActivity {

    private TextView categoryName;
    private TextView description;
    private TextView paymentType;
    private TextView categoryType;
    private TextView startDate;
    private TextView endDate;
    private TextView notificationType;
    private TextView notificationDate;
    private TextView cost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.entry_view);

        categoryName = findViewById(R.id.category_name);
        description = findViewById(R.id.description);
        paymentType = findViewById(R.id.payment_type);
        categoryType = findViewById(R.id.category_type);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        notificationType = findViewById(R.id.notification_type);
        notificationDate = findViewById(R.id.notification_date);
        cost = findViewById(R.id.cost);


    }
}
