package com.autism.mindpower.feeling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Arash Nase on 4/15/2016.
 * The actvity where users add/remove contacts to the database of people the app should send text to
 */
public class ContactsActivity extends AppCompatActivity {

    private ImageButton button;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;

    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    private Button saveButton;

    ContactDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        db = new ContactDatabase(getApplicationContext());
        db.open();

        editText = (EditText) findViewById(R.id.editText);

        editText1 = (EditText) findViewById(R.id.editText1);

        editText2 = (EditText) findViewById(R.id.editText2);

        editText3 = (EditText) findViewById(R.id.editText3);

        editText4 = (EditText) findViewById(R.id.editText4);


        button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               deleteContact(editText);
            }
        });

        button1 = (ImageButton) findViewById(R.id.imageButton1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContact(editText1);
            }
        });

        button2 = (ImageButton) findViewById(R.id.imageButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContact(editText2);
            }
        });

        button3 = (ImageButton) findViewById(R.id.imageButton3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContact(editText3);
            }
        });

        button4 = (ImageButton) findViewById(R.id.imageButton4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteContact(editText4);
            }
        });

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addContacts();
            }
        });



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
        // Close the activity
        finish();
    }

}
