package nz.co.hamishcundy.metacollector2.wizard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.MultipleChoiceFragment;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nz.co.hamishcundy.metacollector2.R;

/**
 * Created by hamish on 19/08/15.
 */
public class MC2MultipleChoiceFragment extends MultipleChoiceFragment {

    private static final java.lang.String ARG_KEY = "key";
    private Page mPage;
    private PageFragmentCallbacks pfc;
    private String mKey;
    private ArrayList<String> mChoices;
    private ArrayList<Boolean> requireds;


    public static MultipleChoiceFragment create(String key) {
        Bundle args = new Bundle();
        args.putString("key", key);
        MC2MultipleChoiceFragment fragment = new MC2MultipleChoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = ((PageFragmentCallbacks)getActivity()).onGetPage(mKey);

        MC2MultipleFixedChoicePage fixedChoicePage = (MC2MultipleFixedChoicePage) mPage;
        mChoices = new ArrayList<String>();
        requireds = new ArrayList<Boolean>();
        for (int i = 0; i < fixedChoicePage.getOptionCount(); i++) {
            mChoices.add(fixedChoicePage.getOptionAt(i));
            requireds.add(fixedChoicePage.getOptionRequiredAt(i));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        pfc = (PageFragmentCallbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        final ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        MC2ArrayAdapter adap = new MC2ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                android.R.id.text1,
                mChoices);
        adap.setRequireds(requireds);
        setListAdapter(adap);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String s = "";
        for(Boolean b:requireds){
            s = s + b + " ";
        }
        Log.d("MC2MCF", "Requireds : " + s);
        // Pre-select currently selected items.
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> selectedItems = mPage.getData().getStringArrayList(
                        Page.SIMPLE_DATA_KEY);
                if (selectedItems == null || selectedItems.size() == 0) {
                    for (int i = 0; i < mChoices.size(); i++) {
                        if (requireds.get(i)) {
                            listView.setItemChecked(i, true);
                        }
                    }
                    return;
                }

                Set<String> selectedSet = new HashSet<String>(selectedItems);

                for (int i = 0; i < mChoices.size(); i++) {
                    if (selectedSet.contains(mChoices.get(i)) || requireds.get(i)) {
                        listView.setItemChecked(i, true);
                    }
                }
            }
        });

        return rootView;
    }
}
