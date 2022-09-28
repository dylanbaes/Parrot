package com.CSE3311.parrot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CSE3311.parrot.databinding.ActivityCreateAccountBinding;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class create_account extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Password;
    Button userCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCreateAccountBinding binding;
        super.onCreate(savedInstanceState);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setContentView(R.layout.activity_create_account);

        FirstName = findViewById(R.id.firstname);
        LastName = findViewById(R.id.lastname);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = new ParseUser();

                if(!FirstName.getText().toString().isEmpty() && !LastName.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Password.getText().toString().isEmpty())
                {
                    user.setEmail(Email.getText().toString());
                    user.setPassword(Password.getText().toString());

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
    }
}