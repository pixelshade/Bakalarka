package shade.pixel.gpsoclient;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by pixelshade on 9.2.2014.
 */

public class GPSTracker extends Service implements LocationListener {
    private static GPSTracker instance;
    private static final String TAG = "GPS_TRACKER";
    private MainActivity mActivity;
    private Context mContext;

    MyHtmlBrowser htmlBrowser;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1;

    // Declaring a Location Manager
    protected LocationManager locationManager;



    public static GPSTracker getInstance(){
        return getInstance(null,null);
    }

    public static GPSTracker getInstance(Context context, MainActivity activity){
        if(instance==null){
            instance = new GPSTracker(context, activity);
        }
        return instance;
    }

    public GPSTracker(Context context, MainActivity activity) {
        this.mActivity = activity;
        this.mContext = context;
        this.getLocation();
    }

    public LatLng getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                this.canGetLocation = false;
                showSettingsAlert();
                Toast.makeText(mContext, "Unable to get your location", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(TAG, "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d(TAG, "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LatLng(latitude, longitude);
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onLocationChanged(final Location location) {
        this.location = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        String url = shade.pixel.gpsoclient.Settings.getServerURL() + "/api/json/" + latitude + "/" + longitude;
        Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
        htmlBrowser = MyHtmlBrowser.getInstance(mContext);
        htmlBrowser.HttpGetAsyncString(mContext, url, new AsyncResponse() {
            @Override
            public void processFinish(Context context, String json) {
                Response response = new Response(json);
                if (response.isLoggedOut()) {
                    StartLoginActivity();
                } else {
                    GameData gameData = ResponseJSONParser.parseGameData(json);
                    GameHandler.gameHandler.setGameData(gameData);
                    if (gameData != null) {
                        StringBuilder sb = new StringBuilder();
                        ArrayList<Region> regions = gameData.getRegions();
                        ArrayList<Quest> quests = gameData.getQuests();
                        ArrayList<Item> items = gameData.getItems();
                        ArrayList<Attribute> attributes = gameData.getAttributes();
                        sb.append("Regions:");
                        for (Region region : regions) {
                            sb.append(region.getName() + ",");
                        }
                        sb.append("\nQuests:");
                        for (Quest quest : quests) {
                            quest.getName();
                            sb.append(quest.getName() + ",");
                        }

                        if(mActivity!=null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            String infoString = json + "\n\n" + latitude + " " + longitude;
                            mActivity.SetTextView(infoString);
                            mActivity.SetQuestsView();
                            mActivity.SetRegionsView();
                            mActivity.SetItemsView();
                            mActivity.SetAttributesView();
                        }

                    } else {
                        Log.d(TAG, "Problem with parsing gamedata");
                    }
                }
            }
        });
        Toast.makeText(mContext,"Sme tu: " + location.getLatitude() + " " + location.getLongitude() + "presnost("+location.getAccuracy()+")", Toast.LENGTH_LONG).show();
    }

    public void StartLoginActivity() {
        mActivity.StartLoginActivity();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "Status changed "+ provider + " status:"+ status);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "GPSTracker stopped", Toast.LENGTH_SHORT).show();
    }
}