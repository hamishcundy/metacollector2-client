package nz.co.hamishcundy.metacollector2.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.co.hamishcundy.metacollector2.BootReceiver;
import nz.co.hamishcundy.metacollector2.MetacollectorApplication;
import nz.co.hamishcundy.metacollector2.R;
import nz.co.hamishcundy.metacollector2.collection.LocationSource;
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

        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionButtonPressed();
            }
        });
        findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionButtonPressed();
            }
        });
        findViewById(R.id.reupload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionButtonPressed();
            }
        });
        Set<String> sources = PreferenceManager.getDefaultSharedPreferences(this).getStringSet(MetacollectorApplication.SOURCE_LIST, new HashSet<String>());
        for (String name : sources) {
            if (!name.equals(LocationSource.name)) {

                collectionKeys.add(MetadataCollectionSource.getKey(name));
            }
        }
        h = new Handler();
        if (getIntent().getBooleanExtra("AUTOSTART", false)) {
            collectionButtonPressed();
        } else {
            showStartView();
        }


    }

    private void collectionButtonPressed() {
        showUploadingView();
        if (collectionKeys.contains("facebook_data")) {
            facebookLogin();
        } else {
            beginCollectionProcess();
        }
    }

    private void facebookLogin() {
        if (callbackManager == null) {
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

    private void beginCollectionProcess() {
        new Thread(new Runnable() {

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

    private void showUploadingView() {
        ((TextView) findViewById(R.id.collection_count)).setText("Preparing collection sources");
        ((TextView) findViewById(R.id.collection_step)).setText("");


        findViewById(R.id.uploading_view).setVisibility(View.VISIBLE);
        findViewById(R.id.fail_view).setVisibility(View.GONE);
        findViewById(R.id.start_view).setVisibility(View.GONE);
        findViewById(R.id.finish_view).setVisibility(View.GONE);

    }

    private void showFailView(String failureReason) {

        ((TextView) findViewById(R.id.fail_label)).setText(failureReason);
        findViewById(R.id.uploading_view).setVisibility(View.GONE);
        findViewById(R.id.fail_view).setVisibility(View.VISIBLE);
        findViewById(R.id.start_view).setVisibility(View.GONE);
        findViewById(R.id.finish_view).setVisibility(View.GONE);


    }

    private void showStartView() {
        findViewById(R.id.uploading_view).setVisibility(View.GONE);
        findViewById(R.id.fail_view).setVisibility(View.GONE);
        findViewById(R.id.start_view).setVisibility(View.VISIBLE);
        findViewById(R.id.finish_view).setVisibility(View.GONE);

    }

    private void showFinishView() {
        findViewById(R.id.uploading_view).setVisibility(View.GONE);
        findViewById(R.id.fail_view).setVisibility(View.GONE);
        findViewById(R.id.start_view).setVisibility(View.GONE);
        findViewById(R.id.finish_view).setVisibility(View.VISIBLE);
    }


    private void startCollectionInWorkerThread() {
        String error = null;
        for (int i = 0; i < collectionKeys.size(); i++) {
            String key = collectionKeys.get(i);
            updateCaption(i, key, "Collating metadata");
            MetadataCollectionSource mcs = MetadataCollectionSource.getSource(key);
            Object mr = mcs.retrieveRecords(this);
            updateCaption(i, key, "Uploading metadata");
            String result = uploadData(mr, key);
            if (result != null) {
                error = result;
                break;
            }

        }
        if(error == null) {
            collectionFinished();
        }else{
            notifyError(error);
        }
    }

    private void collectionFinished() {
        h.post(new Runnable() {
            @Override
            public void run() {

                showFinishView();

            }
        });
    }

    private void notifyError(final String result) {
        h.post(new Runnable() {
            @Override
            public void run() {
                showFailView("Failed to upload data: " + result);

            }
        });
    }


    private String uploadData(Object mr, String key) {
        CommsWrapper cw = new CommsWrapper();
        cw.source = key;
        cw.participantId = PreferenceManager.getDefaultSharedPreferences(this).getInt(MetacollectorApplication.PARTICIPANT_ID, 0);
        cw.payload = mr;
        MCApiInterface mcapi = CommsHelper.getCommsInterface();
        String result;
        try {
            result = mcapi.uploadMetadata(cw);
        } catch (RetrofitError e) {
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


}
