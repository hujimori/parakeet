package com.example.parakeet;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProfileAdapter extends ArrayAdapter<BindData> {

	private Context mContext;
	private LayoutInflater inflater;
	private ViewHolder holder;

	public ProfileAdapter(Context context, List<BindData> objects) {
		super(context, 0, objects);
		// TODO 自動生成されたコンストラクター・スタブ
		this.mContext = context;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		BindData bindData = (BindData) getItem(position);

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.profile_item, null);

			holder = new ViewHolder();

			holder.textView = (TextView) convertView.findViewById(R.id.item);

			holder.count = (TextView) convertView.findViewById(R.id.count);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(bindData.getText());
		holder.count.setText(bindData.getCount());

		return convertView;
	}

}
