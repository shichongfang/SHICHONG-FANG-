package com.example.D16124907.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
	private static ProgressDialog dialog;
	protected static Toast toast = null;

	public static void showText(Context context, String msg) {
		showText(context, msg, Toast.LENGTH_SHORT);
	}

	public static void showText(Context context, String msg, int length_short) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, length_short);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	public static void showLoadDataDialog(Context context, String title, String msg) {
		dialog = ProgressDialog.show(context, title, msg, true);
	}

	public static void dismissLoadDataDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
