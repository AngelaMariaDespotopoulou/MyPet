//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 10th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText firstname;
    EditText lastname;
    EditText username;
    EditText password;
    EditText passwordValidation;
    Button submitButton;
    final LoginController localLoginController = new LoginController(this);
    String charCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.register_activity_title);

        firstname           = (EditText)findViewById(R.id.register_name);
        lastname            = (EditText)findViewById(R.id.register_surname);
        username            = (EditText)findViewById(R.id.register_username);
        password            = (EditText)findViewById(R.id.register_password);
        passwordValidation  = (EditText)findViewById(R.id.register_confpassword);
        submitButton        = (Button)findViewById(R.id.knock_submit);

        // The mechanism to prevent the usage of passwords that do not exceed six characters.
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // The check takes place on exit from the field.
                if (!hasFocus) {
                    charCounter = password.getText().toString();
                    charCounter.trim();
                    if (charCounter.length() <= 6 && charCounter.length() != 0) {
                        String errorMessage = getResources().getString(R.string.too_short_password);
                        // The error message appears as a hint, below the field.
                        password.setError(errorMessage);
                        // Text is cleared and focus return in the problematic field.
                        password.setText("");
                        password.post(new Runnable() {
                            public void run() {
                                password.requestFocus();

                            }
                        });
                    }

                    // A second error message here. The two passwords must be identical, if both have a value.
                    if ( !(password.getText().toString().trim().equals("")) && !(passwordValidation.getText().toString().trim().equals(""))) {
                        if (!(password.getText().toString().trim().equals(passwordValidation.getText().toString().trim()))) {
                            String errorMessage = getResources().getString(R.string.pass_not_identical);
                            password.setError(errorMessage);
                            password.setText("");
                            password.post(new Runnable() {
                                public void run() {
                                    password.requestFocus();
                                }
                            });
                        }
                    }
                }
            }

        });

        // Same as above for the validation string.
        passwordValidation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    charCounter = passwordValidation.getText().toString();
                    charCounter.trim();
                    if (charCounter.length() <= 6 && charCounter.length() != 0) {
                        String errorMessage = getResources().getString(R.string.too_short_password);
                        passwordValidation.setError(errorMessage);
                        passwordValidation.setText("");
                        passwordValidation.post(new Runnable() {
                            public void run() {
                                passwordValidation.requestFocus();

                            }
                        });
                    }

                    // A second error message here. The two passwords must be identical, if both have a value.
                    if ( !(password.getText().toString().trim().equals("")) && !(passwordValidation.getText().toString().trim().equals(""))) {
                        if (!(password.getText().toString().trim().equals(passwordValidation.getText().toString().trim()))) {
                            String errorMessage = getResources().getString(R.string.pass_not_identical);
                            passwordValidation.setError(errorMessage);
                            passwordValidation.setText("");
                            passwordValidation.post(new Runnable() {
                                public void run() {
                                    passwordValidation.requestFocus();
                                }
                            });
                        }
                    }
                }
            }
        });
    }


    //*****************************************************************************************************************************
    // Reaction to a tap on button "Register". Method setOnClickListener produced a fatal exception.
    // This method worked fine, though.
    //*****************************************************************************************************************************

    public void onClickRegister(View view) {
        AttemptRegistration();
    }


    //*****************************************************************************************************************************
    // Attempts registration by referring to the local LoginController object.
    //*****************************************************************************************************************************

    private void AttemptRegistration()
    {
        if(localLoginController.isUserAlreadyLoggedIn() != "" && localLoginController.isUserAlreadyLoggedIn() != null)
        {
            // Practically the code never gets in there. The Menu takes care of this.
            String toastMessage = getResources().getString(R.string.already_loggedin_message);
            Toast.makeText(RegisterActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            // Makes sure that all five fields have values.
            String errorMessage;

            if(firstname.getText().toString().length() < 1)
            {
                errorMessage = getResources().getString(R.string.field_empty);
                firstname.setError(errorMessage);
                firstname.post(new Runnable() {
                    public void run() {
                        firstname.requestFocus();
                    }
                });
            }

            else if(lastname.getText().toString().length() < 1)
            {
                errorMessage = getResources().getString(R.string.field_empty);
                lastname.setError(errorMessage);
                lastname.post(new Runnable() {
                    public void run() {
                        lastname.requestFocus();
                    }
                });
            }

            else if(username.getText().toString().length() < 1)
            {
                errorMessage = getResources().getString(R.string.field_empty);
                username.setError(errorMessage);
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

            else if(passwordValidation.getText().toString().length() < 1)
            {
                errorMessage = getResources().getString(R.string.field_empty);
                passwordValidation.setError(errorMessage);
                passwordValidation.post(new Runnable() {
                    public void run() {
                        passwordValidation.requestFocus();
                    }
                });
            }

            else {
                // Proceed with registration.
                localLoginController.registerUser(firstname.getText().toString(), lastname.getText().toString(), username.getText().toString(), password.getText().toString());
            }
        }
    }
}
