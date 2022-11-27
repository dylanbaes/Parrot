package com.CSE3311.parrot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.CSE3311.parrot.Models.Income;
import com.parse.GetCallback;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private RecyclerView listEntriesRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView income;
    TextView expenses;

    User userInfo;
    ArrayList<Expense> userExpenses;
    ArrayList<Income> userIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        income = findViewById(R.id.incomeTextView);
        expenses = findViewById(R.id.expensesTextView);


        ParseObject.registerSubclass(User.class);
        ParseQuery query = new ParseQuery(User.class);

        listEntriesRecyclerView = (RecyclerView) findViewById(R.id.listEntriesRecyclerView);
        listEntriesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        listEntriesRecyclerView.setLayoutManager(layoutManager);



        if (ParseUser.getCurrentUser().isAuthenticated()) {
            query.whereEqualTo("userName", ParseUser.getCurrentUser().getUsername());
            query.getFirstInBackground(new GetCallback<User>() {
                public void done(User userInformation, ParseException e) {
                    if (e == null) {
                        userInfo = userInformation;

                        // Gather list of expense and income from database
                        userExpenses = userInfo.getExpenseLists();
                        userIncome = userInfo.getIncomeLists();

                        // eachCategory stores all category type
                        ArrayList<String> eachCategory = new ArrayList<>();
                        // eachCategoryValue stores the total cost for each category
                        ArrayList<Double> eachCategoryValue = new ArrayList<>();

                        eachCategory.add(userExpenses.get(0).getCategoryType());
                        eachCategoryValue.add(Double.parseDouble(userExpenses.get(0).getCost()));
                        for(int i = 1; i < userExpenses.size(); i++) {
                            boolean match = false;
                            for (int j = 0; j < eachCategory.size(); j++) {
                                if (userExpenses.get(i).getCategoryType().equals(eachCategory.get(j))) {
                                    eachCategoryValue.set(j, eachCategoryValue.get(j) + Double.parseDouble(userExpenses.get(i).getCost()));
                                    match = true;
                                }
                            }
                            if (!match) {
                                eachCategory.add(userExpenses.get(i).getCategoryType());
                                eachCategoryValue.add(Double.parseDouble(userExpenses.get(i).getCost()));
                            }
                        }


                        mAdapter = new RecyclerViewAdapter(eachCategory, context);
                        listEntriesRecyclerView.setAdapter(mAdapter);

                        double totalIncome = 0;

                        for(int i = 0; i < userIncome.size(); i++) {
                            totalIncome += Double.parseDouble(userIncome.get(i).getPaymentAmount());
                        }
                        income.setText(new StringBuilder().append("Income: $").append(String.format("%.2f", totalIncome)));

                        // ArrayList<Expense> userExpenseList = userInfo.getExpenseLists();
                        double totalExpenses = 0;

                        for(int i = 0; i < eachCategoryValue.size(); i++) {
                            totalExpenses += eachCategoryValue.get(i);
                        }
                        expenses.setText(new StringBuilder().append("Expenses: $").append(String.format("%.2f", totalExpenses)));


                        // Pie Chart Implementation
                        pieChart = findViewById(R.id.mainPieChart);
                        pieChartSetup(eachCategory);
                        pieChartData(eachCategoryValue, eachCategory);
                    } else {
                        userInfo = null;
                    }
                }
            });
        }



        //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.setting);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notification:
                    case R.id.update:
                        return true;
                    case R.id.create:
                        Intent createEntryIntent = new Intent(MainActivity.this, CreateEntry.class);
                        createEntryIntent.putExtra("userInfo",userInfo);
                        startActivity(createEntryIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
    private void pieChartSetup(ArrayList<String> eachCategory){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(14);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Expense Categories");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        // Offset the pie chart
        pieChart.setExtraLeftOffset(10);
        pieChart.setExtraRightOffset(20);

        // Pie chart animation
        pieChart.animateY(1000, Easing.EaseInOutCubic);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String categoryName = String.valueOf(eachCategory.get((int)h.getX()));
                Intent intent = new Intent(MainActivity.this, each_category.class);
                intent.putExtra("CATEGORY_NAME",categoryName);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void pieChartData(ArrayList<Double> eachCategoryValue, ArrayList<String> eachCategory){
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(int i = 0; i < eachCategory.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(eachCategoryValue.get(i).toString()), eachCategory.get(i)));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }

        for(int color: ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        // Create dataset for pie chart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData pieChartObject = new PieData(dataSet);
        pieChartObject.setDrawValues(true);
        pieChartObject.setValueFormatter(new PercentFormatter(pieChart));
        pieChartObject.setValueTextSize(16f);
        pieChartObject.setValueTextColor(Color.BLACK);

        pieChart.setData(pieChartObject);
        pieChart.invalidate(); // refresh the pie chart
    }
}