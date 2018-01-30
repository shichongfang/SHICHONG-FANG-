package com.example.D16124907.db;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.entity.BookInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Book Dao
 */
public class BookDao {
	public static final String TABLE_NAME = "book";
	public static final String ISBN = "ISBN";
	public static final String NAME = "name";
	public static final String AUTHOR = "author";
	public static final String PUBLISHING = "publishing";
	public static final String SALESPRICE = "salesPrice";
	public static final String STOCKCOUNT = "stockCount";

	private DBHelper dbHelper;

	public BookDao(Context context) {
		dbHelper = new DBHelper(context);
	}

	public long saveBook(BookInfo bookInfo) {
		if (bookInfo == null) {
			return -1;
		}
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = getContentValues(bookInfo);
		long insert = database.insert(TABLE_NAME, null, values);
		database.close();
		return insert;
	}

	public int deleteBook(String isbn) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int affectNum = database.delete(TABLE_NAME, ISBN + "=?", new String[] { isbn });
		database.close();
		return affectNum;
	}

	public int updateBook(String isbn, BookInfo bookInfo) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = getContentValues(bookInfo);
		if (values == null) {
			return 0;
		}
		int affectNum = database.update(TABLE_NAME, values, ISBN + "=?", new String[] { isbn });
		database.close();
		return affectNum;
	}

	public List<BookInfo> getAllBook() {
		List<BookInfo> bookList = new ArrayList<BookInfo>();
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			BookInfo bookInfo = getBookInfoFromCusor(cursor);
			if (bookInfo == null) {
				continue;
			}
			bookList.add(bookInfo);
		}
		cursor.close();
		database.close();
		return bookList;
	}

	public BookInfo getBookByISBN(String isbn) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, ISBN + "=?", new String[] { isbn }, null, null, null);

		BookInfo bookInfo = null;
		if (cursor.moveToFirst()) {
			bookInfo = getBookInfoFromCusor(cursor);
		}
		database.close();
		return bookInfo;
	}

	public List<BookInfo> getBookByName(String name) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, NAME + "=?", new String[] { name }, null, null, null);

		List<BookInfo> bookList = new ArrayList<BookInfo>();
		while (cursor.moveToNext()) {
			BookInfo bookInfo = getBookInfoFromCusor(cursor);
			if (bookInfo == null) {
				continue;
			}
			bookList.add(bookInfo);
		}

		cursor.close();
		database.close();
		return bookList;
	}

	public List<BookInfo> getBookByAuthor(String author) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, AUTHOR + "=?", new String[] { author }, null, null, null);

		List<BookInfo> bookList = new ArrayList<BookInfo>();
		while (cursor.moveToNext()) {
			BookInfo bookInfo = getBookInfoFromCusor(cursor);
			if (bookInfo == null) {
				continue;
			}
			bookList.add(bookInfo);
		}

		cursor.close();
		database.close();
		return bookList;
	}

	public List<BookInfo> getBookByPublishing(String publishing) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(TABLE_NAME, null, PUBLISHING + "=?", new String[] { publishing }, null, null,
				null);

		List<BookInfo> bookList = new ArrayList<BookInfo>();
		while (cursor.moveToNext()) {
			BookInfo bookInfo = getBookInfoFromCusor(cursor);
			if (bookInfo == null) {
				continue;
			}
			bookList.add(bookInfo);
		}

		cursor.close();
		database.close();
		return bookList;
	}

	private ContentValues getContentValues(BookInfo bookInfo) {
		try {
			ContentValues values = new ContentValues();
			values.put(ISBN, bookInfo.getISBN());
			values.put(NAME, bookInfo.getName());
			values.put(AUTHOR, bookInfo.getAuthor());
			values.put(PUBLISHING, bookInfo.getPublishing());
			values.put(SALESPRICE, bookInfo.getSalesPrice());
			values.put(STOCKCOUNT, bookInfo.getStockCount());
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private BookInfo getBookInfoFromCusor(Cursor cursor) {
		try {
			BookInfo bookInfo = new BookInfo();
			bookInfo.setISBN(cursor.getString(cursor.getColumnIndex(ISBN)));
			bookInfo.setName(cursor.getString(cursor.getColumnIndex(NAME)));
			bookInfo.setAuthor(cursor.getString(cursor.getColumnIndex(AUTHOR)));
			bookInfo.setPublishing(cursor.getString(cursor.getColumnIndex(PUBLISHING)));
			bookInfo.setSalesPrice(cursor.getDouble(cursor.getColumnIndex(SALESPRICE)));
			bookInfo.setStockCount(cursor.getInt(cursor.getColumnIndex(STOCKCOUNT)));
			return bookInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}