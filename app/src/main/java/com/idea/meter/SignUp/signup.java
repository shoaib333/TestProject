package com.idea.meter.SignUp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idea.meter.R;
import com.idea.meter.Settings.Settings;
import com.idea.meter.Startup_Pages.login_page;

public class signup extends login_page {
    public static final String PREFS_NAME = "MyPrefsFile";
    Button app_signup;
    EditText User_Name;
    EditText Password;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app_signup = (Button) findViewById(R.id.signup_create);
        User_Name = (EditText) findViewById(R.id.new_user_name);
        Password = (EditText) findViewById(R.id.new_password);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        settings = getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);

        app_signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });
    }

    View.OnClickListener signupListener = new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                try {
                    String u_n = User_Name.getText().toString();
                    String pw = Password.getText().toString();

                    if (User_Name.getText().toString().equals("") || Password.getText().toString().equals("")) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(signup.this);

                        dlgAlert.setMessage("set a reasonable username and password");
                        dlgAlert.setTitle("Invalid Username or Password");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    } else {

                        Boolean temp = true;//TODO will be removed when http request is adde
                        // We need an Editor object to make preference changes.
                        // All objects are from android.context.Context
                        Settings appSettings = new Settings(getApplicationContext());
                        appSettings.InitializeSharedPreferences(getApplicationContext());

                        //TODO: make a http GET call to server for signup
                        //and handle the response
                        //if it is successful move to next page otherwise
                        //prompt the user for forget password as already registered
                        appSettings.setPassword(pw);
                        appSettings.setName(u_n);

                        Toast.makeText(getApplicationContext(), "Name = :"+appSettings.getName()+
                                " pswd : "+appSettings.getPassword(), Toast.LENGTH_LONG).show();


                        if (temp)
                        {
                            Intent getIntent = new Intent(signup.this, login_page.class);
                            getIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(getIntent);
                            finish(); // call this to finish the current activity
                        }

                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Exception Caught : "+
                            e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
    };
}
