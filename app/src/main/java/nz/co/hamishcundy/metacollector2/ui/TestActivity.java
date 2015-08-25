package nz.co.hamishcundy.metacollector2.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;

import nz.co.hamishcundy.metacollector2.R;

public class TestActivity extends ActionBarActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d("TestAc", "Activity onCreate");
        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        LoginManager lm = LoginManager.getInstance();

        lm.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TestAc", "SUccess");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        Log.d("TestAc", "Graph api responsed!");
                        Log.d("TestAct", jsonObject.toString());
                    }
                });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("TestAc", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("TestAc", "Error  " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        ArrayList<String> perms = new ArrayList<String>();
        perms.add("user_friends");
        lm.logInWithReadPermissions(this, perms);
        Log.d("TestAc", "Made the call");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
