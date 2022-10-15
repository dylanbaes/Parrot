package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    Button logoutButton;
    Button createEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton=findViewById(R.id.logoutButton);
        createEntryButton=findViewById(R.id.createEntryButton);


        createEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activity_create_entry.class));
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                MainActivity.this.finish();
            }
        });


        //test
        //ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}