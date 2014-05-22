package com.example.parakeet;

import java.text.SimpleDateFormat;
import twitter4j.MediaEntity;
import twitter4j.Status;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusAdapter extends ArrayAdapter<twitter4j.Status> {

	private class ViewHolder {
		TextView screenName;
		TextView text;
		TextView via;
		TextView tweetTime;
		java.text.DateFormat date;
		ImageView icon;
		ImageView thumn;
		LinearLayout layout;
	}

	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	private CharSequence charSequence;
	private Context mContext;
	private String url;
	

	public StatusAdapter(Context mContext) {
		super(mContext, android.R.layout.simple_list_item_1);

		this.mContext = mContext;

		mInflater = (LayoutInflater) this.mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Status status = getItem(position);

		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.list_item_tweet, null);

			mViewHolder = new ViewHolder();

			mViewHolder.screenName = (TextView) convertView
					.findViewById(R.id.screen_name);

			mViewHolder.text = (TextView) convertView.findViewById(R.id.text);

			mViewHolder.tweetTime = (TextView) convertView
					.findViewById(R.id.tweetTime);

			mViewHolder.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			mViewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);

			mViewHolder.via = (TextView) convertView.findViewById(R.id.via);

			mViewHolder.layout = (LinearLayout) convertView.findViewById(R.id.image);
			//mViewHolder.thumn = (ImageView) convertView.findViewById(R.id.thum);

			convertView.setTag(mViewHolder);

		}

		else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		

		mViewHolder.screenName.setText("@" + status.getUser().getScreenName()
				+ "/" + status.getUser().getName());

		mViewHolder.text.setText(status.getText());

		mViewHolder.tweetTime.setText(mViewHolder.date.format(status
				.getCreatedAt()));

		mViewHolder.icon.setTag(status.getUser().getBiggerProfileImageURL());
		DownLoadTask task1 = new DownLoadTask(mViewHolder.icon);
		task1.setProfileIcon(status.getUser().getBiggerProfileImageURL());

		charSequence = Html.fromHtml(status.getSource());

		mViewHolder.via.setText("via " + charSequence.toString());

		//mViewHolder.thumn.setVisibility(View.GONE);


		MediaEntity[] entities = status.getMediaEntities();
		if (entities != null) {
			for (MediaEntity media : entities) {
				url = media.getMediaURL();

				//mViewHolder.thumn.setVisibility(View.VISIBLE);

				//mViewHolder.thumn.setTag(url);

				DownLoadTask task2 = new DownLoadTask(mViewHolder.thumn);
				task2.setThumn(url,mViewHolder.layout);
			}
		}

		return convertView;

	}

	
	

}
