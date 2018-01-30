package com.example.D16124907.service;

import java.util.List;

import com.example.D16124907.common.D16124907Application;
import com.example.D16124907.db.BookDao;
import com.example.D16124907.entity.BookInfo;
import com.example.D16124907.utils.ConvertUtil;

/**
 * Book service
 */
public class BookService extends BaseService {

	public void addBook(String ISBN, String author, String name, String publishing, String price, String stockCount,
			RequestCallback<BookInfo> callback) {
		BookDao BookDao = new BookDao(D16124907Application.sContext);

		BookInfo bookInfo = BookDao.getBookByISBN(ISBN);
		if (bookInfo != null) {
			postFail(callback, "Books already exist！", 1003);
			return;
		}

		bookInfo = new BookInfo();
		bookInfo.setISBN(ISBN);
		bookInfo.setAuthor(author);
		bookInfo.setName(name);
		bookInfo.setPublishing(publishing);
		bookInfo.setSalesPrice(ConvertUtil.convertToDouble(price, 0));
		bookInfo.setStockCount(ConvertUtil.convertToInt(stockCount, 0));

		long insert = BookDao.saveBook(bookInfo);

		if (insert < 0) {
			postFail(callback, "New book failed! Please Try Again！", 1002);
			return;
		}

		postSuccess(callback, bookInfo);
	}

	public void getAllBook(final RequestCallback<List<BookInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<BookInfo> bookList = new BookDao(D16124907Application.sContext).getAllBook();

				if (bookList == null) {
					postFail(callback, "Failed to get a list of books！", 1001);
					return;
				}

				postSuccess(callback, bookList);
			}
		});
	}

	public void deleteBook(final String ISBN, final RequestCallback<Boolean> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				int affectNum = new BookDao(D16124907Application.sContext).deleteBook(ISBN);

				if (affectNum < 1) {
					postFail(callback, "Delete Failed！", 1005);
					return;
				}

				postSuccess(callback, true);
			}
		});
	}

	public void findBookByISBN(final String ISBN, final RequestCallback<BookInfo> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				BookInfo bookInfo = new BookDao(D16124907Application.sContext).getBookByISBN(ISBN);

				if (bookInfo == null) {
					postFail(callback, "No found Book！", 1005);
					return;
				}

				postSuccess(callback, bookInfo);
			}
		});
	}

	public void findBookByName(final String name, final RequestCallback<List<BookInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<BookInfo> bookInfoList = new BookDao(D16124907Application.sContext).getBookByName(name);

				if (bookInfoList == null) {
					postFail(callback, "No found Book！", 1005);
					return;
				}

				postSuccess(callback, bookInfoList);
			}
		});
	}

	public void findBookByAuthor(final String author, final RequestCallback<List<BookInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<BookInfo> bookInfoList = new BookDao(D16124907Application.sContext).getBookByAuthor(author);

				if (bookInfoList == null) {
					postFail(callback, "No found Book！", 1005);
					return;
				}

				postSuccess(callback, bookInfoList);
			}
		});
	}

	public void findBookByPublishing(final String publishing, final RequestCallback<List<BookInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<BookInfo> bookInfoList = new BookDao(D16124907Application.sContext).getBookByPublishing(publishing);

				if (bookInfoList == null) {
					postFail(callback, "No found Book！", 1005);
					return;
				}

				postSuccess(callback, bookInfoList);
			}
		});
	}

	public void updateBookByISBN(final BookInfo bookInfo, final RequestCallback<Boolean> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				int affectNum = new BookDao(D16124907Application.sContext).updateBook(bookInfo.getISBN(), bookInfo);

				if (affectNum == 0) {
					postFail(callback, "Update Book failed！", 1006);
					return;
				}

				postSuccess(callback, true);
			}
		});

	}

}
