package com.example.dell.helloandroid.Startup_Pages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.helloandroid.LocationServices_Entity.MapsMainActivity;
import com.example.dell.helloandroid.R;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Button fb_login = (Button) findViewById(R.id.fb_login);
        Button gm_login = (Button) findViewById(R.id.gm_login);
        Button app_login = (Button) findViewById(R.id.app_login);
        final EditText User_Name = (EditText) findViewById(R.id.user_name);
        final EditText Password = (EditText) findViewById(R.id.password);

        toolbar.setTitle("IdeaMeter");
//        toolbar.setBackgroundColor(Color.parseColor("#FF64FFB7"));
        toolbar.inflateMenu(R.menu.login_menu);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item click event
                        switch (item.getItemId()) {
                            //Change the ImageView image source depends on menu item click
                            case R.id.about_idealojy:
                                Intent getIntent = new Intent(login_page.this, about_idealojy.class);

                                // Use the Intent to start Google Maps application using Activity.startActivity()
                                startActivity(getIntent);
                                return true;
                            case R.id.about_ideaMeter:
                                return true;
                            case R.id.meter_settings:
                                return true;
                        }
                        return true;
                    }
                });

        fb_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    String u_n = User_Name.getText().toString();
                    String pw = Password.getText().toString();

                    if(User_Name.getText().toString().equals("idealojy") && Password.getText().toString().equals("12345"))
                    {
                        Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                        // Use the Intent to start Google Maps application using Activity.startActivity()
                        startActivity(getIntent);
                    }
                    else
                    {
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(login_page.this);

                        dlgAlert.setMessage("wrong password or username");
                        dlgAlert.setTitle("Error Message...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }

                } catch (Exception e) {

                }
            }
        });

        gm_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {

                    if(User_Name.getText().toString().equals("idealojy") && Password.getText().toString().equals("12345"))
                    {
                        Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                        // Use the Intent to start Google Maps application using Activity.startActivity()
                        startActivity(getIntent);
                    }
                    else
                    {
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(login_page.this);

                        dlgAlert.setMessage("wrong password or username");
                        dlgAlert.setTitle("Error Message...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }

                } catch (Exception e) {

                }
            }
        });

        app_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {

                    if(User_Name.getText().toString().equals("idealojy") && Password.getText().toString().equals("12345"))
                    {
                        Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                        // Use the Intent to start Google Maps application using Activity.startActivity()
                        startActivity(getIntent);
                    }
                    else
                    {
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(login_page.this);

                        dlgAlert.setMessage("wrong password or username");
                        dlgAlert.setTitle("Error Message...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    }

                } catch (Exception e) {

                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Intent intent = ((Activity) login_page.this).getIntent();


    }

}
