package nz.co.hamishcundy.metacollector2.data.records;

import java.util.ArrayList;

/**
 * Created by hamish on 29/09/15.
 */
public class ContactRecord implements MetadataRecord {

    public int contactId;
    public String displayName;

    public int timesContacted;
    public long lastTimeContacted;
    public ArrayList<ContactDataRecord> contact_data_records_attributes;
}
