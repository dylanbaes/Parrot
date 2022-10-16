package com.CSE3311.parrot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    private void showAlert(String title, String message, boolean error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this).setTitle(title).setMessage(message).setPositiveButton("Ok", (dialog, which) -> {
            dialog.cancel();

            if (!error)
            {
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog ok = builder.create();
        ok.show();
    }

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

        StringBuilder validErrorMessage = new StringBuilder("Invalid email or password. Please try again.");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Check for empty text fields
                if(userEmailEditText.getText().toString().isEmpty() || userPasswordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, validErrorMessage, Toast.LENGTH_LONG).show();
                    return;
                }

                // Utilize progress dialog
                final ProgressDialog dlg = new ProgressDialog(Login.this);
                dlg.setTitle("Attempting to log in.");
                dlg.show();

                // Reset errors
                userEmailEditText.setError(null);
                userPasswordEditText.setError(null);

                // Attempt to log in with given credentials
                ParseUser.logInInBackground(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if(user!=null){
                            // If a user that matches credentials is found, show successful log in and navigate to main activity
                            Toast.makeText(getApplicationContext(), "Login Successful!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        else{
                            // If no users with matching credentials, show error message.
                            ParseUser.logOut();
                            //Toast.makeText(getApplicationContext(),validErrorMessage,Toast.LENGTH_LONG).show();

                            showAlert("Login failed", "Email not verified. Please try again.", true);
                        }
                    }
                });

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Switch to the create account screen
                startActivity(new Intent(Login.this, create_account.class));
            }

        });
    }
}
