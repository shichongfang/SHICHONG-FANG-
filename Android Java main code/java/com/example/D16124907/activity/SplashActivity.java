package com.example.D16124907.activity;

import java.text.MessageFormat;

import com.example.D16124907.R;
import com.example.D16124907.utils.SpecialUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	private TextView mCurVersionTV;

	@Override
	protected int getContentViewId() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		return R.layout.activity_splash;
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		mCurVersionTV = (TextView) findViewById(R.id.splash_version);
		mCurVersionTV.setText(MessageFormat.format("{0}{1}", "VersionNo: ", SpecialUtil.getAppVersionName(this)));
	}

	@Override
	protected void loadData() {
		super.loadData();
		loadLoginActivity();
	}

	private void loadLoginActivity() {
		// delay 2s to get login page
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				finish();
			}
		}, 2000);
	}
}
