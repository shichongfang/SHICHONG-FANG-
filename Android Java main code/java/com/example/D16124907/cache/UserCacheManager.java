package com.example.D16124907.cache;

import com.example.D16124907.common.GlobalConstant;
import com.example.D16124907.common.D16124907Application;
import com.example.D16124907.entity.UserInfo;
import com.example.D16124907.utils.JsonParserUtil;

/**
 * user cache manager
 */
public class UserCacheManager {
	private final String CACHE_NAME = "D16124907_usercache";
	private final String USERINFO = "userinfo";

	private UserInfo mUserInfo;
	private static volatile UserCacheManager sUserCacheManager = null;
	private CacheUtil mCache;

	private UserCacheManager() {
		mCache = CacheUtil.get(D16124907Application.sContext, CACHE_NAME);
	}

	public static UserCacheManager getInstance() {
		if (sUserCacheManager == null) {
			synchronized (UserCacheManager.class) {
				if (sUserCacheManager == null) {
					sUserCacheManager = new UserCacheManager();
				}
			}
		}

		return sUserCacheManager;
	}

	public UserInfo getUserInfo() {
		if (mUserInfo != null) {
			return mUserInfo;
		}

		mUserInfo = getUserInfoFromSp();
		return mUserInfo;
	}

	private UserInfo getUserInfoFromSp() {
		UserInfo userInfo;

		String userJsonArray = mCache.getAsString(USERINFO);

		userInfo = JsonParserUtil.parseObject(userJsonArray, UserInfo.class);

		if (userInfo == null) {
			return null;
		}

		return userInfo;
	}

	public boolean isAdmin() {
		return getUserInfo() != null && getUserInfo().getType().equals(GlobalConstant.USER_TYPE_ADMIN);
	}

	public void saveUserInfo(UserInfo userInfo) {
		if (userInfo == null) {
			return;
		}

		mUserInfo = userInfo;
		saveUserInfoToSp(userInfo);
	}

	private void saveUserInfoToSp(UserInfo userInfo) {
		mCache.put(USERINFO, JsonParserUtil.toJsonString(userInfo));
	}

	public void removeUserInfo() {
		if (mUserInfo != null) {
			mUserInfo = null;
		}

		removeUserInfoFromSp();
	}

	private void removeUserInfoFromSp() {
		mCache.remove(USERINFO);
	}
}
