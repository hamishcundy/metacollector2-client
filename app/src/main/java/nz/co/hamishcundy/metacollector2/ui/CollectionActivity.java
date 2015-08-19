package nz.co.hamishcundy.metacollector2.ui;

import android.app.AlertDialog;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
        new Thread(new Runnable(){

            @Override
            public void run() {
                startCollection();
            }
        }).start();

    }

    private void showActiveView() {
    }

    private void startCollection() {
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
