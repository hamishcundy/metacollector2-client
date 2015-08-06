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

}
