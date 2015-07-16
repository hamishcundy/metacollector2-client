package nz.co.hamishcundy.metacollector2.ui;

import android.content.Context;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.BranchPage;
import com.tech.freak.wizardpager.model.MultipleFixedChoicePage;
import com.tech.freak.wizardpager.model.NumberPage;
import com.tech.freak.wizardpager.model.PageList;
import com.tech.freak.wizardpager.model.SingleFixedChoicePage;
import com.tech.freak.wizardpager.model.TextPage;

/**
 * Created by hamish on 7/06/15.
 */
public class UserFlowModel extends AbstractWizardModel {


    private final boolean detailsRequired;
    private final String terms;

    public UserFlowModel(Context con, String terms, boolean detailsRequired){
        super(con);
        this.detailsRequired = detailsRequired;
        this.terms = terms;
    }


    @Override
    protected PageList onNewRootPageList() {
        return new PageList(new TermsAndConditionsPage(this, "Terms and Conditions").setTerms(terms).setRequired(true), new ParticipantDetailsPage(this, "Participant details").setRequireName(detailsRequired).setRequired(true), new MultipleFixedChoicePage(this, "Metadata sources").setChoices("Call logs", "SMS logs", "Contacts", "Browser bookmarks/history", "Facebook data", "Photo metadata", "Installed apps").setRequired(true));
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
}
