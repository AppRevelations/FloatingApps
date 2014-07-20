package com.apprevelations.floatingapps;

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
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "PinnedApps";

	// Contacts table name
	private static final String TABLE_CONTACTS = "app_packages";

	// Contacts Table Columns names
	private static final String KEY_STATUS = "status";
	private static final String KEY_NAME = "name";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_STATUS + " INTEGER," + KEY_NAME + " TEXT," + " PRIMARY KEY(" + KEY_NAME + ")"+ ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addPackage(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName()); // Contact Name
		values.put(KEY_STATUS, contact.getStatus()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_STATUS,
				KEY_NAME }, KEY_STATUS + "=?",
				new String[] { String.valueOf(1) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setStatus(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_STATUS, contact.getStatus());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_NAME + " = ?",
				new String[] { String.valueOf(contact.getName()) });
		
		
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_STATUS + " = ?",
				new String[] { String.valueOf(contact.getStatus()) });
		db.close();
	}


	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
