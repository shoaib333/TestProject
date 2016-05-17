package com.example.dell.helloandroid.Contacts_Entity;

/* Android Imports*/
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.example.dell.helloandroid.Main_Entity.*;
import com.example.dell.helloandroid.Blutooth_Entity.*;
import com.example.dell.helloandroid.MeterUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;
/*************************************************************/
/*  This class is responsible for getting the phone state
*   whenever a call is detected, either outgoing or incoming */
/*************************************************************/

public class MyPhoneStateListener extends PhoneStateListener {

    private static final String TAG = "CustomPhoneStateLstner";
    private final Handler mHandler;

    Context context;

    /* Constructor to set the context of the application */
    public MyPhoneStateListener(Context context, Handler handler)
    {
        /* Setting the Context of the application, will be used for displaying toast*
        TODO: remove Context related code as it is not required in the final code/
         */
        this.context=context;

        /* Message Handler of the UI thread to which the Conctact Information will be sent to*/
        mHandler = handler;

    }

    /* Function that is used to detect any change in the phone state while calling or Idle */
    public void onCallStateChanged(int state, String incomingNumber){

        /* Check if the phone is ringing */
        if(state==TelephonyManager.CALL_STATE_RINGING){

            /* TODO: Retrieve Contact details from the phone number */
            String name = "Not in Contacts";

            MeterUtils utils = new MeterUtils();
            name = utils.getContactName(incomingNumber, context);

            /* Send Connected Socket back to the calling Activity */
            mHandler.obtainMessage(MainActivity.CALL_RECEIVED, name).sendToTarget();


            /* Temporary display */
            Toast.makeText(context,"Phone is Ringing : number = "+incomingNumber+": Name = "+name,
                    Toast.LENGTH_LONG).show();


        }
        /* check if the user has picked the call */

        /* TODO: Forward the information of call duration over Bluetooth */
        if(state==TelephonyManager.CALL_STATE_OFFHOOK){
            Toast.makeText(context,"Phone in a call or call picked",
                    Toast.LENGTH_LONG).show();
        }

        if(state==TelephonyManager.CALL_STATE_IDLE){
            /* phone is neither ringing nor in a call */
            /* Call terminated? */
        }
    }
}
