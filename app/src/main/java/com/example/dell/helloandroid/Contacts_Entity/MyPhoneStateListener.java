package com.example.dell.helloandroid.Contacts_Entity;

/* Android Imports*/
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.example.dell.helloandroid.Main_Entity.*;
import com.example.dell.helloandroid.Blutooth_Entity.*;

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


    public String getContactName(String phoneNumber)
    {
        String name = "Not found", number;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = this.context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();

        phoneNumber = removeSpaces(phoneNumber);

        do {
            number = people.getString(indexNumber);
            name = people.getString(indexName);

            number = removeSpaces(number);

            if(phoneNumber.equals(number)){
                name   = people.getString(indexName);

                Toast.makeText(context,"MATCH FOUND number = "+number+": Name = "+name,
                        Toast.LENGTH_LONG).show();

                break;
            }

        } while (people.moveToNext());

        return name;
    }

    private String removeSpaces(String stringToRemoveSpaces)
    {
        StringBuilder sb = new StringBuilder(stringToRemoveSpaces);
        /* Number received in + format, delete spaces in the number if any*/

        while(sb.indexOf(" ") >= 0)
        {
                /*delete space found in the number string*/
            sb.deleteCharAt(sb.indexOf(" "));
        }

        if(sb.indexOf("+") >= 0)
        {
            stringToRemoveSpaces = sb.toString();
        }
        /* Delete spaces in the number if any*/
        else
        {
            /*remove the first 0 present and add + country code in it*/
            sb.deleteCharAt(0);
            stringToRemoveSpaces = sb.toString();
            stringToRemoveSpaces = "+92"+stringToRemoveSpaces;
        }

        /*string with spaces removed*/
        return stringToRemoveSpaces;
    }
}
