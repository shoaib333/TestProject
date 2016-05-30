package com.example.dell.helloandroid.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dell.helloandroid.Main_Entity.MainActivity;

/**
 * Created by hp on 5/28/2016.
 */
public class Settings extends MainActivity{
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String ID = "id";
    public static final String NUMBER = "phoneNumber";
    public static final String EMAIL = "email";
    public static final String AUTOCALLENABLE = "isAutoCallEnable";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    //public SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);


    public SharedPreferences settings;
    public SharedPreferences.Editor editor;
    //public SharedPreferences.Editor editor = settings.edit();



    //private variables
    String _id;
    String _name;
    String _phone_number;
    String _autoCallEnable;
    String _token;
    String _email;

    // Empty constructor
    public Settings(Context context){
        settings = PreferenceManager.getDefaultSharedPreferences(context);
//
//        settings = getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);
        editor = settings.edit();
        //InitializeSharedPreferences();
    }
    // constructor
    public Settings(String id, String _name, String _phone_number, String _autoCallEnable, String _token, String _email){

        //TODO: not required?
        this._id = id;
        this._name = _name;
        this._phone_number = _phone_number;
        this._autoCallEnable =_autoCallEnable;
        this._email = _email;
        this._token = _token;

        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //SharedPreferences.Editor editor = settings.edit();
        //Initializing the shared preferences
        editor.putString(ID, id);
        editor.putString(NAME, _name);
        editor.putString(EMAIL, _email);
        editor.putString(NUMBER, _phone_number);
        editor.putBoolean(AUTOCALLENABLE, false);//TODO; convert string to boolean
        editor.putString(TOKEN, _token);
        // Commit the edits!
       // editor.commit();
        editor.apply();

    }

    // constructor
    public Settings(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
        this._autoCallEnable = "None";
        this._email = "None";
        this._token = "None";
    }


    public void InitializeSharedPreferences(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);

        //settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        // Restore preferences
        //Initializing the shared preferences
        editor.putString(ID, "None");
        editor.putString(NAME, "None");
        editor.putString(EMAIL, "None");
        editor.putString(NUMBER, "None");
        editor.putBoolean(AUTOCALLENABLE, false);
        editor.putString(TOKEN, "None");
        editor.apply();
    }

   // getting ID
    public String getID(){
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(ID, "None");
    }

    // setting id
    public void setID(String id){
        editor.putString(ID, id);
        // Commit the edits!
        //editor.commit();
        editor.apply();
        this._id = id;//TODO remove this
    }

    // getting Email
    public String get_email(){
        return settings.getString(EMAIL, "None");
    }

    // setting Email
    public void set_email(String email){
        editor.putString(EMAIL, email);
        // Commit the edits!
        //editor.commit();
        editor.apply();
        this._email = email;//
    }

    // getting AutoCallEnable
    public String get_autoCallEnable(){
        return settings.getString(AUTOCALLENABLE, "None");
    }

    // setting AutoCallEnable
    public void set_autoCallEnable(String autoCallEnable){
        editor.putString(AUTOCALLENABLE, autoCallEnable);
        // Commit the edits!
        //editor.commit();
        editor.apply();
        this._autoCallEnable = autoCallEnable;//
    }


    // getting AutoCallEnable
    public String get_token(){
        return settings.getString(TOKEN, "None");
    }

    // setting AutoCallEnable
    public void set_token(String token){
        editor.putString(TOKEN, token);
        // Commit the edits!
        //editor.commit();
        editor.apply();
        this._token = token;//
    }

    // getting name
    public String getName(){
        return settings.getString(NAME, "None");
    }

    // setting name
    public void setName(String name){
        editor.putString(NAME, name);
        // Commit the edits!
       // editor.commit();
        editor.apply();
        this._name = name;//
    }

    // getting phone number
    public String getPhoneNumber(){
        return settings.getString(NUMBER, "None");
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        editor.putString(NUMBER, phone_number);
        // Commit the edits!
        //editor.commit();
        editor.apply();
        this._phone_number = phone_number;//
    }


    // getting phone number
    public String getPassword(){
        return settings.getString(PASSWORD, "None");
    }

    // setting phone number
    public void setPassword(String password){
        editor.putString(PASSWORD, password);
        // Commit the edits!
       // editor.commit();
        editor.apply();
    }
}