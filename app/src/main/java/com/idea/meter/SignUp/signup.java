package com.idea.meter.SignUp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabListener);

        app_signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });
    }


    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };
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
                    List<Contact> contacts = database.getAllContacts();
                    boolean found_flag = false;

                    for (Contact cn : contacts) {
                        String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                        if(User_Name.getText().toString().equals(cn.getName()))
                        {
                            found_flag = true;
                            break;
                        }
                    }
                    if (found_flag == false) {
                        database.addContact(new Contact(u_n, pw));
                        Intent getIntent = new Intent(signup.this, login_page.class);
                        getIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(getIntent);
                        finish(); // call this to finish the current activity
                    }
                    else
                    {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(signup.this);

                        dlgAlert.setMessage("Choose another username");
                        dlgAlert.setTitle("Username already exists");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }
                }

            } catch (Exception e) {

            }
        }
    };

}
