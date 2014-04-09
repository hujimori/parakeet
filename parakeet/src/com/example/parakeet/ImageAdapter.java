package com.example.parakeet;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class BindData {

	String text;
	int imageResourceId;

	public BindData(String text, int id) {

		this.text = text;
		this.imageResourceId = id;

	}
}

public class ImageAdapter extends ArrayAdapter<BindData> {

	private LayoutInflater inflater;
	private ViewHolder mViewHolder;

	public ImageAdapter(Context mContext, List<BindData> objects) {
		super(mContext, 0, objects);

		this.inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		BindData data = getItem(position);

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.custom_list_item, parent,
					false);

			mViewHolder = new ViewHolder();

			mViewHolder.textView = (TextView) convertView
					.findViewById(R.id.text);

			mViewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.iamgeview);

			convertView.setTag(mViewHolder);

		} else {

			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.textView.setText(data.text);

		mViewHolder.imageView.setImageResource(data.imageResourceId);

		return convertView;

	}

}
