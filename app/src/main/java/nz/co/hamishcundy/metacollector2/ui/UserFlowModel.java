package nz.co.hamishcundy.metacollector2.ui;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.PageList;

/**
 * Created by hamish on 7/06/15.
 */
public class UserFlowModel extends AbstractWizardModel {


    public UserFlowModel(Context con){
        super(con);
    }


    @Override
    protected PageList onNewRootPageList() {
        return null;
    }
}
