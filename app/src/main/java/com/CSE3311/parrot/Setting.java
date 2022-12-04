package com.CSE3311.parrot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseUser;

public class Setting extends AppCompatActivity {

    Button logoutButton;
    Switch themeSwitch;
    public static boolean night = false;

    TextView username,email,fName,lName,numberOfExpenses, numberOfIncomes;
    User userInfo;

    private AlertDialog.Builder editField;
    private AlertDialog dialog;
    private EditText editFieldValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bundle extra = getIntent().getExtras();
        userInfo = (User) extra.get("userInfo");

        editFieldValue = new EditText(Setting.this);

        themeSwitch = findViewById(R.id.light_dark_switch);
        logoutButton=findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                startActivity(new Intent(Setting.this, Login.class));
                finish();
            }
        });
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    onNightModeChanged(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        username = findViewById(R.id.setting_user_name);
        username.setText(userInfo.getUserName());

        numberOfExpenses = findViewById(R.id.setting_number_of_expenses);
        numberOfExpenses.setText(String.valueOf(userInfo.getExpenseLists().size()));

        numberOfIncomes = findViewById(R.id.setting_number_of_incomes);
        numberOfIncomes.setText(String.valueOf(userInfo.getIncomeLists().size()));

        email = findViewById(R.id.setting_user_email);
        email.setText("EMail: "+ userInfo.getEmail());
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editField = new AlertDialog.Builder(Setting.this);
                editField.setTitle("Edit Email:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String email = editFieldValue.getText().toString();
                        userInfo.setEmail(email);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(Setting.this);
                    }
                });
                editField.show();
            }
        });

        fName = findViewById(R.id.setting_user_fname);
        fName.setText("First Name: "+userInfo.getfName());
        fName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editField = new AlertDialog.Builder(Setting.this);
                editField.setTitle("Edit First Name:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String fNameString = editFieldValue.getText().toString();
                        userInfo.setfName(fNameString);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(Setting.this);
                    }
                });
                editField.show();
            }
        });

        lName = findViewById(R.id.setting_user_lname);
        lName.setText("Lirst Name: "+userInfo.getlName());
        lName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editField = new AlertDialog.Builder(Setting.this);
                editField.setTitle("Edit Last Name:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String lNameString = editFieldValue.getText().toString();
                        userInfo.setfName(lNameString);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(Setting.this);

                    }
                });
                editField.show();
            }
        });

        //Initialization for bottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(Setting.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }
}
