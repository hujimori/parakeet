package com.example.parakeet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.sax.StartElementListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class Profile extends FragmentActivity implements OnClickListener {

	private static final int REQUEST_CODE_1 = 1;
	private static final int REQUSET_CODE_2 = 2;
	private static final int REQUEST_CODE_3 = 3;
	private static final int REQUEST_CODE_4 = 4;
	private static final int RESULT_CODE_1 = 1;
	private static final int RESULT_CODE_2 = 2;
	private static final int RESULT_CODE_3 = 3;
	private static final int RESULT_CODE_4 = 4;

	private SmartImageView icon;
	private SmartImageView header;
	private Button tweet;
	private Button follows;
	private Button followers;
	private Button followes;
	private Button favorites;
	private TextView bio;
	private TextView url;
	private TextView entry;
	private TextView id;
	private Twitter mTwitter;;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(arg0);
		setContentView(R.layout.profile);

		mTwitter = TwitterUtils.getTwitterInstance(this);

		tweet = (Button) findViewById(R.id.tweet);
		tweet.setOnClickListener(this);

		followes = (Button) findViewById(R.id.follows);
		followes.setOnClickListener(this);

		followers = (Button) findViewById(R.id.followers);
		followers.setOnClickListener(this);

		favorites = (Button) findViewById(R.id.favorites);
		favorites.setOnClickListener(this);

		icon = (SmartImageView) findViewById(R.id.icon);
		header = (SmartImageView) findViewById(R.id.header);
		bio = (TextView) findViewById(R.id.bio_text);
		url = (TextView) findViewById(R.id.URL_text);
		entry = (TextView) findViewById(R.id.entryday);
		id = (TextView) findViewById(R.id.Id);

		AsyncTaskLoader task = new AsyncTaskLoader(mTwitter, this, icon,
				header, bio, url, entry, id);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ

		Intent intent;

		switch (v.getId()) {

		case R.id.tweet:
			intent = new Intent();
			intent.setClass(this, UserTimeLine.class);
			startActivityForResult(intent, REQUEST_CODE_1);
			break;

		case R.id.follows:
			intent = new Intent();
			intent.setClass(this, Follows.class);
			startActivityForResult(intent, REQUSET_CODE_2);
			break;

		case R.id.followers:
			intent = new Intent();
			intent.setClass(this, Followers.class);
			startActivityForResult(intent, REQUEST_CODE_3);
			break;

		case R.id.favorites:
			intent = new Intent();
			intent.setClass(this, Favorites.class);
			startActivityForResult(intent, REQUEST_CODE_4);
			break;

		}

	}

	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestcode, resultcode, data);
	}

}

class AsyncTaskLoader extends AsyncTask<Void, Void, ProfileData> {

	private Twitter mTwitter;
	private ProgressDialog mDialog;
	private Context mContext;
	private SmartImageView icon;
	private SmartImageView header;
	private TextView bio;
	private TextView url;
	private TextView entry;
	private TextView id;
	private DateFormat date;
	private ProfileData mProfileData;

	public AsyncTaskLoader(Twitter mTwitter, Context mContext,
			SmartImageView icon, SmartImageView header, TextView bio,
			TextView url, TextView entry, TextView id) {
		this.mContext = mContext;
		this.mTwitter = mTwitter;
		this.icon = icon;
		this.header = header;
		this.bio = bio;
		this.entry = entry;
		this.id = id;
		this.url = url;
	}

	@Override
	protected void onPreExecute() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPreExecute();

		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage("Now Loading....");
		mDialog.setCancelable(false);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.show();
	}

	@Override
	protected ProfileData doInBackground(Void... params) {

		mProfileData = new ProfileData();
		try {

			User user = mTwitter.verifyCredentials();

			mProfileData.setIURL(user.getBiggerProfileImageURL());
			mProfileData.setBURL(user.getProfileBannerURL());
			mProfileData.setDescription(user.getDescription());
			mProfileData.setDate(user.getCreatedAt());
			mProfileData.setID(user.getId());
			mProfileData.setURL(user.getURL());

			return mProfileData;

		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(ProfileData mProfileData) {

		if (mProfileData != null) {

			if (mProfileData.getIURL() != null) {
				icon.setImageUrl(mProfileData.getIURL());
			}

			if (mProfileData.getBURL() != null) {
				header.setImageUrl(mProfileData.getBURL());
			}

			if (mProfileData.getDescription() != null) {
				bio.setText(mProfileData.getDescription());
			}

			if (mProfileData.getDate() != null) {
				date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				entry.setText(date.format(mProfileData.getDate()));
			}

			if (mProfileData != null) {
				id.setText(String.valueOf(mProfileData.getID()));
			}

			if (mProfileData != null) {
				url.setText(mProfileData.getURL());
			}

			if (mDialog != null && mDialog.isShowing()) {
				mDialog.dismiss();
			}
		}
	}

}
