package com.example.dell.helloandroid;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DELL on 3/18/2016.
 */

/* Class to Handle the Socket Layer of Bluetooth Connection */
public class BTConnectionThread extends Thread {

    /* Create BluetoothSocket */
    BluetoothSocket mBluetoothSocket;

    /* Create Handler to Handle Connection Thread */
    private final Handler mHandler;

    /* Streams for Input and Output of Bluetooth data */
    private InputStream mInStream;
    private OutputStream mOutStream;


    BTConnectionThread(BluetoothSocket socket, Handler handler){

        super();

        /* Assign the values that have been passed in the constructor */
        mBluetoothSocket = socket;
        mHandler = handler;

        try {

            /* Store the Input and Output Streams */
            mInStream = mBluetoothSocket.getInputStream();
            mOutStream = mBluetoothSocket.getOutputStream();

        } catch (IOException e) {
            /* Add Exception Handling here when needed */
        }
    }

    @Override
    public void run() {
        /* Main Thread that will run */

        /* Buffer to handle the data */
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {

                int availabledata = mInStream.available();

                /* If Input data is available */
                if (availabledata > 0)
                {

                    /* Read input stream for data recieved */
                    bytes = mInStream.read(buffer);
                    String data = new String(buffer, 0, bytes);

                    /* Return this recieved data back to the Calling Activity */
                    mHandler.obtainMessage(MainActivity.DATA_RECEIVED,data).sendToTarget();
                }

            } catch (IOException e) {

                break;

            }
        }
    }

    public void write(byte[] bytes) {
        try {

            /* Write any data recieved on Output Stream*/
            mOutStream.write(bytes);

        } catch (IOException e) {
        }
    }
}
