package com.CSE3311.parrot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class create_account extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
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
        FirstName = findViewById(R.id.firstname);
        LastName = findViewById(R.id.lastname);
        userEmailEditText = findViewById(R.id.email);
        userPasswordEditText = findViewById(R.id.password);
        userCreateAccount = findViewById(R.id.createaccountbtn);
        cancelButton = findViewById(R.id.cancelbtn);

        //When the create account button is pressed
        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FirstName.getText().toString().isEmpty() && !LastName.getText().toString().isEmpty() && !userEmailEditText.getText().toString().isEmpty() && !userPasswordEditText.getText().toString().isEmpty())
                {
                    ParseUser user = new ParseUser();
                    user.setUsername(userEmailEditText.getText().toString());
                    user.setPassword(userPasswordEditText.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(),"Registration Successful!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(create_account.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration Failed!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Missing Attributes", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go back to the login screen
                startActivity(new Intent(create_account.this, Login.class));
            }
        });
    }
}