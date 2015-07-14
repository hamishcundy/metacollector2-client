package nz.co.hamishcundy.metacollector2.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import nz.co.hamishcundy.metacollector2.FormActivity;
import nz.co.hamishcundy.metacollector2.R;

public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, FormActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }




}
