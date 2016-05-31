package com.idea.meter.Blutooth_Entity;

/*Android Imports*/
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import com.idea.meter.Main_Entity.MainActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/*************************************************************/
/* Class to Handle the Socket Layer of Bluetooth Connection */
/*************************************************************/

public class BTConnectionThread extends Thread {

    /* Create BluetoothSocket */
    BluetoothSocket mBluetoothSocket;

    /* Create Handler to Handle Connection Thread */
    private final Handler mHandler;

    /* Streams for Input and Output of Bluetooth data */
    private InputStream mInStream;
    private OutputStream mOutStream;


    /*BTConnectionThread constructor to set socket parameters*/
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
                    /* Read input stream for data received */
                    bytes = mInStream.read(buffer);
                    String data = new String(buffer, 0, bytes);

                    /* Return this received data back to the Calling Activity */
                    mHandler.obtainMessage(MainActivity.DATA_RECEIVED,data).sendToTarget();
                }

            } catch (IOException e) {
                break;
            }
        }
    }

    /*Function to write data on the Bluetooth output stream*/
    public void write(byte[] bytes) {
        try {

            /* Write any data received on Output Stream*/
            mOutStream.write(bytes);

        } catch (IOException e) {
        }
    }
}
