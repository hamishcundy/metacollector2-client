package nz.co.hamishcundy.metacollector2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by hamish on 10/09/15.
 */
public class BootReceiver extends BroadcastReceiver {
    private static LocationManager locationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(MetacollectorApplication.BACKGROUND_LOCATION_RECORDING, false)){
            if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(MetacollectorApplication.ACTIVE_LOCATION, false)){
                startReceivingActiveLocationUpdates(context);
            }else{
                startReceivingPassiveLocationUpdates(context);
            }


        }

    }

    public static void startReceivingPassiveLocationUpdates(Context context) {
        Toast.makeText(context, "Location recorder started", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Intent i = new Intent(context, LocationReceiver.class);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, PendingIntent.getBroadcast(context, 0, i, 0));
    }

    public static void startReceivingActiveLocationUpdates(Context context) {
        Toast.makeText(context, "Location recorder started", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Intent i = new Intent(context, LocationReceiver.class);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 100, PendingIntent.getBroadcast(context,0,i,0));
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 100, PendingIntent.getBroadcast(context,0,i,0));
    }
}
