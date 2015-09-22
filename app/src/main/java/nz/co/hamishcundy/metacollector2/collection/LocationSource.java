package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 10/09/15.
 */
public class LocationSource extends MetadataCollectionSource {

    public static final String key = "location";
    public static final String name = "Location (continuous)";
    @Override
    public Object retrieveRecords(Context con) {
        //dont do anything here as is all done by service
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
