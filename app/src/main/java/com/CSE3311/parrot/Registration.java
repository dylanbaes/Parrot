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

import com.CSE3311.parrot.AppVirgil;
import com.CSE3311.parrot.utils.AuthRx;
import com.CSE3311.parrot.utils.Preferences;
import com.CSE3311.parrot.utils.RxEthree;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import com.virgilsecurity.android.common.model.EThreeParams;
import com.virgilsecurity.android.ethree.interaction.EThree;
import java.util.stream.Stream;

public class Registration extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText userEmailEditText;
    EditText userPasswordEditText;
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
        userCreateAccount = findViewById(R.id.createaccountbtn);
        cancelButton = findViewById(R.id.cancelbtn);

        //Function for when the button is pressed
        userCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If all instances are filled up
                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !userEmailEditText.getText().toString().isEmpty() && !userPasswordEditText.getText().toString().isEmpty()) {
                    //Set the email and password
                    ParseUser user = new ParseUser();
                    user.setUsername(userEmailEditText.getText().toString());
                    user.setPassword(userPasswordEditText.getText().toString());
                    user.setEmail(userEmailEditText.getText().toString());

                    //Apply the email and password
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e != null) {
                                Log.d("ERROR",e.getMessage());
                                throw new AssertionError("\"Error Log: Registration Failed\"");
                            }
                            if (e == null) {
                                //Toast.makeText(getApplicationContext(), "Registration Successful!" + "\n"  + "Please verify your email.", Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(create_account.this, Login.class));

                                showAlert("Verify Email", "Please verify you email before logging in.", false);
                                ParseObject.registerSubclass(User.class);
                                User userInfo = new User();
                                userInfo.setUserName(userEmailEditText.getText().toString());
                                userInfo.setlName(lastName.getText().toString());
                                userInfo.setfName(firstName.getText().toString());
                                userInfo.setEmail(userEmailEditText.getText().toString());

                                //this might not be in the right section
                                ParseUser curUser = ParseUser.getCurrentUser();
                                Preferences preference = new Preferences(v.getContext());
                                //single here is a reactive scheduling thing, it can do an asynch server call to return the string with the user info
                                Single<String> authString = AuthRx.INSTANCE.virgilJwt(curUser.getSessionToken());//<-- problem is here with session token
                                final String auth = authString.blockingGet();//blocking is inadvisable try to see about turning this to asynch if it causes problems
                                preference.setVirgilToken(auth);
                                RxEthree Rxe3 = new RxEthree(v.getContext());
                                Single<EThree> ethree = Rxe3.initEthree(curUser.getUsername(),false);
                                AppVirgil virgil = new AppVirgil();
                                //these blocking gets force a response and may need to be replaced with asynch
                                virgil.eThree = ethree.blockingGet();//blocking is inadvisable try to see about turning this to asynch if it causes problems
                                //register because its a register function
                                Rxe3.registerEthree();

                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Missing Attributes", Toast.LENGTH_LONG).show();
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
}