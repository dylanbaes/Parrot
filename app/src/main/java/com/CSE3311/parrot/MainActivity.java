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
    Button userCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton=findViewById(R.id.logoutButton);
        userCreateButton=findViewById(R.id.createaccountbtn);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });


        //test
        //ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}