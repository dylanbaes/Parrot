package com.CSE3311.parrot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        return true;
                    case R.id.create:
                        startActivity(new Intent(MainActivity.this, activity_create_entry.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.update:
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
        LegendEntry l1=new LegendEntry("Bills",Legend.LegendForm.SQUARE,10f,2f,null,Color.GREEN);
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
                // startActivity(new Intent(MainActivity.this, each_category.class));
                // String categoryName = pieChart.getData().getDataSetForEntry(e).getLabel();
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