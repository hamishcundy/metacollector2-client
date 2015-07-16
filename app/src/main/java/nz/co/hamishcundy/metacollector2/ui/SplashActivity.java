package nz.co.hamishcundy.metacollector2.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import nz.co.hamishcundy.metacollector2.FormActivity;
import nz.co.hamishcundy.metacollector2.R;
import nz.co.hamishcundy.metacollector2.data.SurveyDetails;
import nz.co.hamishcundy.metacollector2.networking.CommsHelper;
import nz.co.hamishcundy.metacollector2.networking.MCApiInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MCApiInterface mcai = CommsHelper.getCommsInterface();
        mcai.getSurveyDetails(new Callback<SurveyDetails>() {
            @Override
            public void success(SurveyDetails surveyDetails, Response response) {
                Intent i = new Intent(SplashActivity.this, FormActivity.class);
                i.putExtra("DETAILS_REQUIRED", surveyDetails.details_required);
                i.putExtra("TERMS", surveyDetails.terms);
                i.putExtra("NAME", surveyDetails.name);
                startActivity(i);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                AlertDialog.Builder build = new AlertDialog.Builder(SplashActivity.this);
                build.setTitle("Configuration download failed");
                build.setMessage("Unable to download survey configuration from server. Please check your internet connection and try again");
                build.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                build.create().show();
            }
        });

    }




}
