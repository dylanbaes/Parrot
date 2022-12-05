package com.CSE3311.parrot;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ViewList extends AppCompatActivity {

    private RecyclerView expenseRv;
    private RecyclerView incomeRv;
    private TextView expenseTitleTv;
    private TextView incomeTitleTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.entries_list_view);

        expenseRv = findViewById(R.id.expenseRecyclerView);
        incomeRv = findViewById(R.id.incomeRecyclerView);
        expenseTitleTv = findViewById(R.id.expenseTitleTv);
        incomeTitleTv = findViewById(R.id.incomeTitleTv);

        expenseTitleTv.setText("Expenses");
        incomeTitleTv.setText("Income");

        //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(ViewList.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }
}
