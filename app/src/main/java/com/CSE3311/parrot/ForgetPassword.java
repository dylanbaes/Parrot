package com.CSE3311.parrot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgetPassword extends AppCompatActivity {
    private EditText userEmailEditText;

    private Button resetPasswordButton;
    private Button cancelButton;
    private ProgressDialog dlg;

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(ForgetPassword.this, Login.class);
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
        setContentView(R.layout.activity_forget_password);

        userEmailEditText = findViewById(R.id.userEmailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        cancelButton = findViewById(R.id.cancelButton);

        dlg = new ProgressDialog(this);

        resetPasswordButton.setOnClickListener(view -> {
            String userEmail = userEmailEditText.getText().toString().trim();
            dlg.setMessage("Please wait...");
            dlg.show();

            //sends the email

            ParseUser.requestPasswordResetInBackground(userEmail,
                    new RequestPasswordResetCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // An email was successfully sent with reset instructions
                                final String title = "Password Reset Email Sent!";
                                final String message = "Check Your Email To Change Your Password";
                                showAlert(title, message, false);
                            } else {
                                // Something went wrong. Look at the ParseException
                                final String title = "Password Reset Failed";
                                final String message = "Password cannot be changed";
                            }
                        }
                    });
        });
        //When the cancel button is pressed
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go back to the login screen
                startActivity(new Intent(ForgetPassword.this, Login.class));
            }
        });

    }

}