package com.autism.mindpower.feeling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Arash Nase on 4/15/2016.
 * The actvity where users add/remove contacts to the database of people the app should send text to
 */
public class ContactsActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    private Button saveButton;

    private ContactDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        db = new ContactDatabase(getApplicationContext());
        db.open();



        fillEditTexts(); //with contacts already saved in database



        // Save Button
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addContacts();
                finish();
            }
        });

    }


    // If there are Contacts in the database, populate the editTexts
    void fillEditTexts() {
        ArrayList<Contact> contactArrayList;  //get all the contacts objects saved in database
        ArrayList<EditText> editTextsArrayList = new ArrayList<EditText>();

        contactArrayList = db.getContacts();

        editTextsArrayList.add(editText);
        editTextsArrayList.add(editText1);
        editTextsArrayList.add(editText2);
        editTextsArrayList.add(editText3);
        editTextsArrayList.add(editText4);

        // if there is any contact in database, put it in the editText
        for (int i = 0; i < contactArrayList.size(); i++) {
            editTextsArrayList.get(i).setText(contactArrayList.get(i).getNumber().toString());
        }
    }

    // Function to delete empty the EditText and delete its number from database
    void deleteContact(EditText editText){
        String number = editText.getText().toString();  //get the number
        if (!number.isEmpty()) {
            editText.setText("");   // clear the EditText
            db.deleteContact(new Contact(number, null));    //delete the contact from database
        }
    }

    // Function to add contact to database
    void addContacts() {
        // get the numbers in edittext fields
        String number1 = editText.getText().toString();
        String number2 = editText1.getText().toString();
        String number3 = editText2.getText().toString();
        String number4 = editText3.getText().toString();
        String number5 = editText4.getText().toString();

        // if there is a number entered, add it to the database
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

        // Close db
        db.close();
    }

}
