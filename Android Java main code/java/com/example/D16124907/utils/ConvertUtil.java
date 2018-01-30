package com.example.D16124907.utils;

/**
 * convert Util
 */
public class ConvertUtil {

	public final static int convertToInt(Object value, int defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		try {
			return Integer.valueOf(value.toString());
		} catch (Exception e) {
			try {
				return Double.valueOf(value.toString()).intValue();
			} catch (Exception e1) {
				return defaultValue;
			}
		}
	}

	public final static double convertToDouble(Object value, int defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		try {
			return Double.valueOf(value.toString());
		} catch (Exception e1) {
			return defaultValue;
		}
	}

	public static String convertToString(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value.toString();
	}

}
