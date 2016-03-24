package com.example.dell.helloandroid.Contacts_Entity;

/* Android Imports*/
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.example.dell.helloandroid.Main_Entity.*;

import java.util.Objects;
/*************************************************************/
/*  This class is responsible for getting the phone state
*   whenever a call is detected, either outgoing or incoming */
/*************************************************************/

public class MyPhoneStateListener extends PhoneStateListener {

    private static final String TAG = "CustomPhoneStateLstner";

    Context context;

    /*Constructor to set the context of the application*/
    public MyPhoneStateListener(Context context)
    {
        /*Setting the Context of the application, will be used for displaying toast*
        TODO: remove Context related code as it is not required in the final code/
         */
        this.context=context;
    }

    /*Function that is used to detect any change in the phone state while calling or Idle*/
    public void onCallStateChanged(int state, String incomingNumber){

        /*Check if the phone is ringing*/
        if(state==TelephonyManager.CALL_STATE_RINGING){

            /*TODO: Retrieve Contact details from the phone number*/
            String name = "Not in Contacts";

            name = getContactName(incomingNumber);
             /*Temporary display*/
            Toast.makeText(context,"Phone is Ringing : number = "+incomingNumber+": Name = "+name,
                    Toast.LENGTH_LONG).show();


        }
        /*check if the user has picked the call*/
        /*TODO: Forward the information of call duration over Bluetooth*/
        if(state==TelephonyManager.CALL_STATE_OFFHOOK){
            Toast.makeText(context,"Phone in a call or call picked",
                    Toast.LENGTH_LONG).show();
        }
        if(state==TelephonyManager.CALL_STATE_IDLE){
            /*phone is neither ringing nor in a call*/
            /*Call terminated?*/
        }
    }


    public String getContactName(final String phoneNumber)
    {
        String name = "Not found", number;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = this.context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();

        StringBuilder sb = new StringBuilder(phoneNumber);
        sb.deleteCharAt(0);
        String tempNumberCaller = sb.toString();
        tempNumberCaller = "+92"+tempNumberCaller; //adding country code in it

        do {
            number = people.getString(indexNumber);
            name = people.getString(indexName);

            String tempNumberDirectory = number;
            StringBuilder sbt = new StringBuilder(tempNumberDirectory);
            sbt.deleteCharAt(3);
            sbt.deleteCharAt(6);

            tempNumberDirectory = sbt.toString();

            if(phoneNumber.equals(number) || tempNumberCaller.equals(tempNumberDirectory)){
                name   = people.getString(indexName);

                Toast.makeText(context,"MATCH FOUND number = "+number+": Name = "+name,
                        Toast.LENGTH_LONG).show();

                break;
            }

        } while (people.moveToNext());

        return name;
    }

}
