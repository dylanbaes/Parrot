package com.CSE3311.parrot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.Parse;

public class create_account extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Password;
    Button userCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ActivityCreateAccountBinding binding;
        super.onCreate(savedInstanceState);

        //binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        setContentView(R.layout.activity_create_account);

        FirstName = findViewById(R.id.firstname);
        LastName = findViewById(R.id.lastname);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        userCreateAccount = findViewById(R.id.createaccountbtn);

        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Email.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(Email.getText().toString());
                    user.setPassword(Password.getText().toString());
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
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Missing Attributes", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}