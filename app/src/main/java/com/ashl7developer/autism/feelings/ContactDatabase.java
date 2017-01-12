package com.ashl7developer.autism.feelings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Arash Nase on 4/17/2016.
 * Modified on 1/7/2017
 * It maintains the database connection and supports adding/removing/getting contacts from database.
 */
public class ContactDatabase {

    private static final String TAG = ContactDatabase.class.getName();

    private SQLiteDatabase database;
    private ContactDatabaseHelper dbHelper;
    private Context mAppContext;

    private String[] allColumns = {
            ContactDatabaseHelper.COLUMN_ID,
            ContactDatabaseHelper.COLUMN_CONTACT_NUMBER,
            ContactDatabaseHelper.COLUMN_CONTACT_NAME,
            ContactDatabaseHelper.COLUMN_CONTACT_CAN_SEND
    };


    // Initialize the database
    public ContactDatabase(Context context){
        mAppContext = context;
        dbHelper = new ContactDatabaseHelper(mAppContext);
    }


    public void open() {
        try {
            database = dbHelper.getWritableDatabase();   //get a reference to the database
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
        if(hasContact(contact)) {
            Log.d(TAG, "Contact already exists.");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER, contact.getNumber());
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NAME, contact.getName());
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_CAN_SEND, "1");
        database.insert(ContactDatabaseHelper.TABLE_CONTACTS, null, values);
    }


    // Sets COLUMN_CONTACT_CAN_SEND tp 0 or 1 if the contact's check is off/on in app
    public void updateCanSendColumn(String name, int canSend) {
        ContentValues values = new ContentValues();
        if(canSend == 1)
            values.put(ContactDatabaseHelper.COLUMN_CONTACT_CAN_SEND, "1");
        else
            values.put(ContactDatabaseHelper.COLUMN_CONTACT_CAN_SEND, "0");
        database.update(dbHelper.TABLE_CONTACTS, values,
                ContactDatabaseHelper.COLUMN_CONTACT_NAME + " = ?", new String[]{name});
        Log.d(TAG, name + " can_send is updated to " + canSend);
    }


    // Delete a contact from the database
    public void deleteContact(String contactName) {
        database.delete(ContactDatabaseHelper.TABLE_CONTACTS,
                ContactDatabaseHelper.COLUMN_CONTACT_NAME + " = ?",
                new String[]{contactName}) ;
        Log.d(TAG, contactName + " deleted from database.");
    }


    // Get ALL contacts in the database in an ArrayList
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(ContactDatabaseHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contacts.add(cursorToContact(cursor));
            cursor.moveToNext();
        }
        cursor.close();     // make sure to close the cursor
        return contacts;
    }


    // Get contacts with COLUMN_CONTACT_CAN_SEND == 1
    public ArrayList<Contact> getCanSendContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = database.query(
                ContactDatabaseHelper.TABLE_CONTACTS,
                allColumns,
                ContactDatabaseHelper.COLUMN_CONTACT_CAN_SEND + " = ?",
                new String[]{"1"},
                null, null, null);
        cursor.moveToFirst();
        cursor.getCount();
        while (!cursor.isAfterLast()) {
            contacts.add(cursorToContact(cursor));
            cursor.moveToNext();
        }
        cursor.close();     // make sure to close the cursor
        return contacts;
    }


    // Checks if a contact is in database or not
    public boolean hasContact(Contact contact) {
        String query = "SELECT * FROM " + ContactDatabaseHelper.TABLE_CONTACTS + " WHERE " +
                ContactDatabaseHelper.COLUMN_CONTACT_NUMBER + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{contact.getNumber()});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    // Change a cursor(result of a query) to a Contact object, and return the Contact
    private Contact cursorToContact(Cursor cursor) {
        if(cursor.isBeforeFirst() || cursor.isAfterLast())
            return null;
        return new Contact(cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER)),
                cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NAME)));
    }

}
