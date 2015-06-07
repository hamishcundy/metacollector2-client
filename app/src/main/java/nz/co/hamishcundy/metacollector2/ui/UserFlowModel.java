package nz.co.hamishcundy.metacollector2.ui;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.BranchPage;
import com.tech.freak.wizardpager.model.PageList;
import com.tech.freak.wizardpager.model.SingleFixedChoicePage;

/**
 * Created by hamish on 7/06/15.
 */
public class UserFlowModel extends AbstractWizardModel {


    public UserFlowModel(Context con){
        super(con);
    }


    @Override
    protected PageList onNewRootPageList() {
        return new PageList(new BranchPage(this, "Test branch").addBranch("Branch choice 1", new SingleFixedChoicePage(this, "Choice page 1").setChoices("1", "2")));
    }
}
