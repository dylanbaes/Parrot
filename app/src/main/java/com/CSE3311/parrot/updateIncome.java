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
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.Income;
import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class updateIncome extends AppCompatActivity {

    private RecyclerView incomeRv;
    private TextView incomeTitleTv;
    private ArrayList<Income> income;
    private User userInfo;
    private expenseAdapter.RecyclerViewClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        userInfo = (User) extra.get("userInfo");

        income = new ArrayList<>();
        for (int i = 0; i < userInfo.getIncomeLists().size(); i++) {
            income.add(userInfo.getIncomeLists().get(i));
        }

        setContentView(R.layout.entries_update_income);

        incomeRv = findViewById(R.id.incomeRecyclerView);
        incomeRv.setNestedScrollingEnabled(false);
        incomeTitleTv = findViewById(R.id.incomeTitleTv);

        setAdapter();

        incomeTitleTv.setText("Income");

        //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(updateIncome.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    private void setAdapter() {
        setOnClickListener();
        incomeAdapter adapter = new incomeAdapter(income, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        incomeRv.setLayoutManager(layoutManager);
        incomeRv.setItemAnimator(new DefaultItemAnimator());
        incomeRv.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(incomeRv, false);
    }

    private void setOnClickListener() {
        listener = new expenseAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewIncome.class);
                intent.putExtra("incomeName", income.get(position).getUuid());
                intent.putExtra("income", income);
                startActivity(intent);
            }
        };
    }
}
