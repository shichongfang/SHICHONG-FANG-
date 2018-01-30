package com.example.D16124907.view;

import com.example.D16124907.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;


public class LoadingDialog extends Dialog {

	private static LoadingDialog mLoadingDialog = null;

	public LoadingDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		setCanceledOnTouchOutside(false);
	}

	public static LoadingDialog createDialog(Context context, String message) {
		mLoadingDialog = new LoadingDialog(context, R.style.CustomLoadingDialog);
		mLoadingDialog.setContentView(R.layout.loadingdialog);
		mLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		mLoadingDialog.setMessage(message);
		return mLoadingDialog;
	}

	public static LoadingDialog createDialog(Context context) {
		mLoadingDialog = new LoadingDialog(context, R.style.CustomLoadingDialog);
		mLoadingDialog.setContentView(R.layout.loadingdialog);
		mLoadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return mLoadingDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (mLoadingDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) mLoadingDialog.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	public LoadingDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) mLoadingDialog.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return mLoadingDialog;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
