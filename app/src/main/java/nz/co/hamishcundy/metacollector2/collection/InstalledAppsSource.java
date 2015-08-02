package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 2/08/15.
 */
public class InstalledAppsSource implements MetadataCollectionSource{

    public static void testCall(Context con){
        final PackageManager pm = con.getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo packageInfo : packages) {
            Log.d("IAS", "Installed package :" + packageInfo.packageName);
            //Log.d("IAS", "Source dir : " + packageInfo.sourceDir);
            Log.d("IAS", "App name :" + pm.getApplicationLabel(packageInfo));
            Log.d("IAS", "Is launchable?: " + (pm.getLaunchIntentForPackage(packageInfo.packageName) != null));

        }
    }

    @Override
    public List<MetadataRecord> retreiveRecords(Context con) {
        return null;
    }
}
