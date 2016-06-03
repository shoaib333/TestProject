package com.idea.meter.Blutooth_Entity;

/*Android Imports*/
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.idea.meter.R;
import java.util.List;
import java.util.UUID;


/*************************************************************/
/* Class to Search and display available Bluetooth Devices   */
/*************************************************************/

public class ShowBTdevices extends ListActivity {
    BluetoothAdapter mBluetoothAdapter = null;
    ArrayAdapter<String> mArrayAdapter = null;
    ListView lv;
    TextView footer;
    List<String> devices;

    /* Generic SPP device UUID */
    public static final UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            /* Discovery finds a device*/
            if (BluetoothDevice.ACTION_FOUND.equals(action)){

                /* Get the BluetoothDevice object from the Intent */
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_DEVICE, Short.MIN_VALUE);

                /* Add the name and address to an array adapter to show in a ListView */
                System.out.println(device.getName());
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress() + "\n" + rssi);
                Log.d("Device name:", device.getName());
            }

            /* Register the BroadcastReceiver*/
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mReceiver, filter);
            /* TODO: Don't forget to unregister during onDestroy*/
            /* TODO: onDestroy implementation is missing*/
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create Bluetooth Adapter */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//        setContentView(R.layout.bluetooth_device_layout);

        /* Get ListView that will be bound to the ListView in btview.xml file*/
        lv = (ListView) getListView();
        footer = new TextView(this);
        footer.setText("Discover More Devices");
        lv.setFooterDividersEnabled(true);
        lv.addFooterView(footer, null, true);

        /* Create List to save devices found by Bluetooth */
        devices = getIntent().getStringArrayListExtra("devices");

        /* Create ArrayAdapter that bounds the Bluetooth device list with the ListView of btview.xml */
        mArrayAdapter = new ArrayAdapter<String>(this, R.layout.btview,devices);

        /* Set this adapter as the list Adapter */
        setListAdapter(mArrayAdapter);

        /* Implement the onClickListener for the List Adapter */
        getListView().setOnItemClickListener(clickListener);
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            if (parent.getAdapter().getItemViewType(pos) == AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {

                    /* Start Bluetooth Discovery */
                    mBluetoothAdapter.startDiscovery();

                } else {

                    /* User Presses a Bluetooth Device present on the List */
                    String tmp = (String) parent.getItemAtPosition(pos);

                    /* Save the details of the Bluetooth device */
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(tmp.split("\n")[1]);

                    /* Create a new Intent in which the device will be sent back */
                    Intent data = new Intent();

                    /* Add the Bluetooth Device in the Intent created */
                    data.putExtra(BluetoothDevice.EXTRA_DEVICE, device);

                    setResult(RESULT_OK, data);

                    /* Stop Bluetooth Search because the device has already been discovered */
                    mBluetoothAdapter.cancelDiscovery();

                    /* Finish this Activity so that the result from the calling Activity can be sent */
                    finish();
                }

            } /* onClick */

    };
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    protected void onPause() {

        unregisterReceiver(mReceiver);
        super.onPause();
    }

    /*TODO: Implement onDestroy function*/
}
