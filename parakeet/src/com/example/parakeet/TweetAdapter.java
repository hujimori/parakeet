package com.example.parakeet;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;
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

public class TweetAdapter extends ArrayAdapter<twitter4j.Status> {

	private class ViewHolder {
		TextView screenName;
		TextView text;
		TextView via;
		TextView tweetTime;
		java.text.DateFormat date;
		ImageView icon;
		ImageView thumn;
	}

	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	private CharSequence charSequence;
	private int background;
	private Context mContext;
	private Pattern urlPattern = Pattern.compile(
			"(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+",
			Pattern.CASE_INSENSITIVE);
	private String url;
	private int mAnimatedPosition;
	private User user;
	private LinearLayout layout;
	private ImageView imageView;
	private View convertView;

	public TweetAdapter(Context mContext, int mAnimatedPosition) {
		super(mContext, android.R.layout.simple_list_item_1);

		this.mContext = mContext;

		this.mAnimatedPosition = mAnimatedPosition;
		mInflater = (LayoutInflater) mContext
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

			mViewHolder.thumn = (ImageView) convertView.findViewById(R.id.thum);

			convertView.setTag(mViewHolder);

		}

		else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		/*
		 * if (status.isFavorited()) {
		 * 
		 * mViewHolder.favIcon
		 * .setImageResource(R.drawable.btn_rating_star_on_disabled_holo_light);
		 * } else {
		 * 
		 * mViewHolder.favIcon
		 * .setImageResource(R.drawable.btn_rating_star_off_disabled_holo_light
		 * );
		 * 
		 * }
		 */
		/*
		 * if (status.isRetweet()) { background =
		 * R.drawable.status_item_background_other; } else { background =
		 * R.drawable.status_item_background_me;
		 * 
		 * }
		 */

		// convertView.setBackground(mContext.getResources().getDrawable(
		// background));

		mViewHolder.screenName.setText("@" + status.getUser().getScreenName()
				+ "/" + status.getUser().getName());

		mViewHolder.text.setText(status.getText());

		mViewHolder.tweetTime.setText(mViewHolder.date.format(status
				.getCreatedAt()));

		mViewHolder.icon.setTag(status.getUser().getBiggerProfileImageURL());
		DownLoadTask task1 = new DownLoadTask(mViewHolder.icon);
		task1.setThumn(status.getUser().getBiggerProfileImageURL());

		charSequence = Html.fromHtml(status.getSource());

		mViewHolder.via.setText("via " + charSequence.toString());

		mViewHolder.thumn.setVisibility(View.GONE);

		startItemAnimation(position, convertView);

		MediaEntity[] entities = status.getMediaEntities();
		if (entities != null) {
			for (MediaEntity media : entities) {
				url = media.getMediaURL();

				mViewHolder.thumn.setVisibility(View.VISIBLE);

				mViewHolder.thumn.setTag(url);

				DownLoadTask task2 = new DownLoadTask(mViewHolder.thumn);
				task2.setThumn(url);
			}
		}

		return convertView;

	}

	private void startItemAnimation(int position, View convertView) {

		if (mAnimatedPosition < position) {

			Animator animator = AnimatorInflater.loadAnimator(mContext,
					R.anim.card_anim);

			animator.setTarget(convertView);

			animator.start();

			mAnimatedPosition = position;
		}
	}

	public void startItemAddAnimation(Status status) {

		if (getPosition(status) == 0) {
			Animator animator = AnimatorInflater.loadAnimator(mContext,
					R.anim.item_add_anim);

			animator.setTarget(this.convertView);

			animator.start();
		}
	}

}
