package nz.co.hamishcundy.metacollector2.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.BranchPage;
import com.tech.freak.wizardpager.model.MultipleFixedChoicePage;
import com.tech.freak.wizardpager.model.NumberPage;
import com.tech.freak.wizardpager.model.PageList;
import com.tech.freak.wizardpager.model.SingleFixedChoicePage;
import com.tech.freak.wizardpager.model.TextPage;

import nz.co.hamishcundy.metacollector2.FormActivity;

/**
 * Created by hamish on 7/06/15.
 */
public class UserFlowModel extends AbstractWizardModel {


    private static boolean detailsRequired;
    private static String terms;

    public UserFlowModel(Context con){
        super(con);

    }





    @Override
    protected PageList onNewRootPageList() {
        Log.d("UFM2", "DetReq " + detailsRequired + " terms " + terms);
        return new PageList(new TermsAndConditionsPage(this, "Terms and Conditions", terms).setRequired(true), new ParticipantDetailsPage(this, "Participant details", detailsRequired).setRequired(true), new MultipleFixedChoicePage(this, "Metadata sources").setChoices("Call logs", "SMS logs", "Contacts", "Browser bookmarks/history", "Facebook data", "Photo metadata", "Installed apps").setRequired(true));
//        return new PageList(new BranchPage(this, "Order type")
//                .addBranch(
//                        "Sandwich",
//                        new SingleFixedChoicePage(this, "Bread").setChoices("White",
//                                "Wheat", "Rye", "Pretzel", "Ciabatta")
//                                .setRequired(true),
//
//                        new MultipleFixedChoicePage(this, "Meats").setChoices(
//                                "Pepperoni", "Turkey", "Ham", "Pastrami", "Roast Beef",
//                                "Bologna"),
//
//                        new MultipleFixedChoicePage(this, "Veggies").setChoices(
//                                "Tomatoes", "Lettuce", "Onions", "Pickles",
//                                "Cucumbers", "Peppers"),
//
//                        new MultipleFixedChoicePage(this, "Cheeses").setChoices(
//                                "Swiss", "American", "Pepperjack", "Muenster",
//                                "Provolone", "White American", "Cheddar", "Bleu"),
//
//                        new BranchPage(this, "Toasted?")
//                                .addBranch(
//                                        "Yes",
//                                        new SingleFixedChoicePage(this, "Toast time")
//                                                .setChoices("30 seconds", "1 minute",
//                                                        "2 minutes")).addBranch("No")
//                                .setValue("No"))
//
//                .addBranch(
//                        "Salad",
//                        new SingleFixedChoicePage(this, "Salad type").setChoices(
//                                "Greek", "Caesar").setRequired(true),
//
//                        new SingleFixedChoicePage(this, "Dressing").setChoices(
//                                "No dressing", "Balsamic", "Oil & vinegar",
//                                "Thousand Island", "Italian").setValue("No dressing"),
//                        new NumberPage(this, "How Many Salads?").setRequired(true)),
//                new TextPage(this, "Text").setRequired(true)
//
//                        .setRequired(true),
//
//                new TermsAndConditionsPage(this, "Terms and Conditions").setRequired(true));
    }

    public static AbstractWizardModel create(Context con, String terms2, boolean details_required) {
        terms = terms2;
        detailsRequired = details_required;

        return new UserFlowModel(con);
    }
}
