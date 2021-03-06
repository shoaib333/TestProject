package com.idea.meter.SMS_Entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.widget.Toast;
import com.idea.meter.MeterUtils;


public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    /* Create BroadCast action which will be sent from this Activity to notify of message being received */
    public static final String BROADCAST_ACTION = "com.idea.meter.SMS_RECEIVED";
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

            /*Get the formatted message from with RC code tag*/
            String smsRC = utils.getFormattedString(name, address, smsBody, utils.RC_SMS);

            Toast.makeText(context, ("received message contact name is "+name), Toast.LENGTH_SHORT).show();

            /* send smsBroadcast */
            sBroadcast(context, intent, smsRC);

        }
    }


    public void sBroadcast(Context context,Intent intent, String smsRC){

        /* The Intent to be sent in the Broadcast */
        Intent smsBroadcast = new Intent(SmsBroadcastReceiver.BROADCAST_ACTION);

        /* Add the formatted SMS with RC code to transmit it over bluetooth*/
        smsBroadcast.putExtra("smsRC",smsRC);

        /* Broadcast the Intent */
        LocalBroadcastManager.getInstance(context).sendBroadcast(smsBroadcast);
    }

}
