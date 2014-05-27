package com.example.parakeet;

import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.DropBoxManager.Entry;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class TwitterUtils {

	static class Data {
		private String userData;
		private String url;
		private String banner;

		public void seturl(String url) {
			this.url = url;
		}

		public void setUserData(String userData) {
			this.userData = userData;
		}

		public void setBaneer(String banner) {
			this.banner = banner;
		}

		public String getUrl() {
			return url;
		}

		public String getUserData() {
			return userData;
		}

		public String getBanner() {
			return banner;
		}

	}

	private static final String TOKEN = "token";
	private static final String TOKEN_SECRET = "token_secret";
	private static final String USER = "user";
	private static final String FILE = "file";
	private static final String ICON_URL = "icon_url";
	private static final String ID = "id";
	private static final String BANNER_URL = "banner_url";

	private TwitterUtils() {

	}

	public static Twitter getTwitterInstance(Context mContext) {
		String consumerKey = mContext.getString(R.string.twitter_consumer_key);
		String consumerSecret = mContext
				.getString(R.string.twitter_consumer_secret);

		ConfigurationBuilder conf = new ConfigurationBuilder().setDebugEnabled(true);

		Twitter mTwitter = new TwitterFactory(conf.build()).getInstance();

		mTwitter.setOAuthConsumer(consumerKey, consumerSecret);

		if (hasAccessToken(mContext)) {
			mTwitter.setOAuthAccessToken(loadAccessToken(mContext));
		}
		return mTwitter;

	}

	public static void storeAccessToken(Context cn, AccessToken accessToken) {
		SharedPreferences preferences = cn.getSharedPreferences(
				String.valueOf(accessToken.getUserId()), Context.MODE_PRIVATE);

		Editor editor = preferences.edit();
		editor.putString(TOKEN, accessToken.getToken());
		editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
		editor.commit();

	}

	
	
	public static AccessToken loadAccessToken(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				loadID(mContext), Context.MODE_PRIVATE);

		String token = preferences.getString(TOKEN, null);
		String tokenSecret = preferences.getString(TOKEN_SECRET, null);
		if (token != null && tokenSecret != null) {
			return new AccessToken(token, tokenSecret);
		}
		return null;
	}

	public static boolean hasAccessToken(Context mContext) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return loadAccessToken(mContext) != null;
	}

	public static void saveUserData(final Context context, final String id) {

		AsyncTask<Void, Void, Data> task = new AsyncTask<Void, Void, Data>() {

			ProgressDialog mDialog;

			@Override
			protected void onPreExecute() {
				// TODO 自動生成されたメソッド・スタブ
				super.onPreExecute();
				mDialog = new ProgressDialog(context);
				mDialog.setMessage("Now Loading...");
				mDialog.setCancelable(false);
				mDialog.setCanceledOnTouchOutside(false);
				mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mDialog.show();

			}

			@Override
			protected Data doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					Twitter twitter = getTwitterInstance(context);
					User user = twitter.verifyCredentials();

					Gson gson = new Gson();
					String userData = gson.toJson(user);
					String url = user.getOriginalProfileImageURL();
					String banner = user.getProfileBannerURL();
					Data data = new Data();
					data.seturl(url);
					data.setUserData(userData);
					data.setBaneer(banner);

					return data;

				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Data result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				SharedPreferences sharedPreferences1 = context
						.getSharedPreferences(USER, Context.MODE_PRIVATE);
				Editor editor1 = sharedPreferences1.edit();
				editor1.putString(id, result.getUserData());
				editor1.commit();

				SharedPreferences sharedPreferences2 = context
						.getSharedPreferences(ICON_URL, Context.MODE_PRIVATE);
				Editor editor2 = sharedPreferences2.edit();
				editor2.putString(id, result.getUrl());
				editor2.commit();

				SharedPreferences sharedPreferences3 = context
						.getSharedPreferences(BANNER_URL, Context.MODE_PRIVATE);
				Editor editor3 = sharedPreferences3.edit();
				editor3.putString(id, result.getBanner());
				editor3.commit();

				if (mDialog != null && mDialog.isShowing()) {
					mDialog.dismiss();
					Intent intent = new Intent(context, MainActivity.class);
					context.startActivity(intent);
					((Activity) context).finish();
				}
			}
		};

		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	public static String loadUser(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				USER, Context.MODE_PRIVATE);

		String user = sharedPreferences.getString(loadID(context), null);

		return user;

	}

	public static Set<String> allLoadUser(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				USER, Context.MODE_PRIVATE);

		Map<String, ?> map = sharedPreferences.getAll();

		return map.keySet();
	}

	public static String loadUserIcon(Context context, String id) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				ICON_URL, Context.MODE_PRIVATE);

		String iconUrl = sharedPreferences.getString(id, null);

		return iconUrl;
	}

	public static String loadBanner(Context context, String id) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				BANNER_URL, Context.MODE_PRIVATE);

		String banner = sharedPreferences.getString(id, null);

		return banner;
	}

	public static String loadID(Context mContext) {

		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FILE, Context.MODE_PRIVATE);

		String id = sharedPreferences.getString(ID, null);

		return id;

	}

	public static void saveID(Context mContext, String id) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FILE, Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();

		editor.putString(ID, id);

		editor.commit();

	}
}
