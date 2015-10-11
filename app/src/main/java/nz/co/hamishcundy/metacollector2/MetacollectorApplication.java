package nz.co.hamishcundy.metacollector2;

import android.app.Application;
import android.util.Log;

import com.facebook.FacebookSdk;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.MetadataSource;

/**
 * Created by hamish on 16/08/15.
 */
public class MetacollectorApplication extends Application {

    public List<MetadataSource> sources;

    public static final String PARTICIPANT_ID = "participant_id";
    public static final String BACKGROUND_LOCATION_RECORDING = "background_location_recording";
    public static final String ACTIVE_LOCATION = "active_location_provider";
    public static final String SOURCE_LIST = "source_list";

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("MA", "Application created");

    }
}
