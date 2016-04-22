package com.autism.mindpower.feeling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeff To on 4/21/2016.
 */
public class ArrayContactAdapter extends ArrayAdapter<Contact> {

    public ArrayContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact c = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }
        // Lookup view for data population
        TextView tvContactName = (TextView) convertView.findViewById(R.id.tvContactName);
        TextView tvContactPhone = (TextView) convertView.findViewById(R.id.tvContactPhone);
        // Populate the data into the template view using the data object
        tvContactName.setText(c.getName());
        tvContactPhone.setText(c.getNumber());
        // Return the completed view to render on screen
        return convertView;
    }
}
