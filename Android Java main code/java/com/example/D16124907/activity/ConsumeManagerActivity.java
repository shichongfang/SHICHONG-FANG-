package com.example.D16124907.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.adapter.ConsumeAdapter;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.entity.ConsumeInfo;
import com.example.D16124907.service.ConsumeService;
import com.example.D16124907.utils.DialogManager;
import com.example.D16124907.utils.JudgeUtil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Consume Manager record manager
  */
public class ConsumeManagerActivity extends BaseActivity {
	// Administrator can search use the ISBN,phone number
	private static final String[] ADMIN_SEARCH_CONDITION = { "ALL", "ISBN", "PhoneNO" };
	// User can only use the ISBN to search the book
	private static final String[] SEARCH_CONDITION = { "All", "ISBN" };

	private Spinner mSearchSPIN;
	private EditText mSearchET;
	private Button mSearchBTN;
	private ListView mConsumeLV;

	private List<ConsumeInfo> mConsumeList;
	private ConsumeAdapter mConsumeAdapter;
	private ConsumeService mConsumeService;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_consumemanager;
	}

	@Override
	protected void initVariables() {
		super.initVariables();
		mConsumeService = new ConsumeService();
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		initTitle(true, "Records of consumption", null, null);
		mSearchSPIN = (Spinner) findViewById(R.id.spin_consumemanager_search);
		mSearchET = (EditText) findViewById(R.id.et_consumemanager_search);
		mSearchBTN = (Button) findViewById(R.id.btn_consumemanager_search);
		mConsumeLV = (ListView) findViewById(R.id.lv_consumemanager);

		mSearchBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchClick();
			}
		});

		initSearchSPINView();
		initConsumeListView();
	}

	/**
	 * Init the search spin view
	 */
	private void initSearchSPINView() {
		mSearchSPIN.setPrompt("Please select the query condition：");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				UserCacheManager.getInstance().isAdmin() ? ADMIN_SEARCH_CONDITION : SEARCH_CONDITION);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSearchSPIN.setAdapter(adapter);
	}

	/**
	 * Init the consume list of view
	 */
	private void initConsumeListView() {
		mConsumeList = new ArrayList<ConsumeInfo>();
		mConsumeAdapter = new ConsumeAdapter(this, mConsumeList) {
			@Override
			protected void onDeleteClick(int position) {
				showDeleteDialog(position);
			}
		};
		mConsumeLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showConsumeDetailDialog(mConsumeList.get(position));
			}
		});
		mConsumeLV.setAdapter(mConsumeAdapter);
	}

	@Override
	protected void loadData() {
		super.loadData();
		DialogManager.startProgressDialog(this);
		// Get all of the users' consume record
		if (UserCacheManager.getInstance().isAdmin()) {
			mConsumeService.getAllConsume(new AbstractRequestCallback<List<ConsumeInfo>>() {
				@Override
				public void onSuccess(List<ConsumeInfo> response) {
					DialogManager.stopProgressDialog();
					mConsumeList.clear();
					mConsumeList.addAll(response);
					mConsumeAdapter.notifyDataSetChanged();
				}
			});
			return;
		}

		// Consumer can only get their own record
		mConsumeService.findConsumeByUserNumber(UserCacheManager.getInstance().getUserInfo().getNumber(),
				new AbstractRequestCallback<List<ConsumeInfo>>() {
					@Override
					public void onSuccess(List<ConsumeInfo> response) {
						DialogManager.stopProgressDialog();
						mConsumeList.clear();
						mConsumeList.addAll(response);
						mConsumeAdapter.notifyDataSetChanged();
					}
				});
	}

	/**
	 * Show the dialog of the delete
	 */
	private void showDeleteDialog(final int position) {
		DialogManager.showCommonWarningDialog(ConsumeManagerActivity.this, "Confirm Deletion？",
				new DialogManager.OnConfirmListenter() {
					@Override
					public boolean onConfirm() {
						DialogManager.startProgressDialog(ConsumeManagerActivity.this);
						mConsumeService.deleteConsume(mConsumeList.get(position).getId(),
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
	 * Click the search button and get the record
	 */
	private void onSearchClick() {
		int curPos = mSearchSPIN.getSelectedItemPosition();
		String condition = mSearchET.getText().toString();
		if (curPos != 0 && JudgeUtil.isStringEmpty(condition)) {
			showToast("Please enter the query condition！");
			return;
		}

		switch (curPos) {
		case 0: // All
			loadData();
			break;
		case 1: // Search use the ISBN
			DialogManager.startProgressDialog(this);
			if (UserCacheManager.getInstance().isAdmin()) {
				mConsumeService.findConsumeByBookISBN(condition, new AbstractRequestCallback<List<ConsumeInfo>>() {
					@Override
					public void onSuccess(List<ConsumeInfo> response) {
						DialogManager.stopProgressDialog();
						if (response.size() == 0) {
							showToast("No Found Consumer Information！");
						}
						mConsumeList.clear();
						mConsumeList.addAll(response);
						mConsumeAdapter.notifyDataSetChanged();
					}
				});
				break;
			}
			mConsumeService.findConsumeByUserNumberAndBookISBN(UserCacheManager.getInstance().getUserInfo().getNumber(),
					condition, new AbstractRequestCallback<List<ConsumeInfo>>() {
						@Override
						public void onSuccess(List<ConsumeInfo> response) {
							DialogManager.stopProgressDialog();
							if (response.size() == 0) {
								showToast("No Found Consumer Information！");
							}
							mConsumeList.clear();
							mConsumeList.addAll(response);
							mConsumeAdapter.notifyDataSetChanged();
						}
					});
			break;
		case 2: // Search use the number
			DialogManager.startProgressDialog(this);
			mConsumeService.findConsumeByUserNumber(condition, new AbstractRequestCallback<List<ConsumeInfo>>() {
				@Override
				public void onSuccess(List<ConsumeInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("No Found Consumer Information！");
					}
					mConsumeList.clear();
					mConsumeList.addAll(response);
					mConsumeAdapter.notifyDataSetChanged();
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * Show the dialog of teh consumer detail
	 */
	private void showConsumeDetailDialog(final ConsumeInfo consumeInfo) {
		new AlertDialog.Builder(this).setTitle("Consumer record details").setIcon(R.drawable.ic_launcher)
				.setItems(new String[] { "BookName    " + "\t\t" + consumeInfo.getBookName(),
						"ISBN    " + "\t\t" + consumeInfo.getBookISBN(), "Purchaser" + "\t\t" + consumeInfo.getUserName(),
						"PhoneNo    " + "\t\t" + consumeInfo.getUserNumber(),
						"Date    " + "\t\t" + consumeInfo.getCreateDate(), }, null)
				.show();
	}

}
