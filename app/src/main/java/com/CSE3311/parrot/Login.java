package com.CSE3311.parrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Login  extends AppCompatActivity{

    EditText userEmailEditText;
    EditText userPasswordEditText;
    Button loginButton;
    Button forgotPasswordButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        userEmailEditText=findViewById(R.id.userEmailEditText);
        userPasswordEditText=findViewById(R.id.userPasswordEditText);
        loginButton=findViewById(R.id.loginButton);
        forgotPasswordButton=findViewById(R.id.forgotPasswordButton);
        registerButton=findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userEmailEditText.getText().toString().isEmpty() && !userPasswordEditText.getText().toString().isEmpty()){
                    ParseUser.logInInBackground(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null){
                                Toast.makeText(getApplicationContext(), "Login Successful!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Login Failed! Try Again or Register!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!userEmailEditText.getText().toString().isEmpty() && !userPasswordEditText.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(userEmailEditText.getText().toString());
                    user.setPassword(userPasswordEditText.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(),"Registration Successful!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration Failed!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }*/

                startActivity(new Intent(Login.this, create_account.class));
            }

        });
    }
}
