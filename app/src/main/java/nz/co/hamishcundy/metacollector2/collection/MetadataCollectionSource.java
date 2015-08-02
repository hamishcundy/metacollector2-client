package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 30/07/15.
 */
public interface MetadataCollectionSource {

    public List<MetadataRecord> retrieveRecords(Context con);
}
