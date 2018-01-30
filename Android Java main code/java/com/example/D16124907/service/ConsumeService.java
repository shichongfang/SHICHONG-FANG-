package com.example.D16124907.service;

import java.util.List;

import com.example.D16124907.common.D16124907Application;
import com.example.D16124907.db.ConsumeDao;
import com.example.D16124907.entity.ConsumeInfo;
import com.example.D16124907.utils.SpecialUtil;

/**
 * Consume record service
 */
public class ConsumeService extends BaseService {

	public void addConsume(String bookISBN, String bookName, String userNumber, String userName,
			RequestCallback<Boolean> callback) {
		ConsumeDao ConsumeDao = new ConsumeDao(D16124907Application.sContext);

		ConsumeInfo consumeInfo = new ConsumeInfo();
		consumeInfo.setBookISBN(bookISBN);
		consumeInfo.setBookName(bookName);
		consumeInfo.setUserNumber(userNumber);
		consumeInfo.setUserName(userName);
		consumeInfo.setCreateDate(SpecialUtil.getSysDate("yyyy-MM-dd HH:mm:ss"));

		long insert = ConsumeDao.saveConsume(consumeInfo);

		if (insert < 0) {
			postFail(callback, "Failed to add consumer records！", 1002);
			return;
		}

		postSuccess(callback, true);
	}

	public void getAllConsume(final RequestCallback<List<ConsumeInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<ConsumeInfo> consumeList = new ConsumeDao(D16124907Application.sContext).getAllConsume();

				if (consumeList == null) {
					postFail(callback, "Failed to obtain consumption lis！", 1001);
					return;
				}

				postSuccess(callback, consumeList);
			}
		});
	}

	public void deleteConsume(final int id, final RequestCallback<Boolean> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				int affectNum = new ConsumeDao(D16124907Application.sContext).deleteConsume(id);

				if (affectNum < 1) {
					postFail(callback, "Delete Consum Record Failed ！", 1005);
					return;
				}

				postSuccess(callback, true);
			}
		});
	}

	public void findConsumeByUserNumberAndBookISBN(final String userNumber, final String bookISBN,
			final RequestCallback<List<ConsumeInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<ConsumeInfo> consumeList = new ConsumeDao(D16124907Application.sContext)
						.getConsumeByUserNumberAndBookISBN(userNumber, bookISBN);

				if (consumeList == null) {
					postFail(callback, "No Found！", 1005);
					return;
				}

				postSuccess(callback, consumeList);
			}
		});
	}

	public void findConsumeByBookISBN(final String bookISBN, final RequestCallback<List<ConsumeInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<ConsumeInfo> consumeList = new ConsumeDao(D16124907Application.sContext).getConsumeByBookISBN(bookISBN);

				if (consumeList == null) {
					postFail(callback, "No Found！", 1005);
					return;
				}

				postSuccess(callback, consumeList);
			}
		});
	}

	public void findConsumeByUserNumber(final String userNumber, final RequestCallback<List<ConsumeInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<ConsumeInfo> consumeList = new ConsumeDao(D16124907Application.sContext)
						.getConsumeByUserNumber(userNumber);

				if (consumeList == null) {
					postFail(callback, "No Found！", 1005);
					return;
				}

				postSuccess(callback, consumeList);
			}
		});
	}

	public void findConsumeByCreateDate(final String createDate, final RequestCallback<List<ConsumeInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<ConsumeInfo> consumeList = new ConsumeDao(D16124907Application.sContext)
						.getConsumeByCreateDate(createDate);

				if (consumeList == null) {
					postFail(callback, "No Found！", 1005);
					return;
				}

				postSuccess(callback, consumeList);
			}
		});
	}

}
