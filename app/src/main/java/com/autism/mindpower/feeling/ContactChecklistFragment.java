package com.autism.mindpower.feeling;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase
 */
public class ContactChecklistFragment extends Fragment {

    private ListView listView;
    private ContactDatabase database;


    public ContactChecklistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = new ContactDatabase(getActivity().getApplicationContext());
        database.open();
        return inflater.inflate(R.layout.fragment_contact_checklist, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        // Setting up listener for items that are clicked on the list
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {

                }
            }
        };

        listView = (ListView) getView().findViewById(R.id.contacts_listview);
        listView.setOnItemClickListener(itemClickListener);

        ArrayList<Contact> contactList = database.getAllContacts();

        ArrayContactCheckboxAdapter listAdapter =
                new ArrayContactCheckboxAdapter(getActivity(), R.layout.item_checkbox, contactList);
        listView.setAdapter(listAdapter);
    }


    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }
}
