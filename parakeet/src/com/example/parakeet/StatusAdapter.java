package com.example.parakeet;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.parakeet.R.layout;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StatusAdapter extends ArrayAdapter<twitter4j.Status> {

	private class ViewHolder {
		TextView screenName;
		TextView text;
		TextView via;
		TextView tweetTime;
		java.text.DateFormat date;
		ImageView icon;
		ImageView[] thumnail = new ImageView[6];
		LinearLayout layout;
	}

	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	private CharSequence charSequence;
	private Context mContext;
	private String url;

	private static final Pattern URL_MATCH_PATTERN = Pattern.compile(
			"(http|https):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern URL_IMG = Pattern.compile(
			"(http://|https://){1}[\\w\\.\\-/:&?,=#]+(jpg|jpeg|gif|png|bmp)",
			Pattern.CASE_INSENSITIVE);

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

			mViewHolder.thumnail[0] = (ImageView) convertView
					.findViewById(R.id.thum1);

			mViewHolder.thumnail[1] = (ImageView) convertView
					.findViewById(R.id.thum2);

			mViewHolder.thumnail[2] = (ImageView) convertView
					.findViewById(R.id.thum3);

			mViewHolder.thumnail[3] = (ImageView) convertView
					.findViewById(R.id.thum4);

			mViewHolder.thumnail[4] = (ImageView) convertView
					.findViewById(R.id.thum5);

			mViewHolder.thumnail[5] = (ImageView) convertView
					.findViewById(R.id.thum6);
			// mViewHolder.layout = (LinearLayout) convertView

			// .findViewById(R.id.image);

			convertView.setTag(mViewHolder);

		}

		else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		mViewHolder.thumnail[0].setVisibility(View.GONE);
		mViewHolder.thumnail[1].setVisibility(View.GONE);
		mViewHolder.thumnail[2].setVisibility(View.GONE);
		mViewHolder.thumnail[3].setVisibility(View.GONE);
		mViewHolder.thumnail[4].setVisibility(View.GONE);
		mViewHolder.thumnail[5].setVisibility(View.GONE);
	
		mViewHolder.screenName.setText("@" + status.getUser().getScreenName()
				+ "/" + status.getUser().getName());

		mViewHolder.text.setText(status.getText());

		mViewHolder.tweetTime.setText(mViewHolder.date.format(status
				.getCreatedAt()));

//		mViewHolder.icon.setVisibility(View.INVISIBLE);
		mViewHolder.icon.setTag(status.getUser().getBiggerProfileImageURL());
		DownLoadTask task1 = new DownLoadTask(mViewHolder.icon);
		task1.setProfileIcon(status.getUser().getBiggerProfileImageURL());

		charSequence = Html.fromHtml(status.getSource());

		mViewHolder.via.setText("via " + charSequence.toString());

		// mViewHolder.layout.setVisibility(View.GONE);

		URLEntity[] entities = status.getURLEntities();
		if (entities != null) {
			int i = 0;
			for (URLEntity media : entities) {
				String url = media.getExpandedURL();
				Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show(); 
				//mViewHolder.thumnail[i].setVisibility(View.INVISIBLE);
				//mViewHolder.thumnail[i].setTag(url);
				//DownLoadTask task = new DownLoadTask(mViewHolder.thumnail[i]);
				//task.setThumnail(url);
				i++;
			}
			
		}

		/*
		 * if (entities != null) { SpannableString spannable = new
		 * SpannableString(status.getText()); // Matcher matcher =
		 * URL_MATCH_PATTERN.matcher(status.getText()); Matcher matcher =
		 * URL_IMG.matcher(status.getText()); int i = 0;
		 * 
		 * while (matcher.find()) {
		 * 
		 * // Toast.makeText(mContext,urlEntity.getURL(), // Toast.LENGTH_SHORT)
		 * // .show(); String match = matcher.group(); UrlClickableSpan span =
		 * new UrlClickableSpan(match); spannable.setSpan(span, matcher.start(),
		 * matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		 * 
		 * Toast.makeText(mContext, match, Toast.LENGTH_SHORT).show();
		 * 
		 * /* mViewHolder.thumnail[i].setTag(match); DownLoadTask task = new
		 * DownLoadTask(mViewHolder.thumnail[i]); task.setThumnail(match);
		 */
		// i++;
		/*
		 * }
		 * 
		 * mViewHolder.text.setText(spannable); mViewHolder.text
		 * .setMovementMethod(LinkMovementMethod.getInstance()); } else {
		 * mViewHolder.text.setText(status.getText()); }
		 */
		/*
		 * if (entities != null) { for (MediaEntity media : entities) { url =
		 * media.getMediaURL();
		 * 
		 * ImageView thumnail = (ImageView) mInflater.inflate(
		 * R.layout.thumnail, null);
		 * 
		 * 
		 * ImageView thunail = (ImageView) mInflater.inflate(R.layout.imageview,
		 * null); thunail.setVisibility(View.GONE); thunail.setTag(url);
		 * 
		 * DownLoadTask task2 = new DownLoadTask(thunail);
		 * task2.setThumnail(url,mViewHolder.layout);
		 */

		return convertView;

	}

	// ClickableSpanを継承したクラス。
	// onClickの実装のためにいるんだと思う。
	class UrlClickableSpan extends ClickableSpan {

		private String url;

		public UrlClickableSpan(String url) {
			super();
			this.url = url;
		}

		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse(this.url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			mContext.startActivity(intent);

		}
	}

}
