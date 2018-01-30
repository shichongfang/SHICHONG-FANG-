package com.example.D16124907.utils;

import com.example.D16124907.R;
import com.example.D16124907.view.LoadingDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Dialog manager
 */
public class DialogManager {
	private static LoadingDialog mLoadingDialog = null;

	/**
	 * start loading
	 */
	public static void startProgressDialog(Context context) {
		if (mLoadingDialog == null || mLoadingDialog.getContext() != context) {
			mLoadingDialog = LoadingDialog.createDialog(context);
		}
		mLoadingDialog.show();
	}

	/**
	 * stop loading
	 */
	public static void stopProgressDialog() {
		if (mLoadingDialog != null) {
			try {
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public static Dialog showCommonWarningDialog(Activity activity, String message,
			final OnConfirmListenter onConfirmListenter, final OnCancelListener onCancelListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		View view = View.inflate(activity, R.layout.dialog_warning_common, null);
		TextView messageTV = (TextView) view.findViewById(R.id.tv_warningcommon_message);
		TextView cancelTV = (TextView) view.findViewById(R.id.tv_warningcommon_cancel);
		TextView confirmTV = (TextView) view.findViewById(R.id.tv_warningcommon_confirm);
		messageTV.setText(message);
		final AlertDialog dialog = builder.setView(view).setCancelable(false).create();
		dialog.show();
		// 一定要在show之后设置宽高，否则无效.(在show方法中，会设置宽高)
		Window window = dialog.getWindow();
		if (window != null) {
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = SpecialUtil.getWindowsWidth(activity) * 3 / 4;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			dialog.getWindow().setAttributes(params);
		}
		cancelTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onCancelListener != null && !onCancelListener.onCancel()) {
					return;
				}
				dialog.dismiss();
			}
		});
		confirmTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onConfirmListenter != null && !onConfirmListenter.onConfirm()) {
					return;
				}
				dialog.dismiss();
			}
		});

		return dialog;
	}


	public static Dialog showCommonSelectDialog(Context context, String title, View contentView,
			final OnConfirmListenter onConfirmListenter, final OnCancelListener onCancelListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.dialog_select_common, null);
		TextView titleTV = (TextView) view.findViewById(R.id.tv_selectcommon_title);
		View lineView = view.findViewById(R.id.line_title_below);
		TextView cancelTV = (TextView) view.findViewById(R.id.tv_selectcommon_cancel);
		TextView confirmTV = (TextView) view.findViewById(R.id.tv_selectcommon_confirm);
		FrameLayout contentFL = (FrameLayout) view.findViewById(R.id.fl_selectcommon_content);

		final AlertDialog dialog = builder.setView(view).setCancelable(false).create();
		contentFL.addView(contentView);
		if (JudgeUtil.isStringEmpty(title)) {
			titleTV.setVisibility(View.GONE);
			lineView.setVisibility(View.GONE);
		} else {
			titleTV.setVisibility(View.VISIBLE);
			lineView.setVisibility(View.VISIBLE);
			titleTV.setText(title);
		}

		cancelTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onCancelListener != null && !onCancelListener.onCancel()) {
					return;
				}
				dialog.dismiss();
			}
		});
		confirmTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onConfirmListenter != null && !onConfirmListenter.onConfirm()) {
					return;
				}
				dialog.dismiss();
			}
		});

		dialog.show();
		return dialog;
	}

	public interface OnConfirmListenter {
		boolean onConfirm();
	}

	public interface OnCancelListener {
		boolean onCancel();
	}

}
