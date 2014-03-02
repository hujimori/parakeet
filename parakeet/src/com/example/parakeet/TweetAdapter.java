package com.example.parakeet;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class TweetAdapter extends ArrayAdapter<twitter4j.Status> {

	private LayoutInflater mInflater;
	private ViewHolder viewHolder;
	private CharSequence charSequence;
	private int background;

	private Context mContext;
	private Pattern urlPattern = Pattern.compile(
			"(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+",
			Pattern.CASE_INSENSITIVE);
	private String url;

	public TweetAdapter(Context mContext) {
		super(mContext, android.R.layout.simple_list_item_1);

		this.mContext = mContext;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Status status = getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_tweet, null);

			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			viewHolder.screenName = (TextView) convertView
					.findViewById(R.id.screen_name);

			viewHolder.text = (TextView) convertView.findViewById(R.id.text);

			viewHolder.tweetTime = (TextView) convertView
					.findViewById(R.id.tweetTime);

			viewHolder.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			viewHolder.icon = (SmartImageView) convertView
					.findViewById(R.id.icon);

			viewHolder.favIcon = (SmartImageView) convertView
					.findViewById(R.id.fav_icon);

			viewHolder.via = (TextView) convertView.findViewById(R.id.via);

			viewHolder.thumn = (ImageView) convertView.findViewById(R.id.thum);
			convertView.setTag(viewHolder);

		}

		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (status.isFavorited()) {

			viewHolder.favIcon
					.setImageResource(R.drawable.btn_rating_star_on_disabled_holo_light);
		} else {

			viewHolder.favIcon
					.setImageResource(R.drawable.btn_rating_star_off_disabled_holo_light);

		}

		if (status.isRetweet()) {
			background = R.drawable.status_item_background_other;
		} else {
			background = R.drawable.status_item_background_me;

		}

		convertView.setBackground(mContext.getResources().getDrawable(
				background));

		viewHolder.name.setText(status.getUser().getName());

		viewHolder.screenName.setText("@" + status.getUser().getScreenName());

		viewHolder.text.setText(status.getText());

		viewHolder.tweetTime.setText(viewHolder.date.format(status
				.getCreatedAt()));

		viewHolder.icon.setImageUrl(status.getUser().getProfileImageURL());

		charSequence = Html.fromHtml(status.getSource());

		viewHolder.via.setText("via " + charSequence.toString());

		viewHolder.thumn.setVisibility(View.GONE);

		MediaEntity[] entities = status.getMediaEntities();
		if (entities != null) {
			for (MediaEntity media : entities) {
				url = media.getMediaURL();

				viewHolder.thumn.setTag(url);

				DownLoadTask task = new DownLoadTask(viewHolder.thumn);
				task.execute(url);
			}
		}

		return convertView;

	}

}
