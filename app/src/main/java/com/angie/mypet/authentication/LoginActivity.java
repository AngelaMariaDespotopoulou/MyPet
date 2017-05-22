//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angie.mypet.R;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;
    Button loginButton;
    TextView forgotPassword;
    EditText username;
    EditText password;
    final LoginController localLoginController = new LoginController(this);


    //*****************************************************************************************************************************
    // onCreate callback method
    // Assigns listeners to widgets.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login_activity_title);

        registerButton = (Button)findViewById(R.id.knock_register);
        loginButton = (Button)findViewById(R.id.knock_submit);
        forgotPassword = (TextView)findViewById(R.id.knock_forgot);
        username = (EditText)findViewById(R.id.knock_username);
        password = (EditText)findViewById(R.id.knock_password);

        // If the user taps the "Register" button, a new Activity is launched.
        registerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // If the user taps the "Log In" button, a login is attempted.
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                // Makes sure that both fields have values.
                String errorMessage;
                if(username.getText().toString().length() < 1)
                {
                    errorMessage = getResources().getString(R.string.field_empty);
                    // The error message appears as a hint, below the field.
                    username.setError(errorMessage);
                    // Text is cleared and focus return in the problematic field.
                    username.post(new Runnable() {
                        public void run() {
                            username.requestFocus();
                        }
                    });
                }

                else if(password.getText().toString().length() < 1)
                {
                    errorMessage = getResources().getString(R.string.field_empty);
                    password.setError(errorMessage);
                    password.post(new Runnable() {
                        public void run() {
                            password.requestFocus();
                        }
                    });
                }

                else {
                    AttemptLogin();
                }
            }
        });

        // If the user taps the "Forgot button" options, (s)he gets taunted.
        // In a real world situation, this would send probably an e-mail.
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String toastMessage = getResources().getString(R.string.bummer_message);
                Toast.makeText(LoginActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //*****************************************************************************************************************************
    // Attempts login by referring to the local LoginController object.
    //*****************************************************************************************************************************

    private void AttemptLogin()
    {
        if(localLoginController.isUserAlreadyLoggedIn() != "" && localLoginController.isUserAlreadyLoggedIn() != null)
        {
            // Practically the code never gets in there. The Menu takes care of this.
            String toastMessage = getResources().getString(R.string.already_loggedin_message);
            Toast.makeText(LoginActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            localLoginController.validateUser(username.getText().toString(), password.getText().toString());
        }
    }


    //*****************************************************************************************************************************
    // After registration, the user gets automatically to logged in state. In that case, the Login Activity is no longer needed.
    // It kills itself.
    //*****************************************************************************************************************************

    @Override
    protected void onResume()
    {
        super.onResume();

        if(localLoginController.isUserAlreadyLoggedIn() != "" && localLoginController.isUserAlreadyLoggedIn() != null)
        {
            this.finish();
        }
    }
}
