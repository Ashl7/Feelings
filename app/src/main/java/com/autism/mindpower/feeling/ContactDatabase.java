package com.autism.mindpower.feeling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


/**
 * Created by Arash Nase on 4/17/2016.
 * This class creates Java model objects
 * It maintains the database connection and supports adding new comments and fetching all comments.
 */
public class ContactDatabase {

    private static final String TAG = ContactDatabase.class.getName();

    private SQLiteDatabase db;
    private ContactDatabaseHelper dbHelper;
    private Context mAppContext;

    private String[] allColumns = { ContactDatabaseHelper.COLUMN_ID, ContactDatabaseHelper.COLUMN_CONTACT_NUMBER,
            ContactDatabaseHelper.COLUMN_CONTACT_NAME, ContactDatabaseHelper.COLUMN_CONTACT_DATE_ADDED};




    //INIT
    public ContactDatabase(Context context){
        mAppContext = context;
        dbHelper = new ContactDatabaseHelper(mAppContext);
    }




    // make sure to use open() AND close() whenever you use the database
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();   //get a reference to the database
    }

    public void close(){
        dbHelper.close();
    }




    // Add a new comment to database
    public void insertComment(Contact contact){
        ContentValues values = new ContentValues();
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER, contact.getNumber());
        values.put(ContactDatabaseHelper.COLUMN_CONTACT_NAME, contact.getName());
        //values.put(ContactDatabaseHelper.COLUMN_CONTACT_DATE_ADDED, getDate());

        db.insert(ContactDatabaseHelper.TABLE_CONTACTS, null, values);
        db.close();
    }

    // Delete a comment from the database
    public void deleteComment(Contact contact) {
        db.execSQL("DELETE FROM " + ContactDatabaseHelper.TABLE_CONTACTS + " WHERE " +
                ContactDatabaseHelper.COLUMN_CONTACT_NUMBER + "=\"" + contact.getNumber() + "\";");
    }



    // Get ALL comments in the database, via an ArrayList
    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Cursor cursor = db.query(ContactDatabaseHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact = cursorToComment(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }

        cursor.close();     // make sure to close the cursor
        return contacts;
    }


    // Change a cursor(result of a query) to a Comment object, and return the Comment
    private Contact cursorToComment(Cursor cursor) {
        if(cursor.isBeforeFirst() || cursor.isAfterLast())
            return null;

        Contact contact = new Contact(null, null);

        contact.setNumber(cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NUMBER)));
        contact.setName(cursor.getString(cursor.getColumnIndex(ContactDatabaseHelper.COLUMN_CONTACT_NAME)));

        return contact;
    }
}
