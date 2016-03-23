package com.example.dell.helloandroid.Contacts_Entity;

/*Imports*/
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/*************************************************************/
/*  This class is responsible for getting the phoen state
*   whenever a call is detected, either outgoing or incoming */
/*************************************************************/

public class MyPhoneStateListener extends PhoneStateListener {

    private static final String TAG = "CustomPhoneStateLstner";

    Context c;

    /*Constructor to set the context of the application*/
    public MyPhoneStateListener(Context context)
    {
        /*Setting the Context of the application, will be used for displaying toast*
        TODO: remove Context related code as it is not required in the final code/
         */
        c=context;
    }

    /*Function that is used to detect any change in the phone state while calling or Idle*/
    public void onCallStateChanged(int state, String incomingNumber){

        /*Check if the phone is ringing*/
        if(state==TelephonyManager.CALL_STATE_RINGING){

            /*TODO: Retrieve Contact details from the phone number*/

             /*Temporary display*/
            Toast.makeText(c,"Phone is Ringing : "+incomingNumber,
                    Toast.LENGTH_LONG).show();
        }
        /*check if the user has picked the call*/
        /*TODO: Forward information of call duration over Bluetooth*/
        if(state==TelephonyManager.CALL_STATE_OFFHOOK){
            Toast.makeText(c,"Phone in a call or call picked",
                    Toast.LENGTH_LONG).show();
        }
        if(state==TelephonyManager.CALL_STATE_IDLE){
            //phone is neither ringing nor in a call
        }
    }

}
