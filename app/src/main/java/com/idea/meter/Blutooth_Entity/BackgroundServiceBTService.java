package com.idea.meter.Blutooth_Entity;

/* Android Imports*/
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundServiceBTService extends Service {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    /*default constructor*/
    public BackgroundServiceBTService() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /* Start a Service to run application in the background.*/
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Toast.makeText(this, " Application Service Started", Toast.LENGTH_LONG).show();

        if (mBluetoothAdapter == null) {
            /*Device does not support Bluetooth*/
            Toast.makeText(this, "Device Does not support Bluetooth", Toast.LENGTH_LONG).show();
        } else {

            /*Check if Bluetooth is enabled*/
            if (!mBluetoothAdapter.isEnabled()) {

                /*Enable Bluetooth Adaptor as it is in OFF State*/
                mBluetoothAdapter.enable();

                /*Display message for turning Bluetooth ON*/
                Toast.makeText(getApplicationContext(), "Bluetooth is NOT Enabled, turning it ON", Toast.LENGTH_SHORT).show();
            }
            /*BlueTooth is Already Enabled*/
            else {
                Toast.makeText(getApplicationContext(), "Bluetooth is already Enabled", Toast.LENGTH_SHORT).show();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        /*Kill Application if the service is stopped by user*/
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}
