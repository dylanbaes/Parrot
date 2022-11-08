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

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.CSE3311.parrot.Models.Income;
import com.parse.GetCallback;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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

    ArrayList<String> surface = new ArrayList<>();

    private PieChart pieChart;
    private RecyclerView listEntriesRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    User userInfo;
    ArrayList<Expense> userExpenses;
    ArrayList<Income> userIncome;
    ArrayList<String> expenseCategories;
    ArrayList<String> incomeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;

//        surface.add("Income");
//        surface.add("Expense");

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
                        // Make scrollable list --> surface level = category types which can shows entries in that type if clicked
                        // Create ArrayList of all different category types

                        // Gather list of expense and income from database
                        userExpenses = userInfo.getExpenseLists();
                        userIncome = userInfo.getIncomeLists();
                        //surface.add(userExpenses.get(0).getCategoryName());
                        for (int i = 0; i < userExpenses.size(); i++) {
                            surface.add(userExpenses.get(i).getCategoryName());
                        }
                        for (int i = 0; i < userIncome.size(); i++) {
                            surface.add(userIncome.get(i).getIncomeName());
                        }

                        mAdapter = new RecyclerViewAdapter(surface, context);
                        listEntriesRecyclerView.setAdapter(mAdapter);

                        //ArrayList<Expense> exp = userInfo.getExpenseLists();

                    } else {
                        userInfo = null;
                    }
                }
            });
        }

        // Pie Chart Implementation

        pieChart = findViewById(R.id.mainPieChart);
        pieChartSetup();
        pieChartData();

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
    private void pieChartSetup(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(14);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Expense Categories");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        // Offset the pie chart
        pieChart.setExtraTopOffset(-50);
        pieChart.setExtraBottomOffset(50);
        pieChart.setExtraLeftOffset(10);
        pieChart.setExtraRightOffset(10);

        // Pie chart animation
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        // Legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setDrawInside(true);
        LegendEntry l1=new LegendEntry("Bills",Legend.LegendForm.SQUARE,10f,2f,null, Color.GREEN);
        LegendEntry l2=new LegendEntry("Subscriptions", Legend.LegendForm.SQUARE,10f,2f,null,Color.YELLOW);
        LegendEntry l3=new LegendEntry("Investments", Legend.LegendForm.SQUARE,10f,2f,null,Color.RED);
        legend.setCustom(new LegendEntry[]{l1,l2,l3});
        legend.setTextSize(20);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String categoryName = "Bills";
                Intent intent = new Intent(MainActivity.this, each_category.class);
                intent.putExtra("CATEGORY_NAME",categoryName);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void pieChartData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.166f, "$1,422"));
        entries.add(new PieEntry(0.92f, "$245"));
        entries.add(new PieEntry(0.375f, "$1000"));

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