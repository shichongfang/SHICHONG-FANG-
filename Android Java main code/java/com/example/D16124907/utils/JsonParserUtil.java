package com.example.D16124907.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.D16124907.utils.log.Logger;

/**
 * json and entity
 */
public class JsonParserUtil {
	public static <T> T parseObject(String text, Class<T> clazz) {
		try {
			return JSON.parseObject(text, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e("JsonParser  parseObject  json error!");
		}
		return null;
	}

	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		try {
			return JSON.parseArray(text, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e("JsonParser parseArray json error!");
		}
		return null;
	}

	public static String toJsonString(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e("JsonParser toJsonString json error!");
		}
		return null;
	}

	public static Object toJson(Object obj) {
		try {
			return JSON.toJSON(obj);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e("JsonParser toJson json error!");
		}
		return null;
	}

}
