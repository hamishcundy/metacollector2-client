package nz.co.hamishcundy.metacollector2.ui;

import android.support.v4.app.Fragment;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by hamish on 15/06/15.
 */
public class ParticipantDetailsPage extends Page {


    public static final String PARTICIPANT_NAME = "participantName";
    public static final String PARTICIPANT_EMAIL = "participantEmail";
    private final boolean detailsRequired;


    public ParticipantDetailsPage(ModelCallbacks callbacks, String title, boolean detailsRequired) {
        super(callbacks, title);
        this.detailsRequired = detailsRequired;
    }

    @Override
    public Fragment createFragment() {
        return ParticipantDetailsFragment.create(getKey(), detailsRequired);
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> arrayList) {
        if(detailsRequired) {
            arrayList.add(new ReviewItem("Name", mData.getString(PARTICIPANT_NAME), getKey(), -1));
        }
        arrayList.add(new ReviewItem("Email", mData.getString(PARTICIPANT_EMAIL), getKey(), -1));
    }




    @Override
    public boolean isCompleted() {
        return (!detailsRequired || mData.getString(PARTICIPANT_NAME) != null) && mData.getString(PARTICIPANT_EMAIL) != null && mData.getString(PARTICIPANT_EMAIL).contains("@");

    }
}
