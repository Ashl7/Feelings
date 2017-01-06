package com.autism.mindpower.feeling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Arash Nase on 4/17/2016.
 * It maintains the database connection and supports adding new contact or removing from database.
 */
public class ContactDatabase {

    private static final String TAG = ContactDatabase.class.getName();

    private SQLiteDatabase db;
    private ContactDatabaseHelper dbHelper;
    private Context mAppContext;

    private String[] allColumns = {
            ContactDatabaseHelper.COLUMN_ID,
            ContactDatabaseHelper.COLUMN_CONTACT_NUMBER,
            ContactDatabaseHelper.COLUMN_CONTACT_NAME,
    };


    // Initialize the database
    public ContactDatabase(Context context){
        mAppContext = context;
        dbHelper = new ContactDatabaseHelper(mAppContext);
    }


    public void open() {
        try {
            db = dbHelper.getWritableDatabase();   //get a reference to the database
        }
        catch (SQLException e) {
            e.printStackTrace();
            Log.v(TAG, "Opening database failed");
        }
    }


    public void close(){
        dbHelper.close();
    }


    // Add a new contact to database
    public void insertContact(Contact contact){
        ContentValues values = new ContentValues();
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER, contact.getNumber());
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NAME, contact.getName());
        db.insert(ContactDatabaseHelper.TABLE_CONTACTS, null, values);
    }


    // Delete a contact from the database
    public void deleteContact(Contact contact) {
        db.execSQL("DELETE FROM " + ContactDatabaseHelper.TABLE_CONTACTS + " WHERE " +
                ContactDatabaseHelper.COLUMN_CONTACT_NUMBER + "=\"" + contact.getNumber() + "\";");
    }


    // Get ALL contacts in the database in an ArrayList
    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = db.query(ContactDatabaseHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }

        cursor.close();     // make sure to close the cursor
        return contacts;
    }


    // Change a cursor(result of a query) to a Contact object, and return the Contact
    private Contact cursorToContact(Cursor cursor) {
        if(cursor.isBeforeFirst() || cursor.isAfterLast())
            return null;

        Contact contact = new Contact(null, null);

        contact.setNumber(cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER)));
        contact.setName(cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NAME)));

        return contact;
    }
}
