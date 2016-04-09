package com.example.dell.helloandroid.SMS_Entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

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
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
/*
            //this will update the UI with message
            SmsActivity inst = SmsActivity.instance();
            inst.updateList(smsMessageStr);*/

            /* send smsBroadcast */
            sBroadcast(context, intent);

        }
    }


    public void sBroadcast(Context context,Intent intent){

        /* The Intent to be sent in the Broadcast */
        Intent smsBroadcast = new Intent(SmsBroadcastReceiver.BROADCAST_ACTION);


        /* Broadcast the Intent */
        LocalBroadcastManager.getInstance(context).sendBroadcast(smsBroadcast);
    }

}
