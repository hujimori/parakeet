package com.example.parakeet;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Yoshimori
 *
 */
public class ImageAdapter extends ArrayAdapter<BindData> {

	private class ViewHolder {
		public TextView mTextView;
		public ImageView mImageView;
	}

	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	private Context mContext;

	public ImageAdapter(Context mContext, List<BindData> objects) {
		super(mContext, 0, objects);

		this.mContext = mContext;
		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		BindData data = getItem(position);

		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.custom_list_item, parent,
					false);

			mViewHolder = new ViewHolder();

			mViewHolder.mTextView = (TextView) convertView
					.findViewById(R.id.text);

			mViewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.iamgeview);

			convertView.setTag(mViewHolder);

		} else {

			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.mTextView.setText(data.getText());

		mViewHolder.mImageView.setImageResource(data.getResourceId());

		if (data.getId() != null
				&& TwitterUtils.loadID(mContext).equals(data.getId())) {

			data.setIsChecked(true);
			mViewHolder.mImageView
					.setImageResource(R.drawable.btn_radio_on_pressed_holo_dark);

		} else if (data.getId() != null
				&& !TwitterUtils.loadID(mContext).equals(data.getId())) {

			data.setIsChecked(false);

		}

		return convertView;

	}

}
