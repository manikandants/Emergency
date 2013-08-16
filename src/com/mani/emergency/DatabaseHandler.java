package com.mani.emergency;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_BUTTON = "button";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phoneNumber";
    private static final String KEY_STATUS = "status";    
    
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        String CREATE_BUTTON_TABLE = "CREATE TABLE " + TABLE_BUTTON + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_BUTTON_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUTTON);
        // Create tables again
        onCreate(db);
	}
	
	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, contact.getName()); // Contact Name
	    values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone Number
	 
	    // Inserting Row
	    db.insert(TABLE_CONTACTS, null, values);
	    db.close(); // Closing database connection
	}
	
	public void setButton(Boolean status) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_STATUS, status.toString());
	 
	    // updating row
	    db.insert(TABLE_BUTTON, null, values);
	}
	
	public int updateButton(Boolean status) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_STATUS, status.toString());
	 
	    // updating row
	    return db.update(TABLE_BUTTON, values, KEY_ID + " = ?", new String[]{"1"});
	}
	
	public Boolean getButton(int id) {
		String selectQuery = "SELECT  * FROM " + TABLE_BUTTON;
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
		 Boolean status = null;
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	status = Boolean.valueOf(cursor.getString(1));
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact
	    return status;
	}
	
	public Contact getContact(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
	            KEY_NAME, KEY_PH_NO }, KEY_ID + " = ?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2));
	    // return contact
	    return contact;
	}
	
	public List<Contact> getAllContacts() {
	    List<Contact> contactList = new ArrayList<Contact>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Contact contact = new Contact();
	            contact.setId(Integer.parseInt(cursor.getString(0)));
	            contact.setName(cursor.getString(1));
	            contact.setPhoneNumber(cursor.getString(2));
	            // Adding contact to list
	            contactList.add(contact);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return contactList;
	}
	
	public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
	public int updateContact(Contact contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, contact.getName());
	    values.put(KEY_PH_NO, contact.getPhoneNumber());
	 
	    // updating row
	    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(contact.getId()) });
	}
	public void deleteContact(Contact contact) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(contact.getId()) });
	    db.close();
	}
	public void deleteAllContacts() {
	    List<Contact> contactList = getAllContacts();
	    for(Contact contact:contactList){
	    	deleteContact(contact);
	    }
	}
}
