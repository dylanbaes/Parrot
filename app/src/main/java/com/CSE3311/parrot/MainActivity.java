package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.CSE3311.parrot.Models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
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