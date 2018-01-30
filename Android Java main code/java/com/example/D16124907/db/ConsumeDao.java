package com.example.D16124907.db;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.entity.ConsumeInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Consume Dao
 */
public class ConsumeDao {
	public static final String TABLE_NAME = "consume";
	public static final String ID = "id";
	public static final String BOOKISBN = "bookisbn";
	public static final String BOOKNAME = "bookname";
	public static final String USERNUMBER = "usernumber";
	public static final String USERNAME = "username";
	public static final String CREATEDATE = "createdate";

	private DBHelper dbHelper;

	public ConsumeDao(Context context) {
		dbHelper = new DBHelper(context);
	}

	public long saveConsume(ConsumeInfo consumeInfo) {
		if (consumeInfo == null) {
			return -1;
		}
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = getContentValues(consumeInfo);
		long insert = database.insert(TABLE_NAME, null, values);
		database.close();
		return insert;
	}

	public int deleteConsume(int id) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int affectNum = database.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(id) });
		database.close();
		return affectNum;
	}

	public List<ConsumeInfo> getAllConsume() {
		List<ConsumeInfo> consumeList = new ArrayList<ConsumeInfo>();
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			ConsumeInfo consumeInfo = getConsumeInfoFromCusor(cursor);
			if (consumeInfo == null) {
				continue;
			}
			consumeList.add(consumeInfo);
		}
		cursor.close();
		database.close();
		return consumeList;
	}

	public ConsumeInfo getConsume(String id) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, ID + "=?", new String[] { id }, null, null, null);

		ConsumeInfo consumeInfo = null;
		if (cursor.moveToFirst()) {
			consumeInfo = getConsumeInfoFromCusor(cursor);
		}
		database.close();
		return consumeInfo;
	}

	public List<ConsumeInfo> getConsumeByUserNumberAndBookISBN(String userNumber, String bookISBN) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, USERNUMBER + "=? and " + BOOKISBN + "=?",
				new String[] { userNumber, bookISBN }, null, null, null);

		List<ConsumeInfo> consumeList = new ArrayList<ConsumeInfo>();
		while (cursor.moveToNext()) {
			ConsumeInfo consumeInfo = getConsumeInfoFromCusor(cursor);
			if (consumeInfo == null) {
				continue;
			}
			consumeList.add(consumeInfo);
		}

		cursor.close();
		database.close();
		return consumeList;
	}

	public List<ConsumeInfo> getConsumeByBookISBN(String bookISBN) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, BOOKISBN + "=?", new String[] { bookISBN }, null, null, null);

		List<ConsumeInfo> consumeList = new ArrayList<ConsumeInfo>();
		while (cursor.moveToNext()) {
			ConsumeInfo consumeInfo = getConsumeInfoFromCusor(cursor);
			if (consumeInfo == null) {
				continue;
			}
			consumeList.add(consumeInfo);
		}

		cursor.close();
		database.close();
		return consumeList;
	}

	public List<ConsumeInfo> getConsumeByUserNumber(String userNumber) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, USERNUMBER + "=?", new String[] { userNumber }, null, null,
				null);

		List<ConsumeInfo> consumeList = new ArrayList<ConsumeInfo>();
		while (cursor.moveToNext()) {
			ConsumeInfo consumeInfo = getConsumeInfoFromCusor(cursor);
			if (consumeInfo == null) {
				continue;
			}
			consumeList.add(consumeInfo);
		}

		cursor.close();
		database.close();
		return consumeList;
	}

	public List<ConsumeInfo> getConsumeByCreateDate(String createDate) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, CREATEDATE + "=?", new String[] { createDate }, null, null,
				null);

		List<ConsumeInfo> consumeList = new ArrayList<ConsumeInfo>();
		while (cursor.moveToNext()) {
			ConsumeInfo consumeInfo = getConsumeInfoFromCusor(cursor);
			if (consumeInfo == null) {
				continue;
			}
			consumeList.add(consumeInfo);
		}

		cursor.close();
		database.close();
		return consumeList;
	}

	private ContentValues getContentValues(ConsumeInfo consumeInfo) {
		try {
			ContentValues values = new ContentValues();
			values.put(BOOKISBN, consumeInfo.getBookISBN());
			values.put(BOOKNAME, consumeInfo.getBookName());
			values.put(USERNUMBER, consumeInfo.getUserNumber());
			values.put(USERNAME, consumeInfo.getUserName());
			values.put(CREATEDATE, consumeInfo.getCreateDate());
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ConsumeInfo getConsumeInfoFromCusor(Cursor cursor) {
		try {
			ConsumeInfo consumeInfo = new ConsumeInfo();
			consumeInfo.setId(cursor.getInt(cursor.getColumnIndex(ID)));
			consumeInfo.setBookISBN(cursor.getString(cursor.getColumnIndex(BOOKISBN)));
			consumeInfo.setBookName(cursor.getString(cursor.getColumnIndex(BOOKNAME)));
			consumeInfo.setUserNumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
			consumeInfo.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME)));
			consumeInfo.setCreateDate(cursor.getString(cursor.getColumnIndex(CREATEDATE)));
			return consumeInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}