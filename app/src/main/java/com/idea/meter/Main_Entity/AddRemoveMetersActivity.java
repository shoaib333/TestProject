package com.idea.meter.Main_Entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.idea.meter.Meter_Utils.CustomMeterAdapter;
import com.idea.meter.Meter_Utils.MeterStatsActivity;
import com.idea.meter.R;

public class AddRemoveMetersActivity extends AppCompatActivity {

    ListView lv;
    Context context;
    public static String [] meterNameList = {"Meter 1", "Meter 2"};

    public static final int DEVICE_SELECTED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_meters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        lv = (ListView) findViewById(R.id.meterlistView);

        /* TODO: Get All the currently saved meters from the Saved List */

        ListAdapter custom = new CustomMeterAdapter(this, meterNameList, mDeviceSelectHandler);

        lv.setAdapter(custom);



    }


    public Handler mDeviceSelectHandler =   new Handler() {

        /* When Task Handler receives an Event */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DEVICE_SELECTED:{

                    String data = msg.obj.toString();

                    /* TODO: Different Meters to be shown based on Meters Selected */

                    Intent deviceStat = new Intent(AddRemoveMetersActivity.this, MeterStatsActivity.class);

                    startActivity(deviceStat);

                }

                default:
                    break;
            }
        }
    };

}
