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

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Log.d("MA", "Application created");

    }
}
