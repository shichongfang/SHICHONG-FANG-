package com.example.D16124907.adapter;

import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.cache.UserCacheManager;
import com.example.D16124907.entity.ConsumeInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Consume Adapter
 */
public abstract class ConsumeAdapter extends BaseAdapter {
	private Context mContext;
	private List<ConsumeInfo> mData;

	public ConsumeAdapter(Context context, List<ConsumeInfo> data) {
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
		UserViewHolder holder = null;
		if (convertView == null) {
			holder = new UserViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_consume, parent, false);
			holder.nameTV = (TextView) convertView.findViewById(R.id.tv_item_consume_name);
			holder.userTV = (TextView) convertView.findViewById(R.id.tv_item_consume_user);
			holder.dateTV = (TextView) convertView.findViewById(R.id.tv_item_consume_date);
			holder.deleteIV = (ImageView) convertView.findViewById(R.id.iv_item_consume_delete);
			convertView.setTag(holder);
		} else {
			holder = (UserViewHolder) convertView.getTag();
		}

		ConsumeInfo consumeInfo = mData.get(position);
		holder.nameTV.setText(consumeInfo.getBookName());
		holder.userTV.setText("Purchaser：" + consumeInfo.getUserName());
		holder.dateTV.setText(consumeInfo.getCreateDate());
		holder.deleteIV.setVisibility(UserCacheManager.getInstance().isAdmin() ? View.GONE : View.GONE);
		holder.deleteIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteClick(position);
			}
		});

		return convertView;
	}

	protected abstract void onDeleteClick(int position);

	class UserViewHolder {
		public TextView nameTV;
		public TextView userTV;
		public TextView dateTV;
		public ImageView deleteIV;
	}

}
