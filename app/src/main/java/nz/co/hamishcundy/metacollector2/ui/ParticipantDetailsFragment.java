package nz.co.hamishcundy.metacollector2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;

import nz.co.hamishcundy.metacollector2.R;

/**
 * Created by hamish on 15/06/15.
 */
public class ParticipantDetailsFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private ParticipantDetailsPage mPage;
    private EditText nameField, emailField;


    public static ParticipantDetailsFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ParticipantDetailsFragment fragment = new ParticipantDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ParticipantDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ParticipantDetailsPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_data, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        nameField = (EditText) rootView.findViewById(R.id.your_name);
        emailField = (EditText) rootView.findViewById(R.id.your_email);

        Log.d("PDF", "ONCreateView");
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
        Log.d("PDF", "OnViewCreated");
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPage.getData().putString(ParticipantDetailsPage.PARTICIPANT_NAME, s.toString());
                } else {
                    mPage.getData().remove(ParticipantDetailsPage.PARTICIPANT_NAME);
                }
                mPage.notifyDataChanged();
            }
        });
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPage.getData().putString(ParticipantDetailsPage.PARTICIPANT_EMAIL, s.toString());
                }else{
                    mPage.getData().remove(ParticipantDetailsPage.PARTICIPANT_EMAIL);
                }
                mPage.notifyDataChanged();
            }
        });
        nameField.setText(mPage.getData().getString(ParticipantDetailsPage.PARTICIPANT_NAME));
        emailField.setText(mPage.getData().getString(ParticipantDetailsPage.PARTICIPANT_EMAIL));

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);


    }

}
