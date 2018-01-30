package com.example.D16124907.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtil {
	public static <T> void startActivity(Context context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}

	public static <T> void startActivity(Context context, Class<T> cls, Bundle bundle) {
		Intent intent = new Intent(context, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	public static <T> void startActivityForResult(Activity activity, int requstCode, Class<T> cls) {
		Intent intent = new Intent(activity, cls);
		activity.startActivityForResult(intent, requstCode);
	}

	public static <T> void startActivityForResult(Activity activity, int requstCode, Class<T> cls, Bundle bundle) {
		Intent intent = new Intent(activity, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		activity.startActivityForResult(intent, requstCode);
	}
}
