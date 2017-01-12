package com.ashl7developer.autism.feelings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase
 * Fragment that the child uses to select which contacts send sms too
 * Sibling of EmojiGridFragment, contained in MainActivityPager
 */
public class ContactChecklistFragment extends Fragment {

    private static final String TAG = ContactChecklistFragment.class.getName();
    private ListView checkBoxListView;
    private ContactDatabase database;


    public ContactChecklistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_checklist, container, false);
        database = new ContactDatabase(getActivity().getApplicationContext());
        database.open();
        checkBoxListView = (ListView) view.findViewById(R.id.contacts_listview);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Contact> contactList = database.getAllContacts();
        ArrayContactCheckboxAdapter checkBoxListAdapter =
                new ArrayContactCheckboxAdapter(getActivity(), R.layout.item_checkbox, contactList);
        checkBoxListView.setAdapter(checkBoxListAdapter);

        // Setting up listener for items that are clicked on the list
        checkBoxListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckBox nameCheckBox = (CheckBox) view.findViewById(R.id.checkbox1);
                        String name = nameCheckBox.getText().toString();
                        if (nameCheckBox.isChecked()) {
                            database.updateCanSendColumn(name, 0);    //update the database row
                            nameCheckBox.setChecked(!nameCheckBox.isChecked()); //uncheck the checkbox
                        } else {
                            database.updateCanSendColumn(name, 1);
                            nameCheckBox.setChecked(!nameCheckBox.isChecked());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onStop();
        database.close();
    }
}
