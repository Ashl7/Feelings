package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Arash Nase on 1/9/2017.
 * This adapter connects database contacts to the viewlist of checklists in @ContactCheckListFragment
 */

public class ArrayContactCheckboxAdapter extends ArrayAdapter<Contact> {
    ContactDatabase database;
    ArrayList<Contact> contactsCanSend;
    ArrayList<Contact> contactsAll;
    HashSet<String> nameSet;    //hashset of all contact names with can_send set to 1


    public ArrayContactCheckboxAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);

        database = new ContactDatabase(context);
        database.open();
        contactsCanSend = database.getCanSendContacts();
        contactsAll = database.getAllContacts();
        database.close();

        nameSet = new HashSet<>();
        for(Contact c : contactsCanSend)
            nameSet.add(c.getName());
    }


    // This method indicates how the listView should look
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

        // if can_send is one for this contact, in its view set the tick
        if(nameSet.contains(c.getName()))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        return convertView;         // Return the completed view to render on screen
    }
}
