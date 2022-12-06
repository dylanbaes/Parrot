package com.CSE3311.parrot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class each_category extends AppCompatActivity {

    TextView categoryNameText;
    TextView totalCostText;
    ArrayList<Expense> expenses;
    ArrayList<String> expenseList;
    private expenseAdapter.RecyclerViewClickListener listener;
    private User userInfo;
    RecyclerView eachCategoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_category);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String categoryType = getIntent().getStringExtra("CATEGORY_TYPE");
        categoryNameText = (TextView) findViewById(R.id.categoryNameTextView);
        categoryNameText.setText(categoryType);

        userInfo = (User) extra.get("userInfo");
        String totalCost = getIntent().getStringExtra("TOTAL_COST");
        totalCostText = (TextView) findViewById(R.id.totalTextView);
        totalCostText.setText(new StringBuilder().append("Total: $").append(totalCost));

        expenseList = (ArrayList<String>) getIntent().getSerializableExtra("EXPENSE_LIST");
        expenses = (ArrayList<Expense>) getIntent().getSerializableExtra("expenses");

        eachCategoryRecyclerView = (RecyclerView) findViewById(R.id.eachCategoryRecyclerView);
        eachCategoryRecyclerView.setNestedScrollingEnabled(false);
        setAdapter();


            //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.setting);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(each_category.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private void setOnClickListener() {
        listener = new expenseAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewExpense.class);
                intent.putExtra("uuid", expenses.get(position).getUuid());
                intent.putExtra("expenses", expenses);
                intent.putExtra("userInfo", userInfo);
                startActivity(intent);
            }
        };
    }

    private void setAdapter(){
        setOnClickListener();
        expenseAdapter adapter = new expenseAdapter(expenses, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        eachCategoryRecyclerView.setLayoutManager(layoutManager);
        eachCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eachCategoryRecyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(eachCategoryRecyclerView, false);
    }




}
