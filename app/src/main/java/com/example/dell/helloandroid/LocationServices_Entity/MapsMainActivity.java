package com.example.dell.helloandroid.LocationServices_Entity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.dell.helloandroid.Main_Entity.MainActivity;
import com.example.dell.helloandroid.R;

public class MapsMainActivity extends Activity{

    private TextView myAccuracyView;
    private TextView myTimeView;
    private TextView myLatView;
    private TextView myLongView;

    private Location myBestReading;
    private LocationManager myLocationManager;
    private LocationListener myLocationListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        /* Restore any previous saved state */
        super.onCreate(savedInstanceState);

        /* Set Layout of the App */
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        final EditText source_addrText = (EditText) findViewById(R.id.source_location);
        final EditText destination_addrText = (EditText) findViewById(R.id.destination_location);

        /* Create a Button */
        final Button map_button = (Button) findViewById(R.id.mapButton);

		final Button BT_button = (Button) findViewById(R.id.btButton);

        map_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    String source_address = source_addrText.getText().toString();
                    String destination_address = destination_addrText.getText().toString();

                    source_address = source_address.replace(' ', '+');
                    destination_address = destination_address.replace(' ', '+');
                    Intent getIntent = new Intent(MapsMainActivity.this,MapsActivity.class);

                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    startActivity(getIntent);

                } catch (Exception e) {

                }

            }
        });

        BT_button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                try {
                    Intent getIntent = new Intent(MapsMainActivity.this,MainActivity.class);

                    //Start the Main Activity to Read SMS and Start BT Service
                    startActivity(getIntent);
                }
                catch (Exception e) {

                }
            }
        });
    }

}
