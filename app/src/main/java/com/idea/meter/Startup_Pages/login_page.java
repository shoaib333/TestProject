package com.idea.meter.Startup_Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idea.meter.LocationServices_Entity.MapsMainActivity;
import com.idea.meter.R;
import com.idea.meter.Settings.Settings;
import com.idea.meter.SignUp.signup;

public class login_page extends AppCompatActivity {

    static int user_count=0;
    Button fb_login;
    Button gm_login;
    Button app_login;
    Button app_signup;
    Toolbar toolbar;
    EditText User_Name;
    EditText Password;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fb_login = (Button) findViewById(R.id.fb_login);
        gm_login = (Button) findViewById(R.id.gm_login);
        app_login = (Button) findViewById(R.id.app_login);
        app_signup = (Button) findViewById(R.id.app_signup);
        User_Name = (EditText) findViewById(R.id.user_name);
        Password = (EditText) findViewById(R.id.password);

        toolbar.setTitle("IdeaMeter");
//        toolbar.setBackgroundColor(Color.parseColor("#FF64FFB7"));
        toolbar.inflateMenu(R.menu.login_menu);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        /**
         * CRUD Operations
         * */

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
                            case R.id.clear_db:
                                //database.delete_all_contacts();
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
                    Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    startActivity(getIntent);

                } catch (Exception e) {

                }
            }
        });

        gm_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {

                    Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    startActivity(getIntent);

                } catch (Exception e) {

                }
            }
        });

        app_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {

                    Settings appSetting = new Settings(getApplicationContext());
                    if (User_Name.getText().toString().equals(appSetting.getName()) &&
                            Password.getText().toString().equals(appSetting.getPassword()))
                    {
                        Toast.makeText(getApplicationContext(),
                                "LOGIN SUCCESSFUL username = " + User_Name.getText().toString(),
                                Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplicationContext(), "Name = :"+appSetting.getName()+
                                " pswd : "+appSetting.getPassword(), Toast.LENGTH_LONG).show();

                        Intent getIntent = new Intent(login_page.this, MapsMainActivity.class);

                        // Use the Intent to start Google Maps application using Activity.startActivity()
                        startActivity(getIntent);

                        finish();//Finish this activity no going back when signed in 

                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),
                                "LOGIN UNSUCCESSFUL username = " + User_Name.getText().toString()+
                                ", stored params ="+appSetting.getName(),
                                Toast.LENGTH_LONG).show();


                        Toast.makeText(getApplicationContext(), "Name = :"+appSetting.getName()+
                                " pswd : "+appSetting.getPassword(), Toast.LENGTH_LONG).show();

                    }



                } catch (Exception e) {
                String a = "hello";
                }
            }
        });

        app_signup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                        Intent getIntent = new Intent(login_page.this, signup.class);

                        // Use the Intent to start Google Maps application using Activity.startActivity()
                        startActivity(getIntent);

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
