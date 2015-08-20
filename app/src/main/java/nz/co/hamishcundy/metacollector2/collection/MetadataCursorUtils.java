package nz.co.hamishcundy.metacollector2.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamish on 2/08/15.
 */
public class MetadataCursorUtils {


    public static List<ContentValues> getRecordValuesIfPresent(Cursor c, Context con, String[] columnNames){
        ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
        if(c.moveToFirst()){
            do{
                ContentValues cv= new ContentValues();
                for(String columnName:columnNames){
                    int ind = c.getColumnIndex(columnName);
                    Log.d("MCU", ind + " " + columnName);
                    switch(c.getType(ind)){
                        case Cursor.FIELD_TYPE_INTEGER:
                            cv.put(columnName, c.getInt(ind));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            cv.put(columnName, c.getString(ind));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            cv.put(columnName, c.getFloat(ind));
                            break;
                    }

                }
                if(cv.size() > 0) {
                    valuesList.add(cv);
                }

            }while (c.moveToNext());
        }
        return valuesList;

    }
}
