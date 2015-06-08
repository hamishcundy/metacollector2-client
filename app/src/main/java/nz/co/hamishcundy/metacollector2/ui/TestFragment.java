package nz.co.hamishcundy.metacollector2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;

import nz.co.hamishcundy.metacollector2.R;
import nz.co.hamishcundy.metacollector2.networking.CommsHelper;
import nz.co.hamishcundy.metacollector2.networking.MCApiInterface;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hamish on 8/06/15.
 */
public class TestFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private TestPage mPage;
    private View rootView;

    public static Fragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (TestPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                MCApiInterface mcai = CommsHelper.getCommsInterface();
                mcai.testCall("teststring", new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
                        ((TextView)rootView.findViewById(R.id.textView2)).setText("Success: " + s);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
                        ((TextView)rootView.findViewById(R.id.textView2)).setText("Fail: " + error.getMessage());
                    }
                });
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);


    }
}
