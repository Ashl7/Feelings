package com.autism.mindpower.feeling;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Arash Nase on 4/15/2016.
 * The actvity where users add/remove contacts to the database
 * of people the app should send text to
 */
public class ContactsActivity extends AppCompatActivity {

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
    private HashMap<TextView, Contact> textViewContactHashMap;

    private ContactDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //fillTextViews(); //with contacts already saved in database

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

        textViewContactHashMap = new HashMap<>();
        textViewList = new TextView[]{textView1, textView2, textView3, textView4, textView5};

        db = new ContactDatabase(getApplicationContext());
        db.open();
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
        addContacts();
        finish();
    }

    // Function that fires up Contact activity
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
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                // Retrieve the name from the DISPLAY_NAME column
                int nameCol = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameCol);

                // save the contact and its corresponding textview to map,
                // and set textview to contact.name
                Contact contact = new Contact(number, name);
                textViewContactHashMap.put(textViewList[this.viewNumber-1], contact);
                textViewList[this.viewNumber-1].setText(name);
            }
        }
    }

    // Populates the textviews when activity comes up, with the contacts already in database
    void fillTextViews() {
        ArrayList<Contact> contactList = db.getContacts();
        // put contacts in the database on the textviews
        for (int i = 0; i < textViewList.length; i++)
            this.textViewList[i].setText(contactList.get(i).getName());
    }

    // Adds contact to database
    void addContacts() {
        String number1 = textView1.getText().toString();
        String number2 = textView2.getText().toString();
        String number3 = textView3.getText().toString();
        String number4 = textView4.getText().toString();
        String number5 = textView5.getText().toString();

        //if there is a number entered, add it to the database
        if (!number1.equals(""))
            db.insertContact(new Contact(number1,"Contact 1"));
        if (!number2.equals(""))
            db.insertContact(new Contact(number2,"Contact 2"));
        if (!number3.equals(""))
            db.insertContact(new Contact(number3,"Contact 3"));
        if (!number4.equals(""))
            db.insertContact(new Contact(number4,"Contact 4"));
        if (!number5.equals(""))
            db.insertContact(new Contact(number5,"Contact 5"));

        db.close(); //close db
    }

}
