package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 25/08/15.
 */
public class FacebookSource extends MetadataCollectionSource {

    public static final String key = "facebook_data";
    public static final String name = "Facebook data";

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
