package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 30/07/15.
 */
public class CallLogSource implements MetadataCollectionSource{

    public static final String name = "Call logs";


    public static void testAccess(Context con){
//        Cursor c = con.getContentResolver().query(
//                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
//                android.provider.CallLog.Calls.DATE + " DESC ");

//        Uri uriSMSURI = Uri.parse("content://sms/");
//        Cursor c = con.getContentResolver().query(uriSMSURI, null, null,
//                null, null);


        Cursor c = con.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);

        Log.d("CLS", "Size:" + c.getCount() + " Column count:" + c.getColumnCount());
        for(int i = 0; i < c.getColumnCount(); i++){
            Log.d("CLS", i + ": " + c.getColumnName(i));
        }

        JSONArray resultSet = new JSONArray();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            final int totalColumn = c.getColumnCount();
            JSONObject rowObject = new JSONObject();
            int i;// = 0;
            for (  i = 0; i < totalColumn; i++) {

                if (c.getColumnName(i) != null) {
                    try {
                        String getcol = c.getColumnName(i),
                                getstr = c.getString(i);
                        rowObject.put(
                                getcol,
                                getstr
                        );



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }//for

            resultSet.put(rowObject);
            c.moveToNext();
        }
        Log.d("CLS", resultSet.toString());



    }

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        return null;
    }
}
