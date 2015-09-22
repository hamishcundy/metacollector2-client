package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 26/08/15.
 */
public class ContactsSource extends MetadataCollectionSource {

    public static final String key = "contacts";
    public static final String name = "Contacts";

    @Override
    public Object retrieveRecords(Context con) {
        Cursor c = con.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);
        Log.d("CS", "Contacts count " + c.getCount());
        for(int i = 0; i < c.getColumnCount(); i++){
            Log.d("CS", "Col " + i + ": " + c.getColumnName(i));
        }
        c.close();

        c = con.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, null,
                null, null);
        Log.d("CS", "RawContacts count " + c.getCount());
        for(int i = 0; i < c.getColumnCount(); i++){
            Log.d("CS", "Col " + i + ": " + c.getColumnName(i));
        }
        c.close();

        c = con.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null,
                null, null);
        Log.d("CS", "Data count " + c.getCount());
        for(int i = 0; i < c.getColumnCount(); i++){
            Log.d("CS", "Col " + i + ": " + c.getColumnName(i));
        }
        c.close();
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
