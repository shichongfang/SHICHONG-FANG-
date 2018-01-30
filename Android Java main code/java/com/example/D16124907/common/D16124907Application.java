package com.example.D16124907.common;

import com.example.D16124907.utils.log.Logger;

import android.app.Application;
import android.content.Context;

/**
 * Global Application
 */
public class D16124907Application extends Application {
	public static String TAG = "D16124907";
	public static Context sContext;

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = this;
		Logger.init(TAG);
	}
}
