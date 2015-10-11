package nz.co.hamishcundy.metacollector2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;

import nz.co.hamishcundy.metacollector2.data.CommsWrapper;
import nz.co.hamishcundy.metacollector2.data.records.LocationRecord;
import nz.co.hamishcundy.metacollector2.networking.CommsHelper;
import nz.co.hamishcundy.metacollector2.networking.MCApiInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hamish on 10/09/15.
 */
public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Location loc = (Location) intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
        if(loc != null) {
            MCApiInterface mcai = CommsHelper.getCommsInterface();
            LocationRecord lr = new LocationRecord();
            lr.latitude = loc.getLatitude();
            lr.longitude = loc.getLongitude();
            lr.accuracy = loc.getAccuracy();
            lr.source = loc.getProvider();
            lr.date = System.currentTimeMillis();

            CommsWrapper cw = new CommsWrapper();
            cw.location = lr;
            cw.participantId = PreferenceManager.getDefaultSharedPreferences(context).getInt(MetacollectorApplication.PARTICIPANT_ID, 0);
            mcai.locationUpdate(cw, new Callback<Object>() {
                @Override
                public void success(Object o, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }else{
            Log.d("LocRec", "Loc null");
        }
    }
}
