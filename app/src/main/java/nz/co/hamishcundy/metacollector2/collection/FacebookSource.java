package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import nz.co.hamishcundy.metacollector2.data.records.FacebookConversationRecord;
import nz.co.hamishcundy.metacollector2.data.records.FacebookMessageRecord;
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
        try {
            GraphRequest meReq = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                }
            });
            GraphResponse gres = meReq.executeAndWait();
            Log.d("FS", "MeReq: " + gres.getJSONObject());
            String myId = gres.getJSONObject().getString("id");
            String myName = gres.getJSONObject().getString("name");
            GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/inbox", new GraphRequest.Callback() {

                @Override
                public void onCompleted(GraphResponse graphResponse) {

                }


            });
            GraphResponse respo = request.executeAndWait();
            Log.d("FS", respo.getJSONObject().toString());
            List<MetadataRecord> list = new ArrayList<MetadataRecord>();

            FacebookRootRecord frr = new FacebookRootRecord();
            frr.name = myName;

            JSONArray data = respo.getJSONObject().getJSONArray("data");
            ArrayList<FacebookConversationRecord> fcr = new ArrayList<FacebookConversationRecord>();
            for (int i = 0; i < data.length(); i++) {//iterate through each conversation
                String otherPerson = null;
                FacebookConversationRecord convo = new FacebookConversationRecord();
                JSONArray participants = data.getJSONObject(i).getJSONObject("to").getJSONArray("data");
                if(participants.length() > 2) {//if group convo, add participants
                    ArrayList<String> participantNames = new ArrayList<String>();
                    for (int k = 0; k < participants.length(); k++) {
                        if (!participants.getJSONObject(k).getString("id").equals(myId)) {
                            participantNames.add(participants.getJSONObject(k).getString("name"));
                        }
                    }
                    convo.participants = participantNames;
                }else{//single person. record name
                    for (int k = 0; k < participants.length(); k++) {
                        if (!participants.getJSONObject(k).getString("id").equals(myId)) {
                            otherPerson = participants.getJSONObject(k).getString("name");
                        }
                    }
                }
                ArrayList<FacebookMessageRecord> fmr = new ArrayList<FacebookMessageRecord>();
                JSONArray messages = data.getJSONObject(i).getJSONObject("comments").getJSONArray("data");
                for (int j = 0; j < messages.length(); j++) {//iterate through messages
                    JSONObject message = messages.getJSONObject(j);
                    FacebookMessageRecord fm = new FacebookMessageRecord();
                    if(message.getJSONObject("from").getString("id").equals(myId)){//outgoing
                        if(otherPerson != null) {
                            fm.otherPartyName = otherPerson;
                        }

                        fm.type = "outgoing";
                    }else{
                        fm.otherPartyName = message.getJSONObject("from").getString("name");
                        fm.type = "incoming";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date d = sdf.parse(message.getString("created_time"));
                    fm.date = d.getTime();
                    fmr.add(fm);
                }
                convo.messages = fmr;
                fcr.add(convo);

            }
            frr.conversations = fcr;


//
//        FileOutputStream outputStream;
//
//        try {
//            outputStream =new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "messages.json"));
//
//            Log.d("FS", con.getFilesDir().toString());
//            outputStream.write(respo.getJSONObject().toString().getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


            list.add(frr);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getKey() {
        return key;
    }
}
