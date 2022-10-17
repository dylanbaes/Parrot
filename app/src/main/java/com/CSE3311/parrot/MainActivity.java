package com.CSE3311.parrot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.CSE3311.parrot.Models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    Button logoutButton;
    Button createEntryButton;

    EditText userName;

    User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton=findViewById(R.id.logoutButton);
        createEntryButton=findViewById(R.id.createEntryButton);
        userName = (EditText) findViewById(R.id.user_name_value);
        userName.setText("TEST");

        ParseObject.registerSubclass(User.class);
        ParseQuery query = new ParseQuery(User.class);
        query.whereEqualTo("userName",ParseUser.getCurrentUser().getUsername());
        query.getFirstInBackground(new GetCallback<User>() {
            public void done(User userInformation, ParseException e) {
                if (e == null) {
                    userInfo = userInformation;
                    userName.setText(userInfo.getEmail());
                } else {
                    userInfo = null;
                }
            }
        });

        createEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createEntryIntent = new Intent(MainActivity.this, CreateEntry.class);
                createEntryIntent.putExtra("userInfo",userInfo);
                startActivity(createEntryIntent);
            }
        });
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


        //test
        //ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}