package nz.co.hamishcundy.metacollector2.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nz.co.hamishcundy.metacollector2.FormActivity;
import nz.co.hamishcundy.metacollector2.MetacollectorApplication;
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
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(MetacollectorApplication.INITIAL_COLLECTION_COMPLETE, false)){//main collection complete. show end activity

        }else{

            MCApiInterface mcai = CommsHelper.getCommsInterface();
            mcai.getSurveyDetails(new Callback<SurveyDetails>() {
                @Override
                public void success(SurveyDetails surveyDetails, Response response) {
                    if (surveyDetails != null) {
                        Intent i = new Intent(SplashActivity.this, FormActivity.class);
                        i.putExtra("DETAILS_REQUIRED", surveyDetails.details_required);
                        i.putExtra("TERMS", surveyDetails.terms);
                        i.putExtra("NAME", surveyDetails.name);
                        ((MetacollectorApplication) getApplication()).sources = surveyDetails.collection_sources;
                        startActivity(i);
                        finish();
                    } else {
                        AlertDialog.Builder build = new AlertDialog.Builder(SplashActivity.this);
                        build.setIconAttribute(android.R.attr.alertDialogIcon);
                        build.setTitle("No survey");
                        build.setMessage("Could not retrieve survey details. Please confirm with the researcher that the survey has been configured.");
                        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        build.create().show();
                    }
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (findViewById(R.id.progressBar2) != null) {
                        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                    }
                }
            }, 2000);
        }

    }




}
