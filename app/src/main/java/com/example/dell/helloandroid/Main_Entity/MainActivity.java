package com.example.dell.helloandroid.Main_Entity;


/*Android Imports*/
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.helloandroid.Blutooth_Entity.BackgroundServiceBTService;
import com.example.dell.helloandroid.Blutooth_Entity.BTConnectThread;
import com.example.dell.helloandroid.Blutooth_Entity.BTConnectionThread;
import com.example.dell.helloandroid.Contacts_Entity.MyPhoneStateListener;
import com.example.dell.helloandroid.R;
import com.example.dell.helloandroid.Blutooth_Entity.ShowBTdevices;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter = null;
    private BTConnectionThread mBluetoothConnection = null;

    Set<BluetoothDevice> pairedDevices;

    private ArrayList<String> discoverableDeviceList;

    private String data = "";

    private boolean btConnected = false;

    /* String for Debug Log */
    private static final String TAG = "Bluetooth Activity";

    /* Codes used between BT activities for different commands */
    private static final int REQUEST_ENABLE_BT = 0;
    public static final int REQUEST_BLUETOOTH = 1;
    public static final int CALL_RECEIVED = 2;
    public static final int DATA_RECEIVED = 3;
    public static final int SOCKET_CONNECTED = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /* Check if even Bluetooth is available or not */
        if (mBluetoothAdapter == null) {
            Log.i(TAG, "Bluetooth not supported");
            finish();
        }


        setContentView(R.layout.bluetooth_device_layout);
        final Button BT_device_button = (Button) findViewById(R.id.BTdeviceButton);
        Toast.makeText(getApplicationContext(),"Application is turned ON", Toast.LENGTH_LONG).show();

        /* Setting Telephone manager listener to report any change in the phone state */
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(
                Context.TELEPHONY_SERVICE);

        MyPhoneStateListener callStateListener = new MyPhoneStateListener(this,mHandler);

        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        Toast.makeText(getApplicationContext(),"TELEPHONE MANAGER IS SET TO LISTEN", Toast.LENGTH_LONG).show();

        if (!mBluetoothAdapter.isEnabled()) {

            /* Create an Intent to Enable Bluetooth Communication */
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            /* Start Bluetooth Activity */
            startActivityForResult(enableBT, REQUEST_ENABLE_BT);

        }


//        startService(new Intent(getBaseContext(), BackgroundServiceBTService.class));


        BT_device_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pairedDevices = mBluetoothAdapter.getBondedDevices();

                discoverableDeviceList = new ArrayList<String>();

                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice bt : pairedDevices) {
                        discoverableDeviceList.add(bt.getName() + "\n" + bt.getAddress());
                    }
                }

                /* Create Intent to be passed to the BT device list */
                Intent showDevicesIntent = new Intent(MainActivity.this, ShowBTdevices.class);

                /* Add the BT device list Strings to the Intent */
                showDevicesIntent.putStringArrayListExtra("devices", discoverableDeviceList);

                /* Start the other Activity */
                startActivityForResult(showDevicesIntent, REQUEST_BLUETOOTH);

            }

        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            /* This is called when only the Bluetooth has been Enabled */

        }

        else if (requestCode == REQUEST_BLUETOOTH && resultCode == RESULT_OK) {
            /* This is called after a device has been selected from showBT devices */

            /* Save the data received from the Intent data */
            BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            /* Start the BTConnectThread */
            new BTConnectThread(device.getAddress(), mHandler).start();

        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public Handler mHandler = new Handler() {
        /* When Task Handler receives an Event */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOCKET_CONNECTED: {

                    Context context = getBaseContext();

                    data = "Device Connected to: " + msg.obj.toString();
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                    /* Set btConnected flag to true */
                    btConnected = true;

                    mBluetoothConnection = (BTConnectionThread) msg.obj;
                    mBluetoothConnection.write("this is a message".getBytes());

                    break;
                }
                case DATA_RECEIVED: {
                    Context context = getBaseContext();

                    data = msg.obj.toString();

                    /* TODO: -----------------> Add processing here that what is to be done of this recieved data <-------------- */
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                    mBluetoothConnection.write(data.getBytes());
                }
                case CALL_RECEIVED: {
                    Context context = getBaseContext();

                    /* Save the Contact name */
                    data = (String) msg.obj;

                    /* TODO: -----------------> Add processing here that what is to be done of this recieved contact information <-------------- */
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                    if (btConnected){

                        /* If BT is already connected with some device */
                        mBluetoothConnection.write(data.getBytes());
                    }
                }
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), BackgroundServiceBTService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), BackgroundServiceBTService.class));
    }




}
