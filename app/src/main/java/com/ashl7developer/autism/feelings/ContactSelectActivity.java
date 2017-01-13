package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Arash Nase on 4/15/2016.
 * The actvity where users add/remove contacts to the database
 * of people the app should send text to
 *
 * TODO: design it in a way that database only gets updated onces Save button is clicked, not everytime Select Contact is clicked.
 * (because right now, database will be updated whether or not user clicks back button, or clicks on Save button!)
 * TODO: Edge case: in AddNewContact, what if two contacts have the same name, different numbers?
 * TODO: Make a listview of buttons, more scalable!
 *
 */
public class ContactSelectActivity extends AppCompatActivity {

    private static final String TAG = ContactSelectActivity.class.getName();
    static final int PICK_CONTACT_REQUEST = 1;  //request code
    private int viewNumber; // helper variable to have connection between buttons and textviews

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button saveButton;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    private TextView[] textViewList;

    private ContactDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        saveButton = (Button) findViewById(R.id.save_button);

        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);
        textView3 = (TextView) findViewById(R.id.textview3);
        textView4 = (TextView) findViewById(R.id.textview4);
        textView5 = (TextView) findViewById(R.id.textview5);

        textViewList = new TextView[]{textView1, textView2, textView3, textView4, textView5};

        database = new ContactDatabase(getApplicationContext());
        database.open();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // if there are contacts in the database, put them on textviews
        intiTextViews();
    }


    // Buttons onClick method implementations
    public void onButton1Click(View view) {
        pickContact(1);
    }
    public void onButton2Click(View view) {
        pickContact(2);
    }
    public void onButton3Click(View view) {
        pickContact(3);
    }
    public void onButton4Click(View view) {
        pickContact(4);
    }
    public void onButton5Click(View view) {
        pickContact(5);
    }
    public void onSaveButtonClick(View view) {
        database.close(); //close database
        finish();
    }


    // Function that fires up Pick Contact activity
    // viewNumber: the number of the button that was clicked (the contact number)
    public void pickContact(int viewNumber) {
        this.viewNumber = viewNumber;
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        //show user only contacts w/ phone numbers
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }


    // Returns result from Contact activity after users selects a contact
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {        //check which request we're responding to
            if (resultCode == RESULT_OK) {               //make sure the request was successful
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int numberColumn = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumn);
                // Retrieve the name from the DISPLAY_NAME column
                int nameColumn = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumn);

                Contact contact = new Contact(number, name);
                addNewContact(contact, viewNumber);
            }
        }
    }


    // Populates the textviews when activity onStart() is called,
    // with the contacts already in database
    void intiTextViews() {
        ArrayList<Contact> contactList = database.getAllContacts();
        if(contactList.size() > 5) {    //edge case
            Log.d(TAG, "More contacts in database than there are textviews!");
            return;
        }
        for (int i = 0; i < contactList.size(); i++)
            this.textViewList[i].setText(contactList.get(i).getName());
    }


    // Adds contact to database, and then show it on the textview
    void addNewContact(Contact contact, int viewNumber) {
        ArrayList<Contact> contactList = database.getAllContacts();
        // if contact is already in database
        for(Contact oldContact: contactList) {                      //O(N), do search on SQLite not here, optimize later
            if(oldContact.getNumber().equals(contact.getNumber()))
                return;
        }
        // if we are replacing the new contact with an old one
        if(!textViewList[viewNumber-1].getText().toString().equals(
                getResources().getString(R.string.no_contact_selected_textview))) {
            database.deleteContact(textViewList[viewNumber-1].getText().toString());
        }

        database.insertContact(contact);
        //textViewList[this.viewNumber-1].setText(contact.getName()); source of a bug
    }

}
