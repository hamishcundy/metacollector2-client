package nz.co.hamishcundy.metacollector2.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.R;
import nz.co.hamishcundy.metacollector2.collection.MetadataCollectionSource;
import nz.co.hamishcundy.metacollector2.data.CommsWrapper;
import nz.co.hamishcundy.metacollector2.data.records.MetadataRecord;
import nz.co.hamishcundy.metacollector2.networking.CommsHelper;
import nz.co.hamishcundy.metacollector2.networking.MCApiInterface;
import retrofit.RetrofitError;

public class CollectionActivity extends ActionBarActivity {

    private ArrayList<String> collectionKeys = new ArrayList<String>();
    private Handler h;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ArrayList<String> sources = getIntent().getStringArrayListExtra("Sources");
        for(String name:sources){
            collectionKeys.add(MetadataCollectionSource.getKey(name));
        }
        h = new Handler();
        showActiveView();
        if(collectionKeys.contains("facebook_data")){
            facebookLogin();
        }else{
            beginCollectionProcess();
        }

    }

    private void facebookLogin() {
        if(callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
        LoginManager lm = LoginManager.getInstance();
        lm.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                beginCollectionProcess();
            }

            @Override
            public void onCancel() {
                AlertDialog.Builder build = new AlertDialog.Builder(CollectionActivity.this);
                build.setTitle("Facebook login cancelled").setIconAttribute(android.R.attr.alertDialogIcon);
                build.setMessage("Facebook integration attempt was cancelled. Do you want to try again or skip Facebook data collection?");
                build.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        facebookLogin();
                    }
                });
                build.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        collectionKeys.remove("facebook_data");
                        beginCollectionProcess();
                    }
                });
                build.create().show();
            }

            @Override
            public void onError(FacebookException exception) {
                AlertDialog.Builder build = new AlertDialog.Builder(CollectionActivity.this);
                build.setTitle("Facebook login error").setIconAttribute(android.R.attr.alertDialogIcon);
                build.setMessage("Error occurred while connecting to Facebook: " + exception.getMessage() + ". Do you want to try again or skip Facebook data collection?");
                build.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        facebookLogin();
                    }
                });
                build.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        collectionKeys.remove("facebook_data");
                        beginCollectionProcess();
                    }
                });
                build.create().show();

            }
        });
        ArrayList<String> perms = new ArrayList<String>();

        perms.add("read_mailbox");
        lm.logInWithReadPermissions(this, perms);
    }

    private void beginCollectionProcess(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                startCollectionInWorkerThread();
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void showActiveView() {
    }

    private void startCollectionInWorkerThread() {
        for(int i = 0; i < collectionKeys.size(); i++){
            String key = collectionKeys.get(i);
            updateCaption(i, key, "Collating metadata");
            MetadataCollectionSource mcs = MetadataCollectionSource.getSource(key);
            List<MetadataRecord> mr = mcs.retrieveRecords(this);
            updateCaption(i, key, "Uploading metadata");
            String result = uploadData(mr, key);
            if(result != null){
                notifyError(result);
                break;
            }

        }
    }

    private void notifyError(final String result) {
        h.post(new Runnable() {
            @Override
            public void run() {
                showFailView();
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
                builder.setIconAttribute(android.R.attr.alertDialogIcon);
                builder.setTitle("Upload error");
                builder.setMessage("An error occurred while uploading data to the server: " + result);
                builder.setPositiveButton("Ok", null);
                builder.create().show();
            }
        });
    }

    private void showFailView() {
    }

    private String uploadData(List<MetadataRecord> mr, String key) {
        CommsWrapper cw = new CommsWrapper();
        cw.source = key;
        cw.participantId = getIntent().getIntExtra("PARTICIPANT_ID", 0);
        cw.payload = mr;
        MCApiInterface mcapi = CommsHelper.getCommsInterface();
        String result;
        try {
            result = mcapi.uploadMetadata(cw);
        }catch(RetrofitError e){
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    private void updateCaption(final int index, final String key, final String step) {
        h.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.collection_count)).setText("Collecting from source " + (index + 1) + " out of " + collectionKeys.size());
                ((TextView) findViewById(R.id.collection_step)).setText(MetadataCollectionSource.getName(key) + ": " + step);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
