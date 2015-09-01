package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.data.records.FacebookRootRecord;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;

/**
 * Created by hamish on 25/08/15.
 */
public class FacebookSource extends MetadataCollectionSource {

    public static final String key = "facebook_data";
    public static final String name = "Facebook data";

    @Override
    public List<MetadataRecord> retrieveRecords(Context con) {
        GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/inbox", new GraphRequest.Callback() {

            @Override
            public void onCompleted(GraphResponse graphResponse) {

            }


        });
        GraphResponse respo = request.executeAndWait();
        Log.d("FS", respo.getJSONObject().toString());
        List<MetadataRecord> list = new ArrayList<MetadataRecord>();

        String filename = "myfile";

        FileOutputStream outputStream;

        try {
            outputStream =new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "messages.json"));
            
            Log.d("FS", con.getFilesDir().toString());
            outputStream.write(respo.getJSONObject().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FacebookRootRecord frr = new FacebookRootRecord();
        list.add(frr);
        return list;

    }

    @Override
    public String getKey() {
        return key;
    }
}
