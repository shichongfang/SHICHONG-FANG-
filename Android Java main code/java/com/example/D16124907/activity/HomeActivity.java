package com.example.D16124907.activity;

import com.example.D16124907.R;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.utils.SpecialUtil;
import com.example.D16124907.utils.ToastUtil;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Home page
  */
public class HomeActivity extends BaseActivity {
	private TextView mSettingTV;
	private View mAdminView;
	private View mCommonView;

	private PopupWindow mPopupWindow;

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		mSettingTV = (TextView) findViewById(R.id.tv_home_setting);
		mAdminView = findViewById(R.id.home_admin);
		mCommonView = findViewById(R.id.home_common);

		mSettingTV.setText(UserCacheManager.getInstance().getUserInfo().getUserName());
		mSettingTV.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onSettingClick();
			}
		});

		if (UserCacheManager.getInstance().isAdmin()) {
			setModuleView(mAdminView);
		} else {
			setModuleView(mCommonView);
		}
	}

	private void setModuleView(View view) {
		view.setVisibility(View.VISIBLE);
		view.findViewById(R.id.iv_home_bookmanager).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBookManagerClick();
			}
		});
		view.findViewById(R.id.iv_home_usermanager).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onUserManagerClick();
			}
		});
		view.findViewById(R.id.iv_home_consumptionrecord).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onConsumptionRecordClick();
			}
		});
		view.findViewById(R.id.iv_home_about).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onAboutManagerClick();
			}
		});
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_home;
	}

	private void onSettingClick() {
		if (mPopupWindow == null) {
			initPopupwindow();
		}

		mPopupWindow.showAsDropDown(mSettingTV, SpecialUtil.getWindowsWidth(this), 0);
	}

	private void onBookManagerClick() {
		startActivity(BookManagerActivity.class);
	}

	private void onUserManagerClick() {
		startActivity(UserManagerActivity.class);
	}

	private void onConsumptionRecordClick() {
		startActivity(ConsumeManagerActivity.class);
	}

	private void onAboutManagerClick() {
		ToastUtil.showText(this, "current version：" + SpecialUtil.getAppVersionName(this));
	}

	private void initPopupwindow() {
		View contentView = View.inflate(this, R.layout.home_title_popupwindow, null);
		mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable());
		mPopupWindow.setOutsideTouchable(true);

		contentView.findViewById(R.id.title_logout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				logout();
			}
		});

		contentView.findViewById(R.id.title_exit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				ActivityManager.getInstance().finishAll();
			}
		});
	}
}
