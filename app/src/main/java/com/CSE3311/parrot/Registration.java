package com.CSE3311.parrot;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CSE3311.parrot.Models.User;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    EditText userPasswordConfirmText;
    Button userCreateAccount;
    Button cancelButton;

    //sends the verification email to the user when creating an account
    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(Registration.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


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
        userPasswordConfirmText = findViewById(R.id.confirmpass);
        userCreateAccount = findViewById(R.id.createaccountbtn);
        cancelButton = findViewById(R.id.cancelbtn);

        //Function for when the button is pressed
        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If all instances are filled up
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !userEmailEditText.getText().toString().isEmpty() && !userPasswordEditText.getText().toString().isEmpty()) {

                    // Validate password with special characters and confirm pass is the same as regular pass

                    if(!isValidPassword(userPasswordEditText.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, 1 capital letter, 1 lowercase letter, 1 number, and 1 special character.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(!(userPasswordEditText.getText().toString().equals(userPasswordConfirmText.getText().toString()))) {
                        Toast.makeText(getApplicationContext(), "Password and confirm password line must be the same.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Set the email and password
                    ParseUser user = new ParseUser();
                    user.setUsername(userEmailEditText.getText().toString());
                    user.setPassword(userPasswordEditText.getText().toString());
                    user.setEmail(userEmailEditText.getText().toString());

                    //Apply the email and password
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {

                                showAlert("Verify Email", "Please verify you email before logging in.", false);
                                ParseObject.registerSubclass(User.class);
                                User userInfo = new User();
                                userInfo.setUserName(userEmailEditText.getText().toString());
                                userInfo.setlName(lastName.getText().toString());
                                userInfo.setfName(firstName.getText().toString());
                                userInfo.setEmail(userEmailEditText.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to register. An account with that email may already exist.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Missing Attributes", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        });

        //When the cancel button is pressed
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go back to the login screen
                startActivity(new Intent(Registration.this, Login.class));
            }
        });
    }

    public final static boolean isValidPassword(String target) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=!])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (target == null) {
            return false;
        }

        Matcher m = p.matcher(target);

        return m.matches();
    }
}