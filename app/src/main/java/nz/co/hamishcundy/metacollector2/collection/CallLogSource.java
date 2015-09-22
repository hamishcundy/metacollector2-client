package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.CallLogRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 30/07/15.
 */
public class CallLogSource extends MetadataCollectionSource{

    public static final String key = "call_logs";
    public static final String name = "Call logs";
    public static final String[] fields = {"formatted_number", "numbertype", "duration", "presentation", "type", "number", "date", "numberlabel", "name", "matched_number", "normalized_number"};


    private static List<MetadataRecord> convertCVtoMDR(List<ContentValues> data, Context con) {
        ArrayList<MetadataRecord> records=new ArrayList<MetadataRecord>();
        for(ContentValues cv:data){
            CallLogRecord clr = new CallLogRecord();
            clr.formattedNumber = (String) cv.get("formatted_number");
            if(cv.containsKey("numbertype")){
                clr.numberType = cv.getAsInteger("numbertype");
            }
            clr.duration = cv.getAsInteger("duration");
            clr.presentation = cv.getAsInteger("presentation");
            clr.callType = cv.getAsInteger("type");
            clr.number = (String) cv.get("number");
            clr.date = cv.getAsLong("date");

            Log.d("CLS", "Date: " + clr.date);
            clr.numberLabel = (String) cv.get("numberlabel");
            clr.name = (String) cv.get("name");
            clr.matchedNumber = (String) cv.get("matched_number");
            clr.normalizedNumber = (String) cv.get("normalized_number");

            records.add(clr);
        }
        return records;
    }

    @Override
    public Object retrieveRecords(Context con) {
        Cursor c = con.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,
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
