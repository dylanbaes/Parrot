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

public class Login extends AppCompatActivity {
    private EditText userEmailEditText;
    private EditText userPasswordEditText;

    private Button loginButton;
    private Button forgotPasswordButton;
    private Button registerButton;
    private ProgressDialog dlg;

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
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ParseInstallation.getCurrentInstallation().saveInBackground();

            // check if there already exists a session
            if (ParseUser.getCurrentUser()!=null){
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }

            userEmailEditText = findViewById(R.id.userEmailEditText);
            userPasswordEditText = findViewById(R.id.userPasswordEditText);
            loginButton = findViewById(R.id.loginButton);
            forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

            registerButton = findViewById(R.id.registerButton);

            loginButton.setOnClickListener(view -> {
                // Check for empty text fields
                assert !userEmailEditText.getText().toString().isEmpty() : "Error Log: Invalid Email. Please try again!";
                assert !userPasswordEditText.getText().toString().isEmpty() : "Error Log: Invalid Password. Please try again!";

                // Utilize progress dialog
                dlg = new ProgressDialog(Login.this);
                dlg.setTitle("Attempting to log in.");
                dlg.show();

                // Attempt to log in with given credentials
                ParseUser.logInInBackground(userEmailEditText.getText().toString(), userPasswordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e!=null || user==null){
                            throw new AssertionError("\"Error Log: Registration Failed\"");
                        }
                        dlg.dismiss();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    }
                });

            });
            registerButton.setOnClickListener(view -> {
                startActivity(new Intent(Login.this, Registration.class));
                finish();
            });

        } catch (AssertionError e) {
            userEmailEditText.setError(null);
            userPasswordEditText.setError(null);
            dlg.dismiss();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
