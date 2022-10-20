package com.CSE3311.parrot;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CSE3311.parrot.Models.User;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class Registration extends AppCompatActivity {

    private String fName;
    private String lName;
    private String email;
    private String password;
    private String confirmPassword;
    private ProgressDialog dlg;

    EditText firstName;
    EditText lastName;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    Button userCreateAccount;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ActivityCreateAccountBinding binding;
        super.onCreate(savedInstanceState);

        //binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        setContentView(R.layout.activity_create_account);

        //Getting input for each edit text field
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        userEmailEditText = findViewById(R.id.email);
        userPasswordEditText = findViewById(R.id.password);
        userCreateAccount = findViewById(R.id.createaccountbtn);
        cancelButton = findViewById(R.id.cancelbtn);

        //When the create account button is pressed
        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dlg = new ProgressDialog(Registration.this);
                    dlg.setTitle("Registering your account, please wait!");
                    dlg.show();

                    assert (!firstName.getText().toString().isEmpty()) : "Error Log: First Name not passed on";
                    assert (!lastName.getText().toString().isEmpty()) : "Error Log: Last Name not passed on";
                    assert (!userEmailEditText.getText().toString().isEmpty()) : "Error Log: Email Not Passed on";
                    assert (!userPasswordEditText.getText().toString().isEmpty()) : "Error Log: Password not passed on";

                    ParseUser user = new ParseUser();
                    user.setUsername(userEmailEditText.getText().toString());
                    user.setPassword(userPasswordEditText.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                Log.d("ERROR",e.getMessage());
                                throw new AssertionError("\"Error Log: Registration Failed\"");
                            }
                            dlg.dismiss();

                            ParseObject.registerSubclass(User.class);
                            // User userInfo = ParseObject.createWithoutData(User.class,user.getObjectId());
                            User userInfo = new User();
                            userInfo.setUserName(userEmailEditText.getText().toString());
                            userInfo.setlName(lastName.getText().toString());
                            userInfo.setfName(firstName.getText().toString());
                            userInfo.setEmail(userEmailEditText.getText().toString());
                            Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registration.this, Login.class));
                            finish();
                        }
                    });
                } catch (Exception e) {
                    dlg.dismiss();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
        });
    }

}