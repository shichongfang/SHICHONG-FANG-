package com.example.D16124907.db;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.entity.UserInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * User Dao
 */
public class UserDao {
	public static final String TABLE_NAME = "user";
	public static final String NUMBER = "number";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "userName";
	public static final String TYPE = "type";
	public static final String EMAIL = "email";
	public static final String AGE = "age";
	public static final String LANGUAGE = "language";

	private DBHelper dbHelper;

	public UserDao(Context context) {
		dbHelper = new DBHelper(context);
	}

	public long saveUser(UserInfo userInfo) {
		if (userInfo == null) {
			return -1;
		}
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = getContentValues(userInfo);
		long insert = database.insert(TABLE_NAME, null, values);
		database.close();
		return insert;
	}

	public int deleteUser(String number) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int affectNum = database.delete(TABLE_NAME, NUMBER + "=?", new String[] { number });
		database.close();
		return affectNum;
	}

	public int updateUser(String number, UserInfo userInfo) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = getContentValues(userInfo);
		if (values == null) {
			return 0;
		}
		int affectNum = database.update(TABLE_NAME, values, NUMBER + "=?", new String[] { number });
		database.close();
		return affectNum;
	}

	public List<UserInfo> getAllUser() {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			UserInfo userInfo = getUserInfoFromCusor(cursor);
			if (userInfo == null) {
				continue;
			}
			userList.add(userInfo);
		}
		cursor.close();
		database.close();
		return userList;
	}

	public UserInfo getUserByNumber(String number) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, NUMBER + "=?", new String[] { number }, null, null, null);

		UserInfo userInfo = null;
		if (cursor.moveToFirst()) {
			userInfo = getUserInfoFromCusor(cursor);
		}

		cursor.close();
		database.close();
		return userInfo;
	}

	public List<UserInfo> getUserByUserName(String userName) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, USERNAME + "=?", new String[] { userName }, null, null, null);

		List<UserInfo> userList = new ArrayList<UserInfo>();
		while (cursor.moveToNext()) {
			UserInfo userInfo = getUserInfoFromCusor(cursor);
			if (userInfo == null) {
				continue;
			}
			userList.add(userInfo);
		}

		cursor.close();
		database.close();
		return userList;
	}

	public List<UserInfo> getUserByEmail(String calsses) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, EMAIL + "=?", new String[] { calsses }, null, null, null);

		List<UserInfo> userList = new ArrayList<UserInfo>();
		while (cursor.moveToNext()) {
			UserInfo userInfo = getUserInfoFromCusor(cursor);
			if (userInfo == null) {
				continue;
			}
			userList.add(userInfo);
		}

		cursor.close();
		database.close();
		return userList;
	}

	public UserInfo getUser(String number, String password) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, NUMBER + "=? and " + PASSWORD + "=?",
				new String[] { number, password }, null, null, null);

		UserInfo userInfo = null;
		if (cursor.moveToFirst()) {
			userInfo = getUserInfoFromCusor(cursor);
		}
		database.close();
		return userInfo;
	}

	private ContentValues getContentValues(UserInfo userInfo) {
		try {
			ContentValues values = new ContentValues();
			values.put(NUMBER, userInfo.getNumber());
			values.put(PASSWORD, userInfo.getPassword());
			values.put(USERNAME, userInfo.getUserName());
			values.put(TYPE, userInfo.getType());
			values.put(EMAIL, userInfo.getEmail());
			values.put(AGE, userInfo.getAge());
			values.put(LANGUAGE, userInfo.getLanguage());
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private UserInfo getUserInfoFromCusor(Cursor cursor) {
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
			userInfo.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
			userInfo.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME)));
			userInfo.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
			userInfo.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
			userInfo.setAge(cursor.getInt(cursor.getColumnIndex(AGE)));
			userInfo.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}