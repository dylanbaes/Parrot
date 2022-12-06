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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class updateExpense extends AppCompatActivity {

    private RecyclerView expenseRv;
    private TextView expenseTitleTv;
    private ArrayList<Expense> expenses;
    private User userInfo;
    private expenseAdapter.RecyclerViewClickListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        userInfo = (User) extra.get("userInfo");
        expenses = new ArrayList<>();
        for (int i = 0; i < userInfo.getExpenseLists().size(); i++) {
            expenses.add(userInfo.getExpenseLists().get(i));
        }
        setContentView(R.layout.entries_update_expense);

        expenseRv = findViewById(R.id.expenseRecyclerView);
        expenseTitleTv = findViewById(R.id.expenseTitleTv);

        setAdapter();

        //expenseTitleTv.setText(R.string.Expenses);

        //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(updateExpense.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }
    private void setAdapter(){
        setOnClickListener();
        expenseAdapter adapter = new expenseAdapter(expenses, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        expenseRv.setLayoutManager(layoutManager);
        expenseRv.setItemAnimator(new DefaultItemAnimator());
        expenseRv.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new expenseAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewExpense.class);
                intent.putExtra("uuid", expenses.get(position).getUuid());
                intent.putExtra("expenses", expenses);
                startActivity(intent);
            }
        };
    }
}
