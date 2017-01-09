package com.autism.mindpower.feeling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arash Nase on 1/9/2017.
 */
public class ArrayContactCheckboxAdapter extends ArrayAdapter<Contact> {

    public ArrayContactCheckboxAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact c = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_checkbox, parent, false);
        }
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox1);
        checkBox.setText(c.getName());
        return convertView;         // Return the completed view to render on screen
    }
}
