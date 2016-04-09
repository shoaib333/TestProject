package com.example.dell.helloandroid.LocationServices_Entity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.helloandroid.Main_Entity.MainActivity;
import com.example.dell.helloandroid.R;
import com.example.dell.helloandroid.SMS_Entity.SmsBroadcastReceiver;
import com.example.dell.helloandroid.Startup_Pages.about_idealojy;
import com.example.dell.helloandroid.Startup_Pages.login_page;

public class MapsMainActivity extends AppCompatActivity {

    private TextView myAccuracyView;
    private TextView myTimeView;
    private TextView myLatView;
    private TextView myLongView;

    private Location myBestReading;
    private LocationManager myLocationManager;
    private LocationListener myLocationListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        /* Restore any previous saved state */
        super.onCreate(savedInstanceState);

        /* Set Layout of the App */
        setContentView(R.layout.activity_main);

        final Toolbar toolbar_main = (Toolbar) findViewById(R.id.toolbar);

        /* Initialize UI elements */
        final EditText source_addrText = (EditText) findViewById(R.id.source_location);
        final EditText destination_addrText = (EditText) findViewById(R.id.destination_location);

        /* Create a Button */
        final Button map_button = (Button) findViewById(R.id.mapButton);

        final Button BT_button = (Button) findViewById(R.id.btButton);

        final Button sms_button = (Button) findViewById(R.id.smsButton);

        final SmsBroadcastReceiver smsBroadcastReceiver = new SmsBroadcastReceiver();

        toolbar_main.setTitle("IdeaMeter");
        toolbar_main.inflateMenu(R.menu.personal_screen);

        toolbar_main.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        /* Handle menu item click event */
                        switch (item.getItemId()) {

                            /* Change the ImageView image source depends on menu item click */
                            case R.id.logout:

                                Intent getIntent = new Intent(MapsMainActivity.this, login_page.class);
                                getIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(getIntent);
                                finish(); /* call this to finish the current activity */

                                return true;
                        }
                        return true;
                    }
                });

        map_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    String source_address = source_addrText.getText().toString();
                    String destination_address = destination_addrText.getText().toString();

                    source_address = source_address.replace(' ', '+');
                    destination_address = destination_address.replace(' ', '+');
                    Intent getIntent = new Intent(MapsMainActivity.this, MapsActivity.class);

                    /* Use the Intent to start Google Maps application using Activity.startActivity() */
                    startActivity(getIntent);

                } catch (Exception e) {

                }
            }
        });

        BT_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent getIntent = new Intent(MapsMainActivity.this, MainActivity.class);

                    /* Start the Main Activity to Start BT Service */
                    startActivity(getIntent);
                } catch (Exception e) {

                }
            }
        });

        sms_button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                try{
                    Intent getIntent = new Intent(MapsMainActivity.this, SmsBroadcastReceiver.class);

                    /* Register SmsBroadcast Receiver to Read SMS */
                    registerReceiver(smsBroadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

                    Toast.makeText(getBaseContext(), "SMS Reading On", Toast.LENGTH_SHORT).show();
                } catch (Exception e){

                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

