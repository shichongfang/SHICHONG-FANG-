package com.example.D16124907.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * sqlite helper
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "D16124907.db";
	private static int version = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, version);
	}

	public DBHelper(Context context, int version) {
		super(context, DATABASE_NAME, null, version);
		DBHelper.version = version;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create user class
		db.execSQL("CREATE TABLE IF NOT EXISTS " + UserDao.TABLE_NAME
				+ " (number VARCHAR PRIMARY KEY, password VARCHAR, userName VARCHAR, type VARCHAR, email VARCHAR, age INTEGER, language VARCHAR)");
		// Create book class
		db.execSQL("CREATE TABLE IF NOT EXISTS " + BookDao.TABLE_NAME
				+ " (ISBN VARCHAR PRIMARY KEY, name VARCHAR, author VARCHAR, publishing VARCHAR, salesPrice DOUBLE, stockCount INTEGER)");
		// Create consume table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + ConsumeDao.TABLE_NAME
				+ " (id VARCHAR INTEGER PRIMARY KEY, bookisbn VARCHAR, bookname VARCHAR, usernumber VARCHAR, username VARCHAR, createdate VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
