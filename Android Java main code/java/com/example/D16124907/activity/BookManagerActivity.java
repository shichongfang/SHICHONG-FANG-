package com.example.D16124907.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.adapter.BookAdapter;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.entity.BookInfo;
import com.example.D16124907.service.BookService;
import com.example.D16124907.service.ConsumeService;
import com.example.D16124907.utils.ConvertUtil;
import com.example.D16124907.utils.DialogManager;
import com.example.D16124907.utils.JudgeUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
 * Book Manager
  */
public class BookManagerActivity extends BaseActivity {
	private static final String[] SEARCH_CONDITION = { "ALL", "ISBN", "BOOKNAME", "AUTHOR", "PUBLISHER" };

	private Spinner mSearchSPIN;
	private EditText mSearchET;
	private Button mSearchBTN;
	private TextView mOperTV;
	private ListView mBookLV;

	private List<BookInfo> mBookList;
	private BookAdapter mBookAdapter;
	private BookService mBookService;
	private ConsumeService mConsumeService;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_bookmanager;
	}

	@Override
	protected void initVariables() {
		super.initVariables();
		mBookService = new BookService();
		mConsumeService = new ConsumeService();
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		super.initViews(savedInstanceState);
		initTitle(true, "BooKManage", null, UserCacheManager.getInstance().isAdmin() ? "ADD" : null);
		mSearchSPIN = (Spinner) findViewById(R.id.spin_bookmanager_search);
		mSearchET = (EditText) findViewById(R.id.et_bookmanager_search);
		mSearchBTN = (Button) findViewById(R.id.btn_bookmanager_search);
		mOperTV = (TextView) findViewById(R.id.tv_bookingmanager_oper_prompt);
		mBookLV = (ListView) findViewById(R.id.lv_bookmanager);

		mOperTV.setVisibility(UserCacheManager.getInstance().isAdmin() ? View.VISIBLE : View.GONE);
		mSearchBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSearchClick();
			}
		});

		initSearchSPINView();
		initBookListView();
	}

	/**
	 * Init the search SPIN VIEW
	 */
	private void initSearchSPINView() {
		mSearchSPIN.setPrompt("Please select condition：");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				SEARCH_CONDITION);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSearchSPIN.setAdapter(adapter);
	}

	/**
	 * Init the book list view
	 */
	private void initBookListView() {
		mBookList = new ArrayList<BookInfo>();
		mBookAdapter = new BookAdapter(this, mBookList) {
			@Override
			protected void onDeleteClick(int position) {
				showDeleteDialog(position);
			}
		};
		mBookLV.setAdapter(mBookAdapter);
		mBookLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!UserCacheManager.getInstance().isAdmin()) {
					showBookDetailDialog(mBookList.get(position));
				}
			}
		});
		mBookLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (UserCacheManager.getInstance().isAdmin()) {
					showOperBookDialog(position);
				}
				return true;
			}
		});
	}

	@Override
	protected void loadData() {
		super.loadData();
		DialogManager.startProgressDialog(this);
		mBookService.getAllBook(new AbstractRequestCallback<List<BookInfo>>() {
			@Override
			public void onSuccess(List<BookInfo> response) {
				DialogManager.stopProgressDialog();
				mBookList.clear();
				mBookList.addAll(response);
				mBookAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	protected void onTitleRightTextClick(View v) {
		super.onTitleRightTextClick(v);
		showAddBookDialog();
	}

	/**
	 * Show the delete book dialog
	 */
	private void showDeleteDialog(final int position) {
		DialogManager.showCommonWarningDialog(BookManagerActivity.this,
				"Confirm delete?" + mBookList.get(position).getName() + "？", new DialogManager.OnConfirmListenter() {
					@Override
					public boolean onConfirm() {
						DialogManager.startProgressDialog(BookManagerActivity.this);
						mBookService.deleteBook(mBookList.get(position).getISBN(),
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
	 * Set on search click and get the book info
	 */
	private void onSearchClick() {
		int curPos = mSearchSPIN.getSelectedItemPosition();
		String condition = mSearchET.getText().toString();
		if (curPos != 0 && JudgeUtil.isStringEmpty(condition)) {
			showToast("Please enter the query condition value！");
			return;
		}

		switch (curPos) {
		case 0: // All
			loadData();
			break;
		case 1: // Search use ISBN to find the book
			DialogManager.startProgressDialog(this);
			mBookService.findBookByISBN(condition, new AbstractRequestCallback<BookInfo>() {
				@Override
				public void onSuccess(BookInfo response) {
					DialogManager.stopProgressDialog();
					mBookList.clear();
					mBookList.add(response);
					mBookAdapter.notifyDataSetChanged();
				}
			});
			break;
		case 2: // Search use book name to find the book
			DialogManager.startProgressDialog(this);
			mBookService.findBookByName(condition, new AbstractRequestCallback<List<BookInfo>>() {
				@Override
				public void onSuccess(List<BookInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("No Found Book Information！");
					}
					mBookList.clear();
					mBookList.addAll(response);
					mBookAdapter.notifyDataSetChanged();
				}
			});
			break;
		case 3: // Search use author name to find the book
			DialogManager.startProgressDialog(this);
			mBookService.findBookByAuthor(condition, new AbstractRequestCallback<List<BookInfo>>() {
				@Override
				public void onSuccess(List<BookInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("No Found Book Information！");
					}
					mBookList.clear();
					mBookList.addAll(response);
					mBookAdapter.notifyDataSetChanged();
				}
			});
			break;
		case 4: // Search use book publisher to find the book
			DialogManager.startProgressDialog(this);
			mBookService.findBookByPublishing(condition, new AbstractRequestCallback<List<BookInfo>>() {
				@Override
				public void onSuccess(List<BookInfo> response) {
					DialogManager.stopProgressDialog();
					if (response.size() == 0) {
						showToast("No Found Book Information！");
					}
					mBookList.clear();
					mBookList.addAll(response);
					mBookAdapter.notifyDataSetChanged();
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * Show the operating book dialog
	 */
	private void showOperBookDialog(final int position) {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_operbook, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		view.findViewById(R.id.tv_operbook_detail).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showBookDetailDialog(mBookList.get(position));
				dialog.dismiss();
			}
		});
		view.findViewById(R.id.tv_operbook_edit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEditBookDialog(mBookList.get(position));
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 *  Show the dialog of search book detail
	 */
	private void showBookDetailDialog(final BookInfo bookInfo) {
		Builder builder = new AlertDialog.Builder(BookManagerActivity.this).setTitle("Book Details")
				.setIcon(R.drawable.ic_launcher)
				.setItems(new String[] { "ISBN    " + "\t\t" + bookInfo.getISBN(),
						"BookName " + "\t\t" + bookInfo.getName(), "Author  " + "\t\t" + bookInfo.getAuthor(),
						"Publisher " + "\t\t" + bookInfo.getPublishing(), "Price    " + "\t\t" + bookInfo.getSalesPrice(),
						"Stock    " + "\t\t" + bookInfo.getStockCount(), }, null);

		// Administrator can not buy book use the admin account
		if (UserCacheManager.getInstance().isAdmin()) {
			builder.show();
			return;
		}

		if (bookInfo.getStockCount() == 0) {
			builder.show();
			return;
		}

		builder.setPositiveButton("Buy Immediately", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				buyBook(bookInfo);
			}
		});
		builder.show();
	}

	/**
	 * Buy book
	 */
	private void buyBook(final BookInfo bookInfo) {
		// Book stock -1
		bookInfo.setStockCount(bookInfo.getStockCount() - 1);
		DialogManager.startProgressDialog(this);
		mBookService.updateBookByISBN(bookInfo, new AbstractRequestCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean response) {
				DialogManager.stopProgressDialog();
				showToast("Purchase Success！");

				// Add a new consume record
				mConsumeService.addConsume(bookInfo.getISBN(), bookInfo.getName(),
						UserCacheManager.getInstance().getUserInfo().getNumber(),
						UserCacheManager.getInstance().getUserInfo().getUserName(),
						new AbstractRequestCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean response) {
					}

					@Override
					public void onFail(String errorMessage, int errorCode) {
					}
				});
			}

			@Override
			public void onFail(String errorMessage, int errorCode) {
				DialogManager.stopProgressDialog();
				showToast("Purchase Failure！");
			}
		});
	}

	/**
	 * Show dialog of the edit book
	 */
	private void showEditBookDialog(final BookInfo bookInfo) {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_editbook, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Edit the Book Information").setIcon(R.drawable.ic_launcher);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		TextView ISBNTV = (TextView) view.findViewById(R.id.tv_editbook_isbn);
		final EditText nameET = (EditText) view.findViewById(R.id.et_editbook_name);
		final EditText authorET = (EditText) view.findViewById(R.id.et_editbook_author);
		final EditText publishingET = (EditText) view.findViewById(R.id.et_editbook_publishing);
		final EditText priceET = (EditText) view.findViewById(R.id.et_editbook_price);
		final EditText stockcountET = (EditText) view.findViewById(R.id.et_editbook_stockcount);

		ISBNTV.setText(bookInfo.getISBN());
		nameET.setText(bookInfo.getName());
		authorET.setText(bookInfo.getAuthor());
		publishingET.setText(bookInfo.getPublishing());
		priceET.setText(ConvertUtil.convertToString(bookInfo.getSalesPrice(), ""));
		stockcountET.setText(ConvertUtil.convertToString(bookInfo.getStockCount(), "0"));

		view.findViewById(R.id.btn_editbook_confirm).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = nameET.getText().toString();
				String author = authorET.getText().toString();
				String publishing = publishingET.getText().toString();
				String price = priceET.getText().toString();
				String stockCount = stockcountET.getText().toString();

				if (JudgeUtil.isStringEmpty(name)) {
					showToast("Please Input BookName！");
					return;
				}

				if (JudgeUtil.isStringEmpty(author)) {
					showToast("Please Input Author！");
					return;
				}

				if (JudgeUtil.isStringEmpty(publishing)) {
					showToast("Please Input Publisher！");
					return;
				}

				if (JudgeUtil.isStringEmpty(price)) {
					showToast("Please Input Price！");
					return;
				}

				if (JudgeUtil.isStringEmpty(stockCount)) {
					showToast("Please Input Stock！");
					return;
				}

				bookInfo.setName(name);
				bookInfo.setAuthor(author);
				bookInfo.setPublishing(publishing);
				bookInfo.setSalesPrice(ConvertUtil.convertToDouble(price, 0));
				bookInfo.setStockCount(ConvertUtil.convertToInt(stockCount, 0));

				DialogManager.startProgressDialog(BookManagerActivity.this);
				mBookService.updateBookByISBN(bookInfo, new AbstractRequestCallback<Boolean>() {
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
	 * Show the dialog of new book added
	 */
	private void showAddBookDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_addbook, new FrameLayout(this), false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("ADD Book Information").setIcon(R.drawable.ic_launcher);
		final AlertDialog dialog = builder.setView(view).setCancelable(true).create();
		final EditText ISBNET = (EditText) view.findViewById(R.id.et_addbook_isbn);
		final EditText authorET = (EditText) view.findViewById(R.id.et_addbook_author);
		final EditText nameET = (EditText) view.findViewById(R.id.et_addbook_name);
		final EditText publishingET = (EditText) view.findViewById(R.id.et_addbook_publishing);
		final EditText priceET = (EditText) view.findViewById(R.id.et_addbook_price);
		final EditText stockCountET = (EditText) view.findViewById(R.id.et_addbook_stockcount);

		view.findViewById(R.id.btn_addbook_confirm).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String ISBN = ISBNET.getText().toString();
				String author = authorET.getText().toString();
				String name = nameET.getText().toString();
				String publishing = publishingET.getText().toString();
				String price = priceET.getText().toString();
				String stockCount = stockCountET.getText().toString();

				if (JudgeUtil.isStringEmpty(ISBN)) {
					showToast("Please Input ISBN！");
					return;
				}

				if (JudgeUtil.isStringEmpty(name)) {
					showToast("Please Input BookName！");
					return;
				}

				if (JudgeUtil.isStringEmpty(author)) {
					showToast("Please Input Author！");
					return;
				}

				if (JudgeUtil.isStringEmpty(publishing)) {
					showToast("Please Input Publisher！");
					return;
				}

				if (JudgeUtil.isStringEmpty(price)) {
					showToast("Please Input Price！");
					return;
				}

				if (JudgeUtil.isStringEmpty(stockCount)) {
					showToast("Please Input Stock！");
					return;
				}

				DialogManager.startProgressDialog(BookManagerActivity.this);
				mBookService.addBook(ISBN, author, name, publishing, price, stockCount,
						new AbstractRequestCallback<BookInfo>() {
					@Override
					public void onSuccess(BookInfo result) {
						DialogManager.stopProgressDialog();
						loadData();
						dialog.dismiss();
					}

					@Override
					public void onFail(String errorMessage, int errorCode) {

					}
				});
			}
		});
		dialog.show();
	}

}
