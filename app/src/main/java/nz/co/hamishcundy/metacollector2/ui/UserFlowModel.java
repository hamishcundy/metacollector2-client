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

import java.util.List;

import nz.co.hamishcundy.metacollector2.FormActivity;
import nz.co.hamishcundy.metacollector2.collection.MetadataCollectionSource;
import nz.co.hamishcundy.metacollector2.data.MetadataSource;
import nz.co.hamishcundy.metacollector2.wizard.MC2MultipleFixedChoicePage;

/**
 * Created by hamish on 7/06/15.
 */
public class UserFlowModel extends AbstractWizardModel {


    private static boolean detailsRequired;
    private static String terms;
    private static List collectionSources;

    public UserFlowModel(Context con){
        super(con);

    }





    @Override
    protected PageList onNewRootPageList() {
        Log.d("UFM2", "DetReq " + detailsRequired + " terms " + terms);
        String[] sources = getSourcesArray(collectionSources);
        Boolean[] requireds = getSourcesRequiredArray(collectionSources);
        return new PageList(new TermsAndConditionsPage(this, "Terms and Conditions", terms).setRequired(true), new ParticipantDetailsPage(this, "Participant details", detailsRequired).setRequired(true), new MC2MultipleFixedChoicePage(this, "Metadata sources").setRequiredChoices(requireds).setChoices(sources).setRequired(true));
//        return new PageList(new BranchPage(this, "Order callType")
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
//                        new SingleFixedChoicePage(this, "Salad callType").setChoices(
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

    private String[] getSourcesArray(List<MetadataSource> collectionSources) {
        String[] src = new String[collectionSources.size()];
        for(int i = 0; i < collectionSources.size(); i++){
            src[i] = MetadataCollectionSource.getName(collectionSources.get(i).key);
        }
        return src;
    }

    private Boolean[] getSourcesRequiredArray(List<MetadataSource> collectionSources) {
        Boolean[] src = new Boolean[collectionSources.size()];
        for(int i = 0; i < collectionSources.size(); i++){
            src[i] = collectionSources.get(i).required;
        }
        return src;
    }

    public static AbstractWizardModel create(Context con, String terms2, boolean details_required, List<MetadataSource> sources) {
        terms = terms2;
        detailsRequired = details_required;
        collectionSources = sources;
        return new UserFlowModel(con);
    }
}
