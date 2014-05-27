package com.example.parakeet;

import java.io.File;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

public class TwitterUpdate {

	private Twitter mTwitter;
	private Context mContext;

	private static final String ConsumerKey = "w9MLMH6oVTiPgsTjp3EPQ";
	private static final String ConsumerSecret = "UQ62vgzN4jFEPFGABXGVnm8IKtHyw4vtolmUtVSJIvU";

	public TwitterUpdate(Twitter mTwitter, Context mContext) {
		this.mTwitter = mTwitter;
		this.mContext = mContext;
	}

	public void tweet(final EditText editText) {
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {
					mTwitter.updateStatus(params[0]);
					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					showToast("ツイートが完了しました。");

				} else {
					showToast("ツイートに失敗しました");
				}

			}
		};
		task.execute(editText.getText().toString());
	}

	
	public void uploadTwitter(final String tweet, final String path) {
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {

				//	Configuration conf = getConf(MediaProvider.TWITPIC.toString(), mContext.getResources().getString(R.string.twitpic));
			
				//	ImageUpload imageUpload = new ImageUploadFactory(conf)
				//			.getInstance();
				//	String url = imageUpload.upload(new File(path));

					StatusUpdate update = new StatusUpdate(tweet);
					update.setMedia(new File(path));
					mTwitter.updateStatus(update);
					
					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					showToast("ツイートが完了しました。");

				} else {
					showToast("ツイートに失敗しました");
				}

			}
		};
		task.execute(tweet);
	}
	
	public void uploadTwitpic(final String tweet, final String path) {
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {

					Configuration conf = getConf(MediaProvider.TWITPIC.toString(), mContext.getResources().getString(R.string.twitpic));
			
					ImageUpload imageUpload = new ImageUploadFactory(conf)
							.getInstance();
					String url = imageUpload.upload(new File(path));

					mTwitter.updateStatus(new StatusUpdate(params[0] + url));

					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					showToast("ツイートが完了しました。");

				} else {
					showToast("ツイートに失敗しました");
				}

			}
		};
		task.execute(tweet);
	}

	public void reply(final EditText editText, final Long fav) {
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {
					StatusUpdate statusUpdate = new StatusUpdate(editText
							.getText().toString());
					statusUpdate.setInReplyToStatusId(fav);
					mTwitter.updateStatus(statusUpdate);
					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					showToast("リプライを送りました");

				} else {
					showToast("リプライが失敗しました");
				}
			}
		};
		task.execute();
	}

	public void retweet(final Long fav) {
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {
					mTwitter.retweetStatus(fav);
					return true;
				} catch (TwitterException e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					showToast("リツイート完了しました");

				} else {
					showToast("リツイートが失敗しました");
				}
			}
		};
		task.execute();
	}

	public void favorite(final Long statusId, final StatusAdapter mAdapter,
			final int position, final Status status) {

		AsyncTask<Void, Void, twitter4j.Status> task = new AsyncTask<Void, Void, twitter4j.Status>() {

			@Override
			protected twitter4j.Status doInBackground(Void... params) {

				try {

					return mTwitter.createFavorite(statusId);

				} catch (TwitterException e) {
					e.printStackTrace();
					return null;
				}
			}

			protected void onPostExecute(twitter4j.Status result) {

				if (result != null) {
					mAdapter.remove(status);
					mAdapter.insert(result, position - 1);
					mAdapter.notifyDataSetChanged();
					showToast("お気に入りに追加しました");

				} else {
					showToast("お気に入りへの追加に失敗しました");
				}
			}
		};
		task.execute();
	}

	private void showToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	private Configuration getConf(String provider, String ApiKey) {
		return new ConfigurationBuilder()
				.setOAuthConsumerKey(
						mContext.getResources().getString(
								R.string.twitter_consumer_key))
				.setOAuthConsumerSecret(
						mContext.getResources().getString(
								R.string.twitter_consumer_secret))
				.setOAuthAccessToken(
						TwitterUtils.loadAccessToken(mContext).getToken())
				.setOAuthAccessTokenSecret(
						TwitterUtils.loadAccessToken(mContext).getTokenSecret())
				.setMediaProvider(provider).setMediaProviderAPIKey(ApiKey)
				.build();
	}
}
