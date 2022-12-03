package com.CSE3311.parrot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.CSE3311.parrot.Models.Income;
import com.github.mikephil.charting.components.Legend;
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
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private RecyclerView mainRecyclerView;

    TextView income;
    TextView expenses;

    User userInfo;
    ArrayList<Expense> userExpenses;
    ArrayList<Income> userIncome;

    private ItemAdapter adapter;
    private List<DataModel> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mList = new ArrayList<>();
        Context context = this;
        income = findViewById(R.id.incomeTextView);
        expenses = findViewById(R.id.expensesTextView);


        ParseObject.registerSubclass(User.class);
        ParseQuery query = new ParseQuery(User.class);

        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setHasFixedSize(true);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> expenseList = new ArrayList<>();
        List<String> incomeList = new ArrayList<>();

        if (ParseUser.getCurrentUser().isAuthenticated()) {
            query.whereEqualTo("userName", ParseUser.getCurrentUser().getUsername());
            query.getFirstInBackground(new GetCallback<User>() {
                public void done(User userInformation, ParseException e) {
                    if (e == null) {
                        userInfo = userInformation;

                        // Gather list of expense and income from database
                        userExpenses = userInfo.getExpenseLists();
                        userIncome = userInfo.getIncomeLists();

                        // eachCategoryValue stores the total cost for each category
                        ArrayList<String> eachCategory = new ArrayList<>();
                        ArrayList<Double> eachCategoryValue = new ArrayList<>();

                        // Gather all string expense and income and store it into expenseList and incomeList
                        for (int i = 0; i < userExpenses.size(); i++) {
                            expenseList.add(userExpenses.get(i).getCategoryName());
                            System.out.println(expenseList.get(i));
                        }
                        for (int i = 0; i < userIncome.size(); i++) {
                            incomeList.add(userIncome.get(i).getIncomeName());
                        }

                        mList.add(new DataModel(expenseList, "Expenses"));
                        mList.add(new DataModel(incomeList, "Income"));

                        adapter = new ItemAdapter(mList);
                        //mAdapter = new RecyclerViewAdapter(eachCategory, context);
                        mainRecyclerView.setAdapter(adapter);


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
                        pieChartSetup(userInfo, eachCategory);
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
                    case R.id.view:
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
    private void pieChartSetup(User userInfo, ArrayList<String> eachCategory){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(14);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Expense Categories");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setTextColor(getResources().getColor(R.color.main_text_color));
        l.setTextSize(14);

        // Offset the pie chart
        pieChart.setExtraLeftOffset(10);
        pieChart.setExtraRightOffset(20);

        // Pie chart animation
        pieChart.animateY(1000, Easing.EaseInOutCubic);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String categoryType = String.valueOf(eachCategory.get((int)h.getX()));
                Intent intent = new Intent(MainActivity.this, each_category.class);
                userExpenses = userInfo.getExpenseLists();
                ArrayList<String> expenseNameList = new ArrayList<>();
                ArrayList<String> expenseValueList = new ArrayList<>();
                ArrayList<String> expenseList = new ArrayList<>();
                double totalCost = 0;

                for (int i = 0; i < userExpenses.size(); i++) {
                    if(userExpenses.get(i).getCategoryType().equals(categoryType)){
                        expenseNameList.add(userExpenses.get(i).getCategoryName());
                        expenseValueList.add(userExpenses.get(i).getCost());
                        totalCost += Double.parseDouble(userExpenses.get(i).getCost());
                    }
                }

                for (int i = 0; i < expenseNameList.size(); i++) {
                    expenseList.add(expenseNameList.get(i) + ":   $" + expenseValueList.get(i));
                }
                intent.putExtra("CATEGORY_TYPE",categoryType);
                intent.putExtra("EXPENSE_LIST",expenseList);
                intent.putExtra("TOTAL_COST",(String.valueOf(totalCost)));
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

    // Use this function to, given the name of the expense, find the expense within the database and it will return an expense object.
    private Expense getExpenseData (User userInfo, String expenseName) {
        Expense data;
        for (int i = 0; i < userInfo.getExpenseLists().size(); i++) {
            // Check if the expenseName we are searching for is in the expense list
            if (expenseName.equalsIgnoreCase(userInfo.getExpenseLists().get(i).getCategoryName())) {
                data = userInfo.getExpenseLists().get(i);
                return data;
            }
        }
        return null;
    }

    // Use this function to, given the name of the income, find the expense within the database and it will return an income object.
    private Income getIncomeData (User userInfo, String incomeName) {
        Income data;
        for (int i = 0; i < userInfo.getExpenseLists().size(); i++) {
            // Check if the incomeName we are searching for is in the income list
            if (incomeName.equalsIgnoreCase(userInfo.getIncomeLists().get(i).getIncomeName())) {
                data = userInfo.getIncomeLists().get(i);
                return data;
            }
        }
        return null;
    }
}