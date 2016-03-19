package com.example.dell.helloandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

/**
 * Created by DELL on 3/17/2016.
 */

public class BTConnectThread extends Thread {
    private BluetoothSocket mBluetoothSocket;
    private final BluetoothDevice mDevice;
    private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private final Handler mHandler;

public BTConnectThread(String deviceID, Handler handler) {

        /* Obtain the device ID for the device whose MAC address was chosen */
        mDevice = mBluetoothAdapter.getRemoteDevice(deviceID);

        /* Save the handler for this Task */
        mHandler = handler;

        try {

            /* Create a Bluetooth Socket that connects to the selected */
            mBluetoothSocket = mDevice
                    .createRfcommSocketToServiceRecord(showBTdevices.APP_UUID);

        } catch (IOException e) {

            /* Dump all the StackTrace on Error Log */
            e.printStackTrace();
        }
    }

    public void run() {

        if(mBluetoothAdapter.isDiscovering()) {
            /* Stop Bluetooth Search because the device has already been discovered */
            mBluetoothAdapter.cancelDiscovery();
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    /* Add the code which is to be delayed for 1000 milliseconds */
                try {

                    /* Connect the Socket */
                            mBluetoothSocket.connect();

                    /* Do all Socket Management */
                            manageConnectedSocket();

                    } catch (IOException connectException) {

                    /* Handle all Bluetooth Conenction Exceptions here */
                    try {

                            /* Close Bluetooth Connection */
                            mBluetoothSocket.close();

                            Log.e("BTConnectThread Error", connectException.toString());

                        } catch (IOException e) {

                        /* Dump all the StackTrace on Error Log */
                        e.printStackTrace();

                        }
                    }

            }
        },1000 );

    }

    private void manageConnectedSocket() {

        /* Create a Connection Thread to Handle the lower Socket level communication */
        BTConnectionThread conn = new BTConnectionThread(mBluetoothSocket, mHandler);

        /* Send Connected Socket back to the calling Activity */
        mHandler.obtainMessage(MainActivity.SOCKET_CONNECTED, conn).sendToTarget();

        /* Start the socket communication */
        conn.start();
    }

    public void cancel() {
        try {

            /* Close the Bluetooth connection */
            mBluetoothSocket.close();

        } catch (IOException e) {

        }
    }

}
