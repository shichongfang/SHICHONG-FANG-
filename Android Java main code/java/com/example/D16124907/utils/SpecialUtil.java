package com.example.D16124907.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import com.example.D16124907.utils.log.Logger;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class SpecialUtil {
	private static long lastClickTime;


	public static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			Logger.d("isFastClick");
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * get system date
	 */
	public static String getSysDate() {
		return getSysDate("yyyy-MM-dd HH:mm:ss");
	}


	public static String getSysDate(String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr, Locale.getDefault());
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}


	public static String getSysWeek() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int week = c.get(Calendar.DAY_OF_WEEK);
		if (week < 1 || week > 7) {
			return null;
		}
		String[] weeks = new String[] { "日", "一", "二", "三", "四", "五", "六" };
		return weeks[week - 1];
	}

	/**
	 * get system date
	 */
	public static int getSysDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * get application version name
	 * */
	public static String getAppVersionName(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			Logger.e("Get version code fail");
		}
		return info.versionName;
	}


	public static int getAppVersionCode(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			Logger.e("Get version code fail");
		}
		return info.versionCode;
	}

	/**
	 * Get application's metadata
	 */
	public static String getMetaData(Context context, String dataName) {
		ApplicationInfo appInfo = null;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return appInfo.metaData.getString(dataName);
	}

	/**
	 * Get activity's metadata
	 */
	public static String getMetaDataFromActivity(Activity activity, String dataName) {
		ActivityInfo info = null;
		try {
			info = activity.getPackageManager().getActivityInfo(activity.getComponentName(),
					PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.metaData.getString(dataName);
	}

	public static Bitmap getBitmap(Context context, int resID) {
		return BitmapFactory.decodeResource(context.getResources(), resID);
	}

	public static Drawable getDrawable(Context context, int resID) {
		return new BitmapDrawable(context.getResources(), getBitmap(context, resID));
	}

	public static String getString(Context context, int resID) {
		return context.getResources().getString(resID);
	}

	public static float getDimension(Context context, int resID) {
		return context.getResources().getDimension(resID);
	}

	public static Integer getInteger(Context context, int resID) {
		return context.getResources().getInteger(resID);
	}

	public static String[] getArrays(Context context, int resID) {
		return context.getResources().getStringArray(resID);
	}

	public static String getDoubleStr(double numFloat, int length) {
		String format = "0.";
		while (length-- > 0) {
			format += "0";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String numStr = decimalFormat.format(numFloat);

		return numStr;
	}


	public static String getFloatStr(Float numFloat, int length) {
		String format = "0.";
		while (length-- > 0) {
			format += "0";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String numStr = decimalFormat.format(numFloat);

		return numStr;
	}


	public static String getFloatToIntStr(Float numFloat) {
		if (numFloat == null) {
			return "";
		}

		String numStr = "";

		try {
			numStr = (int) (float) numFloat + "";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numStr;
	}


	public static float getFloatDecimal(float num, int length) {
		return (float) (Math.round(num * 100)) / 100;
	}


	public static double getDoubleDecimal(double num, int length) {
		return (double) (Math.round(num * 100)) / 100;
	}


	public static String getFloatDecimalStr(Float numFloat, int length) {
		if (numFloat == null || length <= 0) {
			return "";
		}

		String numStr = "";
		numFloat += 1;

		try {
			int baseNum = (int) Math.pow(10, length);
			String numStrTemp = (int) (numFloat * baseNum) + "";
			numStr = numStrTemp.substring(numStrTemp.length() - length, numStrTemp.length());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numStr;
	}

	/**
	 * set error format
	 */
	public static void setTextViewError(Context context, TextView tv, String errorMsg) {
		// tv.setError(null);
		// tv.requestFocus();
		ToastUtil.showText(context, errorMsg);
	}

	public static void setError(TextView tv, String errorMsg) {
		tv.setError(errorMsg);
		tv.requestFocus();
		if (tv instanceof EditText && tv.length() > 0) {
			((EditText) tv).setSelection(tv.length() - 1);
		}
	}


	public static void startVibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}


	public static void startVibrate(final Activity activity, long[] pattern, boolean isRepeat) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}


	public static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		return dm.widthPixels;
	}

	public static int getwindowsHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		return dm.heightPixels;
	}

	public static Spanned convertUnderLine(String str) {
		return Html.fromHtml("<u>" + str + "</u>");
	}


	public static void startQQClient(Context context, String qq) {
		String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}


	public static void startCallActivity(Context context, String phoneNumber) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL); // 拨号界面，不是马上打电话
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}


	public static Bitmap zoomBitmap(Bitmap bitmap, int bmWidth, int bmHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) bmWidth) / width;
		float scaleHeight = ((float) bmHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newbm;
	}


	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}


	private static DelayedOperListener mDelayedOperListener;
	private static int mCurTimes;
	private static int mTotalTimes;
	private static long mDelayTime;
	private static Handler mDelayedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mCurTimes >= mTotalTimes) {
				mCurTimes = 0;
				mTotalTimes = 0;
				mDelayedOperListener = null;
				mDelayTime = 0;
				return;
			}

			mCurTimes++;

			if (mDelayedOperListener != null) {
				mDelayedOperListener.delayFinish(mCurTimes);
			}
			mDelayedHandler.sendEmptyMessageDelayed(1, mDelayTime);
		}
	};

	public static void startDelayedOper(final long delayTime, final int times,
			final DelayedOperListener delayedOperListener) {
		mDelayedOperListener = delayedOperListener;
		mTotalTimes = times;
		mCurTimes = 0;
		mDelayTime = delayTime;
		mDelayedHandler.sendEmptyMessageDelayed(1, delayTime);
	}

	public static void stopDelayedOper() {
		if (mDelayedHandler != null) {
			mDelayedHandler.removeCallbacksAndMessages(null);
			mCurTimes = 0;
			mTotalTimes = 0;
			mDelayedOperListener = null;
			mDelayTime = 0;
		}
	}

	public interface DelayedOperListener {
		void delayFinish(int times);
	}


	private static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static Random mRandom = new Random();

	public static String getRandomNumString(int length) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			builder.append(CHARS[mRandom.nextInt(CHARS.length)]);
		}

		return builder.toString();
	}


	public static void showSoftInput(Context context, View view, int flags) {
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(view, 0);
	}


	public static void hideSoftInput(Activity activity) {
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}


	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}
