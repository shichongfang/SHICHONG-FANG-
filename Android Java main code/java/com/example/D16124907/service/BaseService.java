package com.example.D16124907.service;

import android.os.Handler;

/**
 * Base service
 */
class BaseService {
	protected Handler mHandler;

	BaseService() {
		mHandler = new Handler();
	}

	protected <T> void postFail(final RequestCallback<T> callback, final String errorMessage, final int errorCode) {
		if (callback != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					callback.onFail(errorMessage, errorCode);
				}
			});
		}
	}

	protected <T> void postSuccess(final RequestCallback<T> callback, final T obj) {
		if (callback != null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					callback.onSuccess(obj);
				}
			});
		}
	}
}
