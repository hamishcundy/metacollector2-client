package nz.co.hamishcundy.metacollector2.ui;

import android.support.v4.app.Fragment;

import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.model.ReviewItem;

import java.util.ArrayList;

/**
 * Created by hamish on 8/06/15.
 */
public class TestPage extends Page {

    public TestPage(ModelCallbacks callbacks, String title){
        super(callbacks, title);

    }

    @Override
    public Fragment createFragment() {
        return TestFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> arrayList) {

    }
}
