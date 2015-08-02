package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.InstalledAppRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 2/08/15.
 */
public class InstalledAppsSource implements MetadataCollectionSource{



    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        ArrayList<MetadataRecord> apps = new ArrayList<MetadataRecord>();

        final PackageManager pm = con.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo packageInfo : packages) {
            if(pm.getLaunchIntentForPackage(packageInfo.packageName) != null) {//only want apps with launcher intent (dont want system stuff)
                InstalledAppRecord iar = new InstalledAppRecord();
                iar.packageName = packageInfo.packageName;
                iar.appName = pm.getApplicationLabel(packageInfo).toString();
                apps.add(iar);


            }

        }
        return apps;
    }
}