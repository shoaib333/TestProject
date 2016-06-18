package com.idea.meter.LocationServices_Entity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.meter.Main_Entity.AddRemoveMetersActivity;
import com.idea.meter.Main_Entity.MainActivity;
import com.idea.meter.R;
import com.idea.meter.SMS_Entity.SmsBroadcastReceiver;
import com.idea.meter.Startup_Pages.login_page;

public class MapsMainActivity extends AppCompatActivity {

    private TextView myAccuracyView;
    private TextView myTimeView;
    private TextView myLatView;
    private TextView myLongView;

    Button map_button;
    Button BT_button;
    Button sms_button;
    Button arDevice_Button;

    EditText source_addrText;
    EditText destination_addrText;
    Toolbar toolbar_main;

    SmsBroadcastReceiver smsBroadcastReceiver;

    private Location myBestReading;
    private LocationManager myLocationManager;
    private LocationListener myLocationListener;

    /* Request Codes for Permissions */
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    final int MY_PERMISSIONS_REQUEST_READ_SMS = 2;
    final int MY_PERMISSIONS_REQUEST_BLUETOOTH = 3;
    final int MY_PERMISSIONS_REQUEST_BLUETOOTH_MANAGER = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        /* Restore any previous saved state */
        super.onCreate(savedInstanceState);

        /* Set Layout of the App */
        setContentView(R.layout.activity_main);

        toolbar_main = (Toolbar) findViewById(R.id.toolbar);

        /* Initialize UI elements */

//        map_button = (Button) findViewById(R.id.mapButton);
        BT_button = (Button) findViewById(R.id.btButton);
        arDevice_Button = (Button) findViewById(R.id.addRemDeviceButton);

//        sms_button = (Button) findViewById(R.id.smsButton);
        smsBroadcastReceiver = new SmsBroadcastReceiver();

        toolbar_main.setTitle("ideaMeter");
        toolbar_main.inflateMenu(R.menu.personal_screen);

        /* Set onClickListeners here */
        toolbar_main.setOnMenuItemClickListener(toolListener);

//        map_button.setOnClickListener(mapListener);

        BT_button.setOnClickListener(BT_Listener);
        arDevice_Button.setOnClickListener(arDevice_Listener);

//        sms_button.setOnClickListener(smsListener);
    }

    Toolbar.OnMenuItemClickListener toolListener = new Toolbar.OnMenuItemClickListener() {
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
    };


    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener smsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                /* Check if Marshmallow or greater */
                if (Build.VERSION.SDK_INT >= 23){

                    /* Check if Permission is not available */
                    if (ContextCompat.checkSelfPermission(MapsMainActivity.this,
                            Manifest.permission.READ_SMS)
                            != PackageManager.PERMISSION_GRANTED) {

                        /* TODO: To display custom dialogue box this API call cant be used
                        *  see more details here: https://developer.android.com/training/permissions/requesting.html
                        */

                        /* Request the permission */

                        ActivityCompat.requestPermissions(MapsMainActivity.this,
                                new String[]{Manifest.permission.READ_SMS},
                                MY_PERMISSIONS_REQUEST_READ_SMS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.

                    }
                }


                Intent getIntent = new Intent(MapsMainActivity.this, SmsBroadcastReceiver.class);

                /* Register SmsBroadcast Receiver to Read SMS */
                registerReceiver(smsBroadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

                Toast.makeText(getBaseContext(), "SMS Reading On", Toast.LENGTH_SHORT).show();

            } catch (Exception e){

            }
        }
    };

    View.OnClickListener BT_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

             /* Check if Marshmallow or greater */
                if (Build.VERSION.SDK_INT >= 23){

                    /* Check if Permission is not available */
                    if (ContextCompat.checkSelfPermission(MapsMainActivity.this,
                            Manifest.permission.BLUETOOTH)
                            != PackageManager.PERMISSION_GRANTED) {

                        /* TODO: To display custom dialogue box this API call cant be used
                        *  see more details here: https://developer.android.com/training/permissions/requesting.html
                        */

                        /* Request the permission */

                        ActivityCompat.requestPermissions(MapsMainActivity.this,
                                new String[]{Manifest.permission.BLUETOOTH},
                                MY_PERMISSIONS_REQUEST_BLUETOOTH);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.

                    }

                    /* Check if Permission is not available */
                    if (ContextCompat.checkSelfPermission(MapsMainActivity.this,
                            Manifest.permission.BLUETOOTH_ADMIN)
                            != PackageManager.PERMISSION_GRANTED) {

                        /* TODO: To display custom dialogue box this API call cant be used
                        *  see more details here: https://developer.android.com/training/permissions/requesting.html
                        */

                        /* Request the permission */

                        ActivityCompat.requestPermissions(MapsMainActivity.this,
                                new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                                MY_PERMISSIONS_REQUEST_BLUETOOTH);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.

                    }

                }

                Intent getIntent = new Intent(MapsMainActivity.this, MainActivity.class);

                startActivity(getIntent);
            } catch (Exception e) {

            }
        }
    };

    View.OnClickListener arDevice_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{

                Intent getIntent = new Intent(MapsMainActivity.this, AddRemoveMetersActivity.class);

                startActivity(getIntent);

            } catch (Exception e){

            }
        }
    };

    View.OnClickListener mapListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            try {
                String source_address = source_addrText.getText().toString();
                String destination_address = destination_addrText.getText().toString();

                source_address = source_address.replace(' ', '+');
                destination_address = destination_address.replace(' ', '+');
                Intent getIntent = new Intent(getApplicationContext(), MapsActivity.class);
                getIntent.putExtra("source",source_address+" ");
                getIntent.putExtra("destination", destination_address+" ");
                    /* Use the Intent to start Google Maps application using Activity.startActivity() */
                startActivity(getIntent);

            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                /* If request is cancelled, the result arrays are empty. */
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    /* permission was granted, yay! Do the
                     * task you need to do.
                     */
                } else {

                    /* permission denied, Disable the
                     * functionality that depends on this permission.
                     */
                    Toast.makeText(getBaseContext(), "Permission not Granted for Reading of SMS", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                /* If request is cancelled, the result arrays are empty. */
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /* permission was granted, yay! Do the
                     * contacts-related task you need to do.
                     */
                } else {

                    /* permission denied, Disable the
                     * functionality that depends on this permission.
                     */
                    Toast.makeText(getBaseContext(), "Permission not Granted for Contacts Reading", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_BLUETOOTH: {
                /* If request is cancelled, the result arrays are empty. */
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /* permission was granted, yay! Do the
                     * contacts-related task you need to do.
                     */
                } else {

                    /* permission denied, Disable the
                     * functionality that depends on this permission.
                     */
                    Toast.makeText(getBaseContext(), "Permission not Granted for Bluetooth", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_BLUETOOTH_MANAGER: {
                /* If request is cancelled, the result arrays are empty. */
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /* permission was granted, yay! Do the
                     * contacts-related task you need to do.
                     */
                } else {

                    /* permission denied, Disable the
                     * functionality that depends on this permission.
                     */
                    Toast.makeText(getBaseContext(), "Permission not Granted for Bluetooth Admin", Toast.LENGTH_SHORT).show();

                }
                return;
            }

            /* other 'case' lines to check for other
             * permissions this app might request
             */
        }
    }

}
