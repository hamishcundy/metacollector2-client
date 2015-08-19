package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 30/07/15.
 */
public abstract class MetadataCollectionSource {

    public abstract List<MetadataRecord> retrieveRecords(Context con);
    public abstract String getKey();

    public static MetadataCollectionSource getSource(String key){
        switch(key){
            case SMSSource.key:
                return new SMSSource();
            case CallLogSource.key:
                return new CallLogSource();
            case InstalledAppsSource.key:
                return new InstalledAppsSource();
        }
        return null;
    }

    public static String getName(String key){
        switch(key){
            case SMSSource.key:
                return SMSSource.name;
            case CallLogSource.key:
                return CallLogSource.name;
            case InstalledAppsSource.key:
                return InstalledAppsSource.name;

        }
        return null;
    }

    public static String getKey(String name){
        switch(name){
            case SMSSource.name:
                return SMSSource.key;
            case CallLogSource.name:
                return CallLogSource.key;
            case InstalledAppsSource.name:
                return InstalledAppsSource.key;

        }
        return null;
    }

}
