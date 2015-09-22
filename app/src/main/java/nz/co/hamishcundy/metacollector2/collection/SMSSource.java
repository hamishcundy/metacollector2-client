package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;
import nz.co.hamishcundy.metacollector2.data.records.SMSRecord;

/**
 * Created by hamish on 2/08/15.
 */
public class SMSSource extends MetadataCollectionSource {

    public static final String[] fields = {"thread_id", "address", "person", "date", "date_sent", "type"};
    public static final String key = "sms_logs";
    public static final String name = "SMS logs";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object retrieveRecords(Context con) {
        Uri uriSMSURI = Uri.parse("content://sms/");
        Cursor c = con.getContentResolver().query(uriSMSURI, null, null,
                null, null);


        List<ContentValues> data = MetadataCursorUtils.getRecordValuesIfPresent(c, con, fields);
        List<MetadataRecord> mr = convertCVtoMDR(data, con);
        return mr;
    }

    private static List<MetadataRecord> convertCVtoMDR(List<ContentValues> data, Context con) {
        ArrayList<MetadataRecord> records=new ArrayList<MetadataRecord>();
        for(ContentValues cv:data){
            SMSRecord smsr = new SMSRecord();
            smsr.threadId = cv.getAsInteger("thread_id");
            smsr.address = (String) cv.get("address");
            if(cv.containsKey("person")) {
                smsr.person = cv.getAsInteger("person");
            }
            smsr.date = cv.getAsLong("date");
            smsr.dateSent = cv.getAsLong("date_sent");
            smsr.messageType = cv.getAsInteger("type");
            records.add(smsr);
        }
        return records;
    }
}
