package com.idea.meter.Main_Entity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.idea.meter.Meter_Utils.CustomMeterAdapter;
import com.idea.meter.R;

public class AddRemoveMetersActivity extends AppCompatActivity {

    ListView lv;
    Context context;
    public static String [] meterNameList = {"Meter 1", "Meter 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_meters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        lv = (ListView) findViewById(R.id.meterlistView);

        ListAdapter custom = new CustomMeterAdapter(this, meterNameList);

        lv.setAdapter(custom);

    }


}
