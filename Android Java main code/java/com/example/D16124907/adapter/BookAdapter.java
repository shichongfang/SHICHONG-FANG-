package com.example.D16124907.adapter;

import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.entity.BookInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Book list Adapter
  */
public abstract class BookAdapter extends BaseAdapter {
	private Context mContext;
	private List<BookInfo> mData;

	public BookAdapter(Context context, List<BookInfo> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		BookViewHolder holder = null;
		if (convertView == null) {
			holder = new BookViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_book, parent, false);
			holder.nameTV = (TextView) convertView.findViewById(R.id.tv_item_book_name);
			holder.publishingTV = (TextView) convertView.findViewById(R.id.tv_item_book_publishing);
			holder.ISBNTV = (TextView) convertView.findViewById(R.id.tv_item_book_isbn);
			holder.deleteIV = (ImageView) convertView.findViewById(R.id.iv_item_book_delete);
			convertView.setTag(holder);
		} else {
			holder = (BookViewHolder) convertView.getTag();
		}

		BookInfo bookInfo = mData.get(position);
		holder.nameTV.setText(bookInfo.getName());
		holder.publishingTV.setText("Publisher：" + bookInfo.getPublishing());
		holder.ISBNTV.setText("ISBN：" + bookInfo.getISBN());
		holder.deleteIV.setVisibility(UserCacheManager.getInstance().isAdmin() ? View.VISIBLE : View.GONE);
		holder.deleteIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteClick(position);
			}
		});

		return convertView;
	}

	protected abstract void onDeleteClick(int position);

	class BookViewHolder {
		public TextView nameTV;
		public TextView publishingTV;
		public TextView ISBNTV;
		public ImageView deleteIV;
	}

}
