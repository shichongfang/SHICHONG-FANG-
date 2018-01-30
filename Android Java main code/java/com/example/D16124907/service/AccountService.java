package com.example.D16124907.service;

import java.util.List;

import com.example.D16124907.common.GlobalConstant;
import com.example.D16124907.common.D16124907Application;
import com.example.D16124907.db.UserDao;
import com.example.D16124907.entity.UserInfo;
import com.example.D16124907.utils.JudgeUtil;

/**
 * User account service
 */
public class AccountService extends BaseService {

	public void login(final String number, final String password, final RequestCallback<UserInfo> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				UserInfo userInfo = findAdmin(number);

				if (userInfo == null) {
					userInfo = new UserDao(D16124907Application.sContext).getUser(number, password);
				}

				if (userInfo == null || !JudgeUtil.isStringEquals(userInfo.getPassword(), password)) {
					postFail(callback, "ERROR Incorrect username or password!", 1001);
					return;
				}

				postSuccess(callback, userInfo);
			}
		});
	}

	public void register(String number, String password, String userName, String email, String age, String language,
			RequestCallback<UserInfo> callback) {
		UserDao userDao = new UserDao(D16124907Application.sContext);
		UserInfo userInfo = findAdmin(number);
		if (userInfo != null) {
			postFail(callback, "User already exists！", 1003);
			return;
		}

		userInfo = userDao.getUserByNumber(number);
		if (userInfo != null) {
			postFail(callback, "User already exists！", 1003);
			return;
		}

		userInfo = new UserInfo();
		userInfo.setNumber(number);
		userInfo.setPassword(password);
		userInfo.setUserName(userName);
		userInfo.setType(GlobalConstant.USER_TYPE_COMMON);
		userInfo.setEmail(email);
		try {
			userInfo.setAge(Integer.parseInt(age));
		} catch (Exception e) {
			e.printStackTrace();
		}
		userInfo.setLanguage(language);

		long insert = userDao.saveUser(userInfo);

		if (insert < 0) {
			postFail(callback, "Registration failure! Please Try Again！", 1002);
			return;
		}

		postSuccess(callback, userInfo);
	}

	private UserInfo findAdmin(String number) {
		for (UserInfo userInfo : GlobalConstant.adminList) {
			if (JudgeUtil.isStringEquals(userInfo.getNumber(), number)) {
				return userInfo;
			}
		}

		return null;
	}

	public void getAllUser(final RequestCallback<List<UserInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<UserInfo> userList = new UserDao(D16124907Application.sContext).getAllUser();

				if (userList == null) {
					postFail(callback, "Failed to obtain a user list！", 1001);
					return;
				}

				postSuccess(callback, userList);
			}
		});
	}

	public void deleteUser(final String number, final RequestCallback<Boolean> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				int affectNum = new UserDao(D16124907Application.sContext).deleteUser(number);

				if (affectNum < 1) {
					postFail(callback, "Delete failed！", 1005);
					return;
				}

				postSuccess(callback, true);
			}
		});
	}

	public void findUserByNumber(final String number, final RequestCallback<UserInfo> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				UserInfo userInfo = new UserDao(D16124907Application.sContext).getUserByNumber(number);

				if (userInfo == null) {
					postFail(callback, "No found User！", 1005);
					return;
				}

				postSuccess(callback, userInfo);
			}
		});
	}

	public void findUserByUserName(final String userName, final RequestCallback<List<UserInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<UserInfo> userInfoList = new UserDao(D16124907Application.sContext).getUserByUserName(userName);

				if (userInfoList == null) {
					postFail(callback, "No found User！", 1005);
					return;
				}

				postSuccess(callback, userInfoList);
			}
		});
	}

	public void findUserByEmail(final String calsses, final RequestCallback<List<UserInfo>> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				List<UserInfo> userInfoList = new UserDao(D16124907Application.sContext).getUserByEmail(calsses);

				if (userInfoList == null) {
					postFail(callback, "No found User！", 1005);
					return;
				}

				postSuccess(callback, userInfoList);
			}
		});
	}

	public void updateUserByNumber(final UserInfo userInfo, final RequestCallback<Boolean> callback) {
		DefaultThreadPool.getInstance().execute(new Runnable() {
			public void run() {
				int affectNum = new UserDao(D16124907Application.sContext).updateUser(userInfo.getNumber(), userInfo);

				if (affectNum == 0) {
					postFail(callback, "Update failed！", 1006);
					return;
				}

				postSuccess(callback, true);
			}
		});

	}

}
