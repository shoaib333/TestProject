package com.example.dell.helloandroid.SMS_Entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.example.dell.helloandroid.MeterUtils;


public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    /* Create BroadCast action which will be sent from this Activity to notify of message being received */
    public static final String BROADCAST_ACTION = "com.example.dell.helloandroid.SMS_RECEIVED";
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {

            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String smsBody = "";
            String address = "";

            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
/*            SmsActivity inst = SmsActivity.instance();
            inst.updateList(smsMessageStr);*/

            /*Search the name in the directory*/
            MeterUtils utils = new MeterUtils();
            String name = utils.getContactName(address, context);

            Toast.makeText(context, ("received message contact name is "+name), Toast.LENGTH_SHORT).show();

            /* send smsBroadcast */
            sBroadcast(context, intent, address, smsBody);

        }
    }


    public void sBroadcast(Context context,Intent intent, String address, String body){

        /* The Intent to be sent in the Broadcast */
        Intent smsBroadcast = new Intent(SmsBroadcastReceiver.BROADCAST_ACTION);

        /* Add the phone number with the Extra Tag "address" */
        smsBroadcast.putExtra("address",address);
        smsBroadcast.putExtra("body", body);

        /* Broadcast the Intent */
        LocalBroadcastManager.getInstance(context).sendBroadcast(smsBroadcast);
    }

}
