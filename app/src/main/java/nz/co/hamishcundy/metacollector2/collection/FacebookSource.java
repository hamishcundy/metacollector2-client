package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

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
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

            }
        });
        GraphResponse respo = request.executeAndWait();
        Log.d("FS", respo.getJSONObject().toString());
        List<MetadataRecord> list = new ArrayList<MetadataRecord>();
        FacebookRootRecord frr = new FacebookRootRecord();
        list.add(frr);
        return list;

    }

    @Override
    public String getKey() {
        return key;
    }
}
