package com.example.D16124907.utils;

import java.util.Collection;
import java.util.Map;

import android.widget.TextView;

public class JudgeUtil {
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isMapEmpty(Map<?, ?> map) {
		return isNull(map) || map.size() == 0;
	}

	public static boolean isCollectionEmpty(Collection<?> collection) {
		return isNull(collection) || collection.size() == 0;
	}

	public static boolean isStringEmpty(String str) {
		return isNull(str) || str.trim().equals("");
	}

	public static boolean isStringEquals(String str, String msg) {
		return !isNull(str) && str.equals(msg);
	}

	public static boolean isStringContains(String str, String msg) {
		return !isNull(str) && str.contains(msg);
	}

	public static boolean isStringRange(String str, Integer minLength, Integer maxLength) {
		if (isNull(str)) {
			return false;
		}

		if (isNull(minLength) || str.length() < minLength) {
			return false;
		}

		if (isNull(maxLength) || str.length() > maxLength) {
			return false;
		}

		return true;
	}

	public static boolean isStringTrimEquals(String str, String msg) {
		return !isNull(str) && str.trim().equals(msg);
	}

	public static boolean isTextViewEmpty(TextView tv) {
		return isNull(tv) || isStringEmpty(tv.getText().toString());
	}

	public static boolean isTextViewEquals(TextView tv, String msg) {
		return !isNull(tv) && isStringEquals(tv.getText().toString(), msg);
	}

	public static boolean isTextViewTrimEquals(TextView tv, String msg) {
		return !isNull(tv) && isStringTrimEquals(tv.getText().toString(), msg);
	}

	public static boolean isTextViewRange(TextView tv, Integer minLength, Integer maxLength) {
		return !isNull(tv) && isStringRange(tv.getText().toString(), minLength, maxLength);
	}
}
