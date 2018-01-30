package com.example.D16124907.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.adapter.UserAdapter;
import com.example.D16124907.entity.UserInfo;
import com.example.D16124907.service.AccountService;
import com.example.D16124907.utils.ConvertUtil;
import com.example.D16124907.utils.DialogManager;
import com.example.D16124907.utils.JudgeUtil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * User manager
 */
public class UserManagerActivity extends BaseActivity {
	private static final String[] SEARCH_CONDITION = { "All", "PhoneNo", "UserName", "Email" };

	private Spinner mSearchSPIN;
	private EditText mSearchET;
	private Button mSearchBTN;
	private ListView mUserLV;

	private List<UserInfo> mUserList;
	private UserAdapter mUserAdapter;
	private AccountService mAccountService;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_usermanager;
	}

	@Override
	protected void initVariables() {
		super.initVariables();
		mAccountService = new AccountService();
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		initTitle(true, "UserManage", null, "ADD");
		mSearchSPIN = (Spinner) findViewById(R.id.spin_usermanager_search);
		mSearchET = (EditText) findViewById(R.id.et_usermanager_search);
		mSearchBTN = (Button) findViewById(R.id.btn_usermanager_search);
		mUserLV = (ListView) findViewById(R.id.lv_usermanager);

		mSearchBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchClick();
			}
		});

		initSearchSPINView();
		initUserListView();
	}

	/**
	 * Init search SPIN view
	 */
	private void initSearchSPINView() {
		mSearchSPIN.setPrompt("Please select to search ：");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				SEARCH_CONDITION);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSearchSPIN.setAdapter(adapter);
	}

	/**
	 * Init user list view
	 */
	private void initUserListView() {
		mUserList = new ArrayList<UserInfo>();
		mUserAdapter = new UserAdapter(this, mUserList) {
			@Override
			protected void onDeleteClick(int position) {
				showDeleteDialog(position);
			}
		};
		mUserLV.setAdapter(mUserAdapter);
		mUserLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				showOperUserDialog(position);
				return true;
			}
		});
	}

	@Override
	protected void loadData() {
		super.loadData();
		DialogManager.startProgressDialog(this);
		mAccountService.getAllUser(new AbstractRequestCallback<List<UserInfo>>() {
			@Override
			public void onSuccess(List<UserInfo> response) {
				DialogManager.stopProgressDialog();
				mUserList.clear();
				mUserList.addAll(response);
				mUserAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onTitleRightTextClick(View v) {
		super.onTitleRightTextClick(v);
		showAddUserDialog();
	}

	/**
	 * Show the delete dialog
	 */
	private void showDeleteDialog(final int position) {
		DialogManager.showCommonWarningDialog(UserManagerActivity.this,
				"Confirm delete" + mUserList.get(position).getUserName() + "？", new DialogManager.OnConfirmListenter() {
					@Override
					public boolean onConfirm() {
						DialogManager.startProgressDialog(UserManagerActivity.this);
						mAccountService.deleteUser(mUserList.get(position).getNumber(),
								new AbstractRequestCallback<Boolean>() {
							@Override
							public void onSuccess(Boolean response) {
								DialogManager.stopProgressDialog();
								loadData();
							}
						});
						return true;
					}
				}, null);
	}

	/**
	 * Click the search button and get the user information
	 */
	private void onSearchClick() {
		int curPos = mSearchSPIN.getSelectedItemPosition();
		String condition = mSearchET.getText().toString();
		if (curPos != 0 && JudgeUtil.isStringEmpty(condition)) {
			showToast("Please fill in search condition！");
			return;
		}

		switch (curPos) {
		case 0: // All
			loadData();
			break;
		case 1: // search use number
			DialogManager.startProgressDialog(this);
			mAccountService.findUserByNumber(condition, new AbstractRequestCallback<UserInfo>() {
				@Override
				public void onSuccess(UserInfo response) {
					DialogManager.stopProgressDialog();
					mUserList.clear();
					mUserList.add(response);
					mUserAdapter.notifyDataSetChanged();
				}
			});
			break;
		case 2: // search use username
			DialogManager.startProgressDialog(this);
			mAccountService.findUserByUserName(condition, new AbstractRequestCallback<List<UserInfo>>() {
				@Override
				public void onSuccess(List<UserInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("Not find the user info！");
					}
					mUserList.clear();
					mUserList.addAll(response);
					mUserAdapter.notifyDataSetChanged();
				}
			});
			break;
		case 3: // search use email
			DialogManager.startProgressDialog(this);
			mAccountService.findUserByEmail(condition, new AbstractRequestCallback<List<UserInfo>>() {
				@Override
				public void onSuccess(List<UserInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("Not find the user info！");
					}
					mUserList.clear();
					mUserList.addAll(response);
					mUserAdapter.notifyDataSetChanged();
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * Show the user operating dialog
	 */
	private void showOperUserDialog(final int position) {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_operuser, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		view.findViewById(R.id.tv_operuser_detail).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showUserDetailDialog(mUserList.get(position));
				dialog.dismiss();
			}
		});
		view.findViewById(R.id.tv_operuser_edit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEditUserDialog(mUserList.get(position));
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * Show the user detail dialog
	 */
	private void showUserDetailDialog(UserInfo userInfo) {
		new AlertDialog.Builder(this).setTitle("User Datails ").setIcon(R.drawable.ic_launcher)
				.setItems(new String[] { "PhoneNO" + "\t\t" + userInfo.getNumber(), "UserName" + "\t\t" + userInfo.getUserName(),
						"Email" + "\t\t" + userInfo.getEmail(), "Age" + "\t\t" + userInfo.getAge(),
						"Language" + "\t\t" + userInfo.getLanguage(), }, null)
				.show();
	}

	/**
	 * Show the user edit dialog
	 */
	private void showEditUserDialog(final UserInfo userInfo) {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_edituser, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Edit UserName").setIcon(R.drawable.ic_launcher);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		TextView numberTV = (TextView) view.findViewById(R.id.tv_edituser_number);
		final EditText userNameET = (EditText) view.findViewById(R.id.et_edituser_username);
		final EditText emailET = (EditText) view.findViewById(R.id.et_edituser_email);
		final EditText ageET = (EditText) view.findViewById(R.id.et_edituser_age);
		final EditText languageET = (EditText) view.findViewById(R.id.et_edituser_language);

		numberTV.setText(userInfo.getNumber());
		userNameET.setText(userInfo.getUserName());
		emailET.setText(userInfo.getEmail());
		ageET.setText(ConvertUtil.convertToString(userInfo.getAge(), ""));
		languageET.setText(userInfo.getLanguage());

		view.findViewById(R.id.btn_edituser_confirm).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = userNameET.getText().toString();
				String email = emailET.getText().toString();
				String age = ageET.getText().toString();
				String language = languageET.getText().toString();

				if (JudgeUtil.isStringEmpty(userName)) {
					showToast("Please Input UserName！");
					return;
				}

				if (JudgeUtil.isStringEmpty(email)) {
					showToast("Please Input Email！");
					return;
				}

				userInfo.setUserName(userName);
				userInfo.setEmail(email);
				userInfo.setAge(ConvertUtil.convertToInt(age, 0));
				userInfo.setLanguage(language);

				DialogManager.startProgressDialog(UserManagerActivity.this);
				mAccountService.updateUserByNumber(userInfo, new AbstractRequestCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean response) {
						DialogManager.stopProgressDialog();
						loadData();
						dialog.dismiss();
					}
				});
			}
		});
		dialog.show();
	}

	/**
	 * Show the new user added dialog
	 */
	private void showAddUserDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_adduser, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("New user added").setIcon(R.drawable.ic_launcher);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		final EditText numberET = (EditText) view.findViewById(R.id.et_adduser_number);
		final EditText passwordET = (EditText) view.findViewById(R.id.et_adduser_password);
		final EditText userNameET = (EditText) view.findViewById(R.id.et_adduser_username);
		final EditText emailET = (EditText) view.findViewById(R.id.et_adduser_email);
		final EditText ageET = (EditText) view.findViewById(R.id.et_adduser_age);
		final EditText languageET = (EditText) view.findViewById(R.id.et_adduser_language);

		view.findViewById(R.id.btn_adduser_confirm).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String number = numberET.getText().toString();
				String password = passwordET.getText().toString();
				String userName = userNameET.getText().toString();
				String email = emailET.getText().toString();
				String age = ageET.getText().toString();
				String language = languageET.getText().toString();

				if (JudgeUtil.isStringEmpty(number)) {
					showToast("Please Input PhoneNo！");
					return;
				}

				if (JudgeUtil.isStringEmpty(password)) {
					showToast("Please Input Password！");
					return;
				}

				if (JudgeUtil.isStringEmpty(userName)) {
					showToast("Please Input Username！");
					return;
				}

				if (JudgeUtil.isStringEmpty(email)) {
					showToast("Please Input Email！");
					return;
				}

				DialogManager.startProgressDialog(UserManagerActivity.this);
				mAccountService.register(number, password, userName, email, age, language,
						new AbstractRequestCallback<UserInfo>() {
					@Override
					public void onSuccess(UserInfo result) {
						DialogManager.stopProgressDialog();
						loadData();
						dialog.dismiss();
					}

					@Override
					public void onFail(String errorMessage, int errorCode) {
						DialogManager.stopProgressDialog();
						showToast("New user failed！");
					}
				});
			}
		});
		dialog.show();
	}

}
