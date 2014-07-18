package com.example.parakeet;

import twitter4j.Status;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter {

	private class ViewHolder {
		TextView screenName;
		ImageView icon;
	}

	private LayoutInflater inflater;
	private Context context;

	public TestAdapter(Context cn) {
		this.context = cn;

		this.inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		Status status = (Status) getItem(position);

		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_tweet, null);
			holder = new ViewHolder();

			holder.screenName = (TextView) convertView
					.findViewById(R.id.screen_name);

			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.screenName.setText("/@" + status.getUser().getScreenName());

		DownLoadTask task = new DownLoadTask(holder.icon);
		task.setProfileIcon(status.getUser().getBiggerProfileImageURL());

		return convertView;
	}

}
