package nz.co.hamishcundy.metacollector2.ui;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;


/**
 * A page asking for a name and an email.
 */
public class TermsAndConditionsPage extends Page {
    public static final String TERMS_ACCEPTED_KEY = "termsAccepted";


    public TermsAndConditionsPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TermsAndConditionsFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Terms & Conditions accepted", mData.getBoolean(TERMS_ACCEPTED_KEY)?"Yes":"No", getKey(), -1));

    }

    @Override
    public boolean isCompleted() {
        return mData.getBoolean(TERMS_ACCEPTED_KEY);
    }

    public Page setTerms(String terms) {
        return this;
    }
}
