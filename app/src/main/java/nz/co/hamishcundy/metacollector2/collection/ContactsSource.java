package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.ContactDataRecord;
import nz.co.hamishcundy.metacollector2.data.records.ContactRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 26/08/15.
 */
public class ContactsSource extends MetadataCollectionSource {

    public static final String key = "contacts";
    public static final String name = "Contacts";

    public static final String[] fields = {"_id", "display_name", "times_contacted", "last_time_contacted"};

    public static final String[] dataFields = {"contact_id", "mimetype", "data1", "data2"};

    @Override
    public Object retrieveRecords(Context con) {
        Cursor c = con.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, null);
        List<ContentValues> contactData = MetadataCursorUtils.getRecordValuesIfPresent(c, con, fields);
        c.close();

        c = con.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null,
                null, null);
        List<ContentValues> dataData = MetadataCursorUtils.getRecordValuesIfPresent(c, con, dataFields);
        c.close();

        return processData(contactData, dataData, con);

    }

    private ArrayList<ContactRecord> processData(List<ContentValues> contactData, List<ContentValues> dataData, Context con) {
        ArrayList<ContactRecord> contactList = new ArrayList<ContactRecord>();
        for(ContentValues cv:contactData){
            ContactRecord c = new ContactRecord();
            c.contactId = cv.getAsInteger("_id");
            c.displayName = cv.getAsString("display_name");
            c.timesContacted = cv.getAsInteger("times_contacted");
            c.lastTimeContacted = cv.getAsLong("last_time_contacted");
            c.data = new ArrayList<ContactDataRecord>();
            contactList.add(c);
        }

        for(ContentValues cv:dataData){
            for(ContactRecord cd:contactList){
                if(cd.contactId == cv.getAsInteger("contact_id")){
                    ContactDataRecord cdr = new ContactDataRecord();
                    cdr.data = cv.getAsString("data1");
                    String mimetype = cv.getAsString("mimetype");
                    if(mimetype.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                        cdr.type = "Phone";
                        int subtype = cv.getAsInteger(ContactsContract.CommonDataKinds.Phone.TYPE);
                        switch (subtype){
                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                cdr.subType = "Home";
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                cdr.subType = "Mobile";
                                break;
                            default:
                                cdr.subType = "Other";
                                break;
                        }
                    }else if(mimetype.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                        cdr.type = "Email";
                        int subtype = cv.getAsInteger(ContactsContract.CommonDataKinds.Email.TYPE);
                        switch (subtype){
                            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                cdr.subType = "Home";
                                break;
                            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                cdr.subType = "Work";
                                break;
                            case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                cdr.subType = "Mobile";
                                break;
                            case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                cdr.subType = "Other";
                                break;
                            default:
                                cdr.subType = "Other";
                                break;
                        }

                    }else{
                        Log.d("CS", "Removing contact data of type " + mimetype);
                        cdr = null;
                    }

                    
                    if(cdr != null) {
                        cd.data.add(cdr);
                    }
                }
            }
        }


        return contactList;

    }

    @Override
    public String getKey() {
        return key;
    }
}
