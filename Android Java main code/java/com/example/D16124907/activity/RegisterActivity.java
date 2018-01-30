package com.example.D16124907.activity;

import com.example.D16124907.R;
import com.example.D16124907.cache.CacheUtil;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.common.GlobalConstant;
import com.example.D16124907.entity.UserInfo;
import com.example.D16124907.service.AccountService;
import com.example.D16124907.utils.JudgeUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Register page
 */
public class RegisterActivity extends BaseActivity {
	private EditText mNumberTE;
	private EditText mPasswordTE;
	private EditText mConfirmPasswordTE;
	private EditText mUserNameTE;
	private EditText mEmailTE;
	private EditText mAgeTE;
	private EditText mLanguageTE;

	private AccountService mAccountService;

	@Override
	protected void initVariables() {
		super.initVariables();
		mAccountService = new AccountService();
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		initTitle(true, "Register", null, null);
		mNumberTE = (EditText) findViewById(R.id.et_register_number);
		mPasswordTE = (EditText) findViewById(R.id.et_register_password);
		mConfirmPasswordTE = (EditText) findViewById(R.id.et_register_confirmpassword);
		mUserNameTE = (EditText) findViewById(R.id.et_register_username);
		mEmailTE = (EditText) findViewById(R.id.et_register_email);
		mAgeTE = (EditText) findViewById(R.id.et_register_age);
		mLanguageTE = (EditText) findViewById(R.id.et_register_language);

		findViewById(R.id.btn_register_register).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRegisterClick();
			}
		});
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_register;
	}

	protected void onRegisterClick() {
		String numberStr = mNumberTE.getText().toString();
		String passwordStr = mPasswordTE.getText().toString();
		String confirmPasswordStr = mConfirmPasswordTE.getText().toString();
		String userNameStr = mUserNameTE.getText().toString();
		String emailStr = mEmailTE.getText().toString();
		String ageStr = mAgeTE.getText().toString();
		String languageStr = mLanguageTE.getText().toString();

		if (JudgeUtil.isStringEmpty(numberStr)) {
			showToast("Please Input PhoneNo！");
			return;
		}

		if (JudgeUtil.isStringEmpty(passwordStr)) {
			showToast("Please Input Password！");
			return;
		}

		if (!JudgeUtil.isStringEquals(passwordStr, confirmPasswordStr)) {
			showToast("Two input passwords are not consistent！");
			return;
		}

		if (JudgeUtil.isStringEmpty(userNameStr)) {
			showToast("Please Input UserName！");
			return;
		}

		if (JudgeUtil.isStringEmpty(emailStr)) {
			showToast("Please Input Email！");
			return;
		}

		mAccountService.register(numberStr, passwordStr, userNameStr, emailStr, ageStr, languageStr,
				new AbstractRequestCallback<UserInfo>() {
					@Override
					public void onSuccess(UserInfo result) {
						UserCacheManager.getInstance().saveUserInfo(result);
						CacheUtil.get(RegisterActivity.this).put(GlobalConstant.LAST_USERNAME, result.getNumber());
						showToast("Register Success！");
						startActivity(HomeActivity.class);
						finish();
					}
				});
	}

}
