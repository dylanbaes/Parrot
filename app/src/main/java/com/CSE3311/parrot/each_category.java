package com.CSE3311.parrot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_category);

        Intent intent = getIntent();
        String categoryType = getIntent().getStringExtra("CATEGORY_TYPE");
        categoryNameText = (TextView) findViewById(R.id.categoryNameTextView);
        categoryNameText.setText(categoryType);

        String totalCost = getIntent().getStringExtra("TOTAL_COST");
        totalCostText = (TextView) findViewById(R.id.totalTextView);
        totalCostText.setText(new StringBuilder().append("Total: $").append(totalCost));

        ArrayList<String> expenseList = (ArrayList<String>) getIntent().getSerializableExtra("EXPENSE_LIST");

        RecyclerView eachCategoryRecyclerView = (RecyclerView) findViewById(R.id.eachCategoryRecyclerView);
        eachCategoryRecyclerView.setHasFixedSize(true);
        eachCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NestedAdapter adapter = new NestedAdapter(expenseList);
        eachCategoryRecyclerView.setAdapter(adapter);


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
}
