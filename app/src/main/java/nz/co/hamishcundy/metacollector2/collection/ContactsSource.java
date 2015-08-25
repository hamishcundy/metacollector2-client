package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 26/08/15.
 */
public class ContactsSource extends MetadataCollectionSource {

    public static final String key = "contacts";
    public static final String name = "Contacts";

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
