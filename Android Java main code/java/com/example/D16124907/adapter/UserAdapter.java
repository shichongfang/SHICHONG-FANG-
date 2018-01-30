package com.example.D16124907.adapter;

import java.util.List;

import com.example.D16124907.R;
import com.example.D16124907.entity.UserInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * User Adapter
 */
public abstract class UserAdapter extends BaseAdapter {
	private Context mContext;
	private List<UserInfo> mData;

	public UserAdapter(Context context, List<UserInfo> data) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
			holder.nameTV = (TextView) convertView.findViewById(R.id.tv_item_user_name);
			holder.emailTV = (TextView) convertView.findViewById(R.id.tv_item_user_email);
			holder.numberTV = (TextView) convertView.findViewById(R.id.tv_item_user_number);
			holder.deleteIV = (ImageView) convertView.findViewById(R.id.iv_item_user_delete);
			convertView.setTag(holder);
		} else {
			holder = (UserViewHolder) convertView.getTag();
		}

		UserInfo userInfo = mData.get(position);
		holder.nameTV.setText(userInfo.getUserName());
		holder.emailTV.setText("Email：" + userInfo.getEmail());
		holder.numberTV.setText("PhoneNO：" + userInfo.getNumber());
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
		public TextView emailTV;
		public TextView numberTV;
		public ImageView deleteIV;
	}

}
