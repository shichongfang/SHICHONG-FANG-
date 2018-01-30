package com.example.D16124907.activity;

import com.example.D16124907.R;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.service.RequestCallback;
import com.example.D16124907.utils.DialogManager;
import com.example.D16124907.utils.IntentUtil;
import com.example.D16124907.utils.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Base activity
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActivityManager.getInstance().create(this);
		initVariables();
		initViews(savedInstanceState);
		loadData();
	}

	protected abstract int getContentViewId();

	/*
	 * init Variables
	 */
	protected void initVariables() {
	}

	/*
	 * init Views
	 */
	protected void initViews(Bundle savedInstanceState) {
		setContentView(getContentViewId());
	}

	/*
	 * load Data
	 */
	protected void loadData() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManager.getInstance().destory(this);
	}

	/*
	 * Abstract request call back
	 */
	protected abstract class AbstractRequestCallback<T> implements RequestCallback<T> {
		@Override
		public abstract void onSuccess(T response);

		@Override
		public void onFail(String errorMessage, int errorCode) {
			DialogManager.stopProgressDialog();
			showToast(errorMessage);
		}
	}

	/*
	 * Logout
	 */
	protected void logout() {
		UserCacheManager.getInstance().removeUserInfo();
		ActivityManager.getInstance().finishAll();
		startActivity(LoginActivity.class);
	}

	protected void showToast(String msg) {
		ToastUtil.showText(this, msg);
	}

	protected <T> void startActivity(Class<T> targetObj) {
		IntentUtil.startActivity(this, targetObj);
	}

	protected <T> void startActivity(Class<T> targetObj, Bundle bundle) {
		IntentUtil.startActivity(this, targetObj, bundle);
	}

	protected <T> void startActivityForResult(int requstCode, Class<T> targetObj) {
		IntentUtil.startActivityForResult(this, requstCode, targetObj);
	}

	protected <T> void startActivityForResult(int requstCode, Class<T> targetObj, Bundle bundle) {
		IntentUtil.startActivityForResult(this, requstCode, targetObj, bundle);
	}

	/*
	 * Init the title
	 */
	protected void initTitle(final boolean back, String name, String leftText, String rightText) {
		ImageView backIV = (ImageView) findViewById(R.id.iv_title_back);
		TextView nameTV = (TextView) findViewById(R.id.tv_title_name);
		TextView leftTextTV = (TextView) findViewById(R.id.tv_title_lefttext);
		TextView rightTextTV = (TextView) findViewById(R.id.tv_title_righttext);

		backIV.setVisibility(back ? View.VISIBLE : View.GONE);
		backIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTitleBackClick(v);
			}
		});

		nameTV.setVisibility(name != null ? View.VISIBLE : View.GONE);
		nameTV.setText(name);

		leftTextTV.setVisibility(leftText != null ? View.VISIBLE : View.GONE);
		leftTextTV.setText(leftText);
		leftTextTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTitleLeftTextClick(v);
			}
		});

		rightTextTV.setVisibility(rightText != null ? View.VISIBLE : View.GONE);
		rightTextTV.setText(rightText);
		rightTextTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onTitleRightTextClick(v);
			}
		});
	}

	protected void onTitleBackClick(View v) {
		finish();
	}

	protected void onTitleLeftTextClick(View v) {
	}

	protected void onTitleRightTextClick(View v) {
	}
}
