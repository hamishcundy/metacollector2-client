package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.CallLogRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;
import nz.co.hamishcundy.metacollector2.data.records.SMSRecord;

/**
 * Created by hamish on 2/08/15.
 */
public class SMSSource implements MetadataCollectionSource {

    public static final String[] fields = {"thread_id", "address", "person", "date", "date_sent", "type"};

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
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
            smsr.threadId = (int) cv.get("thread_id");
            smsr.address = (String) cv.get("address");
            smsr.person = (int) cv.get("person");
            smsr.date = (long) cv.get("date");
            smsr.dateSent = (long) cv.get("date_sent");
            smsr.type = (int) cv.get("type");
            records.add(smsr);
        }
        return records;
    }
}
