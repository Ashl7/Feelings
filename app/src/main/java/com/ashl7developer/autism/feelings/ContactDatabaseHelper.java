package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Arash Nase on 4/17/2016.
 * SQLite Database for saving contacts
 */
public class ContactDatabaseHelper extends SQLiteOpenHelper  {

    private static final String TAG = ContactDatabaseHelper.class.getName();

    private static final String DB_NAME = "contacts.sqlite";    //or .db name of the file
    private static final int VERSION = 1;

    public static final String TABLE_CONTACTS = "contact";      //name of the table
    public static final String COLUMN_ID = "_id";   //primary key of the table
    public static final String COLUMN_CONTACT_NUMBER = "number";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_CAN_SEND = "can_send";    // can have values 0 or 1


    //Create the database
    public ContactDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        Log.d(TAG, "database instance created.");
    }


    //create the contact table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_CONTACTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACT_NUMBER + " TEXT, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_CONTACT_CAN_SEND + " TEXT" +
                ");";
        db.execSQL(createQuery);
        Log.d(TAG, TABLE_CONTACTS + " table created.");
    }


    // Upgrade the table, delete the older version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

}
