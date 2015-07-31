package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hamish on 30/07/15.
 */
public class CallLogSource {

    public static final String name = "Call logs";


    public static void testAccess(Context con){
        Cursor c = con.getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC ");

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
}
