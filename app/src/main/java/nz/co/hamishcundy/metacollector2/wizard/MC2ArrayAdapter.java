package nz.co.hamishcundy.metacollector2.wizard;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

import nz.co.hamishcundy.metacollector2.collection.MetadataCollectionSource;

/**
 * Created by hamish on 19/08/15.
 */
public class MC2ArrayAdapter<S> extends ArrayAdapter {

    private ArrayList<Boolean> requireds;
    public MC2ArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        Log.d("MC2AA", "Required (" + getItem(position) + ": " + requireds.get(position));
        if(requireds.get(position)){
            v.setEnabled(false);
            ((CheckedTextView)v).setChecked(true);
            ((CheckedTextView)v).setClickable(true);
        }else{
            v.setEnabled(true);
            ((CheckedTextView)v).setChecked(false);
            ((CheckedTextView)v).setClickable(false);
        }
        return v;
    }

    public void setRequireds(ArrayList<Boolean> requireds) {
        this.requireds = requireds;

    }
}
