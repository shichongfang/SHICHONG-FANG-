package com.example.D16124907.activity;

import com.example.D16124907.R;
import com.example.D16124907.cache.CacheUtil;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.common.GlobalConstant;
import com.example.D16124907.entity.UserInfo;
import com.example.D16124907.service.AccountService;
import com.example.D16124907.utils.DialogManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	private EditText mUserNameET;
	private EditText mPasswordET;

	private AccountService mAccountService;

	@Override
	protected void initVariables() {
		super.initVariables();
		mAccountService = new AccountService();
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		mUserNameET = (EditText) findViewById(R.id.et_login_username);
		mPasswordET = (EditText) findViewById(R.id.et_login_password);

		findViewById(R.id.tv_login_findpassword).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onFindPasswordClick();
			}
		});
		findViewById(R.id.tv_login_register).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onRegisterClick();
			}
		});
		findViewById(R.id.btn_login_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
		mUserNameET.setText(CacheUtil.get(this).getAsString(GlobalConstant.LAST_USERNAME));
	}

	private void onFindPasswordClick() {
		showToast("Retrieve the password page.");
	}

	private void onRegisterClick() {
		startActivity(RegisterActivity.class);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_login;
	}

	@Override
	protected void loadData() {
		super.loadData();

		// If the user has already login, return to home page
		if (UserCacheManager.getInstance().getUserInfo() != null) {
			startActivity(HomeActivity.class);
			finish();
		}
	}

	/*
	 * Click login button to login
	 */
	private void login() {
		String userName = mUserNameET.getText().toString().trim();
		String password = mPasswordET.getText().toString().trim();
		if (userName.length() <= 0 || password.length() <= 0) {
			showToast("Username and password must be filled");
			return;
		}

		DialogManager.startProgressDialog(this);
		mAccountService.login(userName, password, new AbstractRequestCallback<UserInfo>() {
			@Override
			public void onSuccess(UserInfo result) {
				UserCacheManager.getInstance().saveUserInfo(result);
				CacheUtil.get(LoginActivity.this).put(GlobalConstant.LAST_USERNAME, result.getNumber());

				startActivity(HomeActivity.class);
				finish();
			}
		});
	}

}
