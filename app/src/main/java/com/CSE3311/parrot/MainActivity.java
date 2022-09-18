package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test
        //ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}