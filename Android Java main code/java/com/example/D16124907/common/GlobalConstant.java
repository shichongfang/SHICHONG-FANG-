package com.example.D16124907.common;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.entity.UserInfo;

/**
 * Global Constant
 */
public interface GlobalConstant {
	String LAST_USERNAME = "last_username";
	int RESULT_OK = 100;
	int RESULT_CANCEL = 101;
	String USER_TYPE_COMMON = "common";
	String USER_TYPE_ADMIN = "admin";

	// Administrator List
	List<UserInfo> adminList = new ArrayList<UserInfo>() {
		private static final long serialVersionUID = 116516613L;

		{
			add(new UserInfo("admin", "123456", "Admin", USER_TYPE_ADMIN, null, 21, "man"));
		}
	};
}
