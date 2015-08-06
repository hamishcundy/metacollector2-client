package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.CallLogRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 30/07/15.
 */
public class CallLogSource extends MetadataCollectionSource{

    public static final String key = "Call logs";
    public static final String[] fields = {"formatted_number", "numbertype", "duration", "presentation", "type", "number", "date", "numberlabel", "name", "matched_number", "normalized_number"};


    private static List<MetadataRecord> convertCVtoMDR(List<ContentValues> data, Context con) {
        ArrayList<MetadataRecord> records=new ArrayList<MetadataRecord>();
        for(ContentValues cv:data){
            CallLogRecord clr = new CallLogRecord();
            clr.formattedNumber = (String) cv.get("formatted_number");
            clr.numberType = (int) cv.get("numbertype");
            clr.duration = (int) cv.get("duration");
            clr.presentation = (int) cv.get("presentation");
            clr.type = (int) cv.get("type");
            clr.number = (String) cv.get("number");
            clr.date = (long) cv.get("date");
            clr.numberLabel = (String) cv.get("numberlabel");
            clr.name = (String) cv.get("name");
            clr.matchedNumber = (String) cv.get("matched_number");
            clr.normalizedNumber = (String) cv.get("normalized_number");

            records.add(clr);
        }
        return records;
    }

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        Cursor c = con.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);

        List<ContentValues> data = MetadataCursorUtils.getRecordValuesIfPresent(c, con, fields);
        List<MetadataRecord> mr = convertCVtoMDR(data, con);
        return mr;
    }

    @Override
    public String getKey() {
        return key;
    }
}
