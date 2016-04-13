package com.example.dell.helloandroid.SignUp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.helloandroid.DataBase.Contact;
import com.example.dell.helloandroid.LocationServices_Entity.MapsMainActivity;
import com.example.dell.helloandroid.R;
import com.example.dell.helloandroid.Startup_Pages.login_page;

import java.util.List;

public class signup extends login_page {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button app_signup = (Button) findViewById(R.id.signup_create);
        final EditText User_Name = (EditText) findViewById(R.id.new_user_name);
        final EditText Password = (EditText) findViewById(R.id.new_password);

/*        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        Contact omer = new Contact("Omer", "9100000000");
        database.addContact(omer);
        database.addContact(new Contact("Bilal", "9199999999"));
        database.addContact(new Contact("Osama", "9522222222"));
        database.addContact(new Contact("Shoaib", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = database.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
*/
//        database.delete_all_contacts();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        app_signup.setOnClickListener(new View.OnClickListener() {

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
        });
    }

}
