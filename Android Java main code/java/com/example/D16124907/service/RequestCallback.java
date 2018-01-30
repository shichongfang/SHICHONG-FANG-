package com.example.D16124907.service;

public interface RequestCallback<T> {
	void onSuccess(T result);

	void onFail(String errorMessage, int errorCode);
}
