package com.idea.meter.LocationServices_Entity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class GetLocationActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();

    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long MEASURE_TIME = 1000 * 30;
    private static final long POLLING_FREQ = 1000 * 10;
    private static final long FASTES_UPDATE_FREQ = 1000 * 2;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;


    // Define an object that holds accuracy and frequency parameters
    LocationRequest mLocationRequest;

    // Views for display location information
//    private TextView mAccuracyView;
//    private TextView mTimeView;
//    private TextView mLatView;
//    private TextView mLngView;

//    private int mTextViewColor = Color.GRAY;

    // Current best location estimate
    private Location mBestReading;

//    private final String TAG = "LocationGetLocationActivity";

    private boolean mFirstUpdate = true;
//    private LocationClient mLocationClient;
    private Location mCurrentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!servicesAvailable())
            finish();

//        setContentView(R.layout.main);
//
//        mAccuracyView = (TextView) findViewById(R.id.accuracy_view);
//        mTimeView = (TextView) findViewById(R.id.time_view);
//        mLatView = (TextView) findViewById(R.id.lat_view);
//        mLngView = (TextView) findViewById(R.id.lng_view);

        // Create new Location Client. This class will handle callbacks
//        mLocationClient = new LocationClient(this, this, this);

        // Create and define the LocationRequest
        mLocationRequest = LocationRequest.create();

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Update every 10 seconds
        mLocationRequest.setInterval(POLLING_FREQ);

        // Recieve updates no more often than every 2 seconds
        mLocationRequest.setFastestInterval(FASTES_UPDATE_FREQ);

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

    }

//    @Override void onResume(){
//        super.onResume();
//        setUpMapLoc(mCurrentLocation);
//        mGoogleApiClient.connect();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }

    public LatLng setUpMapLoc(Location location){
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        return latLng;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();

        // Connect to LocationServices
//        mLocationClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GetLocation Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.idea.meter/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    protected void onStop() {

        // Stop updates
//        mLocationClient.removeLocationUpdates(this);

        // Disconnect from LocationServices
//        mLocationClient.disconnect();

        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.nesgleApiClient, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.disconnect();
    }


    // Called back when location changes

    @Override
    public void onLocationChanged(Location location) {

//        ensureColor();

        // Determine whether new location is better than current best
        // estimate

        handleNewLocation(location);
//
//        if (null == mBestReading
//                || location.getAccuracy() < mBestReading.getAccuracy()) {
//
//            // Update best estimate
//            mBestReading = location;
//
//            // Update display
////            updateDisplay(location);
//
//            if (mBestReading.getAccuracy() < MIN_ACCURACY)
//                mLocationClient.removeLocationUpdates(this);
//
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        mCurrentLocation = location;
        setUpMapLoc(mCurrentLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle dataBundle) {

        // Get first reading. Get additional location updates if necessary

        Log.i(TAG, "Location Service Connected. Congrats");
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {

            } else {
                handleNewLocation(location);
            }
        }


//        if (servicesAvailable()) {
//            // Get best last location measurement meeting criteria
//            mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY,
//                    FIVE_MIN);
//
//            // Display last reading information
//            if (null != mBestReading) {
//
//                updateDisplay(mBestReading);
//
//            } else {
//
//                mAccuracyView.setText("No Initial Reading Available");
//
//            }
//
//            if (null == mBestReading
//                    || mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
//                    || mBestReading.getTime() < System.currentTimeMillis()
//                    - TWO_MIN) {
//
//                mLocationClient.requestLocationUpdates(mLocationRequest, this);
//
//                // Schedule a runnable to unregister location listeners
//                Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        mLocationClient.removeLocationUpdates(LocationGetLocationActivity.this);
//
//                    }
//                }, MEASURE_TIME, TimeUnit.MILLISECONDS);
//            }
//
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location Service Suspended. Please Reconnect");
    }

    // Get the last known location from all providers
    // return best reading is as accurate as minAccuracy and
    // was taken no longer then maxAge milliseconds ago

//    private Location bestLastKnownLocation(float minAccuracy, long maxAge) {
//
//        Location bestResult = null;
//        float bestAccuracy = Float.MAX_VALUE;
//        long bestTime = Long.MIN_VALUE;
//
//        // Get the best most recent location currently available
//        mCurrentLocation = mLocationClient.getLastLocation();
//
//        if (mCurrentLocation != null) {
//
//            float accuracy = mCurrentLocation.getAccuracy();
//            long time = mCurrentLocation.getTime();
//
//            if (accuracy < bestAccuracy) {
//
//                bestResult = mCurrentLocation;
//                bestAccuracy = accuracy;
//                bestTime = time;
//
//            }
//        }
//
//        // Return best reading or null
//        if (bestAccuracy > minAccuracy || (System.currentTimeMillis() - bestTime) > maxAge) {
//            return null;
//        } else {
//            return bestResult;
//        }
//    }


//    @Override
//    public void onDisconnected() {
//
//        Log.i(TAG, "Disconnected. Try again later.");
//
//    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed. Try again later.");
    }

    private boolean servicesAvailable() {

        // Check that Google Play Services are available
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);

        // If Google Play services is available

        return (ConnectionResult.SUCCESS == resultCode);

    }

    // Update display
//    private void updateDisplay(Location location) {
//
//        mAccuracyView.setText("Accuracy:" + location.getAccuracy());
//
//        mTimeView.setText("Time:"
//                + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale
//                .getDefault()).format(new Date(location.getTime())));
//
//        mLatView.setText("Longitude:" + location.getLongitude());
//
//        mLngView.setText("Latitude:" + location.getLatitude());
//
//    }

//    private void ensureColor() {
//        if (mFirstUpdate) {
//            setTextViewColor(mTextViewColor);
//            mFirstUpdate = false;
//        }
//    }
//
//    private void setTextViewColor(int color) {
//
//        mAccuracyView.setTextColor(color);
//        mTimeView.setTextColor(color);
//        mLatView.setTextColor(color);
//        mLngView.setTextColor(color);
//
//    }


}
