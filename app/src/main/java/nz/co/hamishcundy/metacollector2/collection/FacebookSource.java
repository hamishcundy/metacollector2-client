package nz.co.hamishcundy.metacollector2.collection;

import android.content.Context;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import nz.co.hamishcundy.metacollector2.data.records.ConversationParticipants;
import nz.co.hamishcundy.metacollector2.data.records.FacebookConversationRecord;
import nz.co.hamishcundy.metacollector2.data.records.FacebookMessageRecord;
import nz.co.hamishcundy.metacollector2.data.records.FacebookRootRecord;

/**
 * Created by hamish on 25/08/15.
 */
public class FacebookSource extends MetadataCollectionSource {

    public static final String key = "facebook_data";
    public static final String name = "Facebook data";

    @Override
    public Object retrieveRecords(Context con) {
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


            FacebookRootRecord frr = new FacebookRootRecord();
            frr.name = myName;

            JSONArray data = respo.getJSONObject().getJSONArray("data");
            ArrayList<FacebookConversationRecord> fcr = new ArrayList<FacebookConversationRecord>();
            for (int i = 0; i < data.length(); i++) {//iterate through each conversation

                FacebookConversationRecord convo = new FacebookConversationRecord();
                JSONArray participants = data.getJSONObject(i).getJSONObject("to").getJSONArray("data");

                    ArrayList<ConversationParticipants> participantNames = new ArrayList<ConversationParticipants>();
                    for (int k = 0; k < participants.length(); k++) {
                        if (!participants.getJSONObject(k).getString("id").equals(myId)) {
                            participantNames.add(new ConversationParticipants(participants.getJSONObject(k).getString("name")));
                        }
                    }
                    convo.conversation_participants_attributes = participantNames;

                ArrayList<FacebookMessageRecord> fmr = new ArrayList<FacebookMessageRecord>();
                JSONArray messages = data.getJSONObject(i).getJSONObject("comments").getJSONArray("data");
                for (int j = 0; j < messages.length(); j++) {//iterate through messages_attributes
                    JSONObject message = messages.getJSONObject(j);
                    FacebookMessageRecord fm = new FacebookMessageRecord();
                    fm.sender = message.getJSONObject("from").getString("name");
                    if(message.getJSONObject("from").getString("id").equals(myId)){//outgoing


                        fm.messageType = "outgoing";
                    }else{

                        fm.messageType = "incoming";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date d = sdf.parse(message.getString("created_time"));
                    fm.date = d.getTime();
                    fmr.add(fm);
                }
                convo.messages_attributes = fmr;
                fcr.add(convo);

            }
            frr.conversations = fcr;


//
//        FileOutputStream outputStream;
//
//        try {
//            outputStream =new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "messages_attributes.json"));
//
//            Log.d("FS", con.getFilesDir().toString());
//            outputStream.write(respo.getJSONObject().toString().getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


            return frr;
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
