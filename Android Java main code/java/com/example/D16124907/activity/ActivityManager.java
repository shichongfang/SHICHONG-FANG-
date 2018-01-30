package com.example.D16124907.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Activity manager
 *
 */
public class ActivityManager {
	private static volatile ActivityManager sActivityManager = null;
	private List<Activity> activitys;

	private ActivityManager() {
		activitys = new ArrayList<Activity>();
	}

	public static ActivityManager getInstance() {
		if (sActivityManager == null) {
			synchronized (ActivityManager.class) {
				if (sActivityManager == null) {
					sActivityManager = new ActivityManager();
				}
			}
		}

		return sActivityManager;
	}

	public void create(Activity activity) {
		if (activity == null || activitys == null) {
			return;
		}

		activitys.add(activity);
	}

	public void destory(Activity activity) {
		if (activity == null || activitys == null) {
			return;
		}

		activitys.remove(activity);
	}

	/**
	 * Get the last Activity
	 */
	public Activity getLastActivity() {
		if (activitys == null || activitys.size() == 0) {
			return null;
		}
		return activitys.get(activitys.size() - 1);
	}

	/**
	 * Judge the Activity is running or not
	 */
	public boolean isActivityRunning(String className) {
		if (className != null) {
			for (Activity activity : activitys) {
				if (activity.getClass().getName().equals(className)) {
					return true;
				}
			}
		}
		return false;
	}

	public void finishAll() {
		for (Activity activity : activitys) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * Exit the app
	 */
	public void AppExit() {
		try {
			finishAll();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
