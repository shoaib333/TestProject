package com.example.dell.helloandroid;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * Created by hp on 5/17/2016.
 */
public class MeterUtils {

    /*RC message codes*/
    public static final String RC_SMS = "01";
    public static final String RC_CALL = "02";
    public static final String RC_BLUETOOTH = "03";



    public String getContactName(String phoneNumber, Context context) {
        String name = "Not found", number;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor people = context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();

        phoneNumber = removeSpaces(phoneNumber);

        do {
            number = people.getString(indexNumber);
            name = people.getString(indexName);

            number = removeSpaces(number);

            if (phoneNumber.equals(number)) {
                name = people.getString(indexName);

                Toast.makeText(context, "MATCH FOUND number = " + number + ": Name = " + name,
                        Toast.LENGTH_LONG).show();

                break;
            }

        } while (people.moveToNext());

        return name;
    }

    private String removeSpaces(String stringToRemoveSpaces) {
        StringBuilder sb = new StringBuilder(stringToRemoveSpaces);
        /* Number received in + format, delete spaces in the number if any*/

        while (sb.indexOf(" ") >= 0) {
                /*delete space found in the number string*/
            sb.deleteCharAt(sb.indexOf(" "));
        }

        if (sb.indexOf("+") >= 0) {
            stringToRemoveSpaces = sb.toString();
        }
        /* Delete spaces in the number if any*/
        else {
            /*remove the first 0 present and add + country code in it*/
            sb.deleteCharAt(0);
            stringToRemoveSpaces = sb.toString();
            stringToRemoveSpaces = "+92" + stringToRemoveSpaces;
        }

        /*string with spaces removed*/
        return stringToRemoveSpaces;
    }

    /*Tis function will be used to format the string that is to be sent to meter via bluetooth
    * in by added codes*/
    public String getFormattedString(String name, String number, String message, String RCCode)
    {

        /*
            Agreed Response Codes
            Response Codes For Services:
            SMS: 01, name, no, and message
            Call: 02 , name and no
            Bluetooth: 03... TODO add cases

            Incoming TODO
            Speed: 06, TODO
            Distance: 04 TODO
            RPM: 05 TODO
            Bluetooth: 03... TODO add cases
            Fuel: 07 ... TODO
            Engine Oil: 08 TODO

            Sample message
            <RCNumber>;<ContactName>;<ContactNUmber>;<SMS>
        */

        switch (RCCode)
        {
            case RC_SMS:
                return RCCode+":"+name+":"+number+":"+message+"\r\n";
            case RC_CALL:
                return RCCode+":"+name+":"+number+"\r\n";
            case RC_BLUETOOTH:
                return RCCode+":"+name+":"+number+"\r\n";
            default:
                return "00:00:00\r\n";
        }
    }
}
