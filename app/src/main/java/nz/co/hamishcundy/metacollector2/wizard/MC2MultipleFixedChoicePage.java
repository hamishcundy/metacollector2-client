package nz.co.hamishcundy.metacollector2.wizard;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.MultipleFixedChoicePage;
import com.tech.freak.wizardpager.model.SingleFixedChoicePage;

import java.util.ArrayList;

import nz.co.hamishcundy.metacollector2.data.MetadataSource;

/**
 * Created by hamish on 19/08/15.
 */
public class MC2MultipleFixedChoicePage extends MultipleFixedChoicePage {

    protected Boolean[] requireds;

    public MC2MultipleFixedChoicePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        Log.d("MC2MFCP", "Create frag");
        return MC2MultipleChoiceFragment.create(getKey());
    }


    public boolean getOptionRequiredAt(int position) {
        Log.d("MC2MFCP", "Get reqd at position " + position);
        return requireds[position];
    }

    public SingleFixedChoicePage setRequiredChoices(Boolean[] requireds) {
        Log.d("MC2MFCP", "Requireds: " + requireds);
        this.requireds = requireds;
        return this;
    }
}
