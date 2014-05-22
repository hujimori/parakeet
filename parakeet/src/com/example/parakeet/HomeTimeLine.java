package com.example.parakeet;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserStreamAdapter;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * @author Yoshimori
 * 
 */
public class HomeTimeLine extends PullToRefreshListFragment implements
		OnRefreshListener2<ListView> {

	// ---------------------------------------------------------------------------------------------
	// class field declaration
	// ---------------------------------------------------------------------------------------------
	public static final String ARG_SECTION_NUMBER = "position_number"; 
	public static final String ConsumerKey = "w9MLMH6oVTiPgsTjp3EPQ";
	public static final String ConsumerSecret = "UQ62vgzN4jFEPFGABXGVnm8IKtHyw4vtolmUtVSJIvU";
	public static final int REQUEST_CODE = 1; // REQUEST_CODE
	public static final int RESULT_CODE = 2; // RESULT_CODE'
																	

	// ---------------------------------------------------------------------------------------------
	// instance field 
	// ---------------------------------------------------------------------------------------------
	private LoadStatus loadStatus;
	private PullToRefreshListView mPullToRefreshListView;
	private TwitterUpdate update;
	private Bundle bundle;
	private Twitter twitter;
	private StatusAdapter adapter;
	private Status status;
	private Context context;
	private SharedPreferences sharedPreferences;
	private Vibrator vibrator;
	private com.example.parakeet.User user;
	
	/**
	 * Factory method
	 * 
	 * @pFram position
	 * @return fragment object
	 */
	public static HomeTimeLine getInstance(int position) {
		HomeTimeLine hm = new HomeTimeLine();
		Bundle mBundle = new Bundle();
		mBundle.putInt(ARG_SECTION_NUMBER, position);
		hm.setArguments(mBundle);
		return hm;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		context = getActivity();

		adapter = new StatusAdapter(context);

		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);

		loadStatus = new LoadStatus(adapter, context);
		loadStatus.loadTimeLine();

		startUserStream();

		Gson gson = new Gson();
		user = gson.fromJson(TwitterUtils.loadUser(context),
				com.example.parakeet.User.class);

		ListView listView = getListView();
		listView.setScrollingCacheEnabled(false);
		listView.setScrollbarFadingEnabled(true);
		listView.setFastScrollEnabled(true);
		listView.setDivider(null);
		listView.setVerticalScrollBarEnabled(false);
		listView.setSelector(android.R.color.transparent);
		listView.addFooterView(LayoutInflater.from(getActivity()).inflate(
				R.layout.item_footer, listView, false));
		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		setListAdapter(adapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);

			mPullToRefreshListView = getPullToRefreshListView();

		
		mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullToRefreshListView.setOnRefreshListener(this);

	}

	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

		loadStatus.loadPastTimeline(mPullToRefreshListView);

	}

	@Override
	public void onDestroyView() {
		// TODO 自動生成されたメソッド・スタブ
		setListAdapter(null);
		super.onDestroyView();

	}

	/**
	 * begin UserStream
	 */
	private void startUserStream() {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(ConsumerKey, ConsumerSecret);
		twitterStream.setOAuthAccessToken(TwitterUtils
				.loadAccessToken(getActivity()));
		twitterStream.addListener(new MyUserStreamAdapter());
		twitterStream.user();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {

			bundle = data.getExtras();

		}

		switch (requestCode) {
		case REQUEST_CODE:
			if (resultCode == RESULT_CODE) {

				int position = bundle.getInt("position");
				Long statusId = bundle.getLong("statusID");
				status = (Status) getListView().getItemAtPosition(position);

				update = new TwitterUpdate(twitter, getActivity());
				update.favorite(statusId, adapter, position, status);

			}

		default:
			break;

		}

	}

	/**
	 * called when receive status
	 * 
	 * @author Yoshimori
	 * 
	 */
	private class MyUserStreamAdapter extends UserStreamAdapter {
		Handler handler = new Handler();
		private boolean retweetSound;
		private boolean retwetVib;
		private boolean replySound;
		private boolean replyVib;
		private boolean favSound;
		private boolean favVib;
		private boolean unfavSound;
		private boolean unfavVib;

		@Override
		public void onStatus(final Status status) {
			super.onStatus(status);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO 自動生成されたメソッド・スタブ
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO 自動生成されたメソッド・スタブ

							adapter.insert(status, 0);
							onRetweet(status);
							onReply(status);

						}
					});
				}
			}).start();

		}

		@Override
		public void onFavorite(final twitter4j.User source,
				twitter4j.User target, final Status favoritedStatus) {
			// TODO 自動生成されたメソッド・スタブ
			super.onFavorite(source, target, favoritedStatus);

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO 自動生成されたメソッド・スタブ
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO 自動生成されたメソッド・スタブ

							favSound = sharedPreferences.getBoolean(
									"FAVORITE_REGISTRATION_SOUND", false);
							if (favSound) {

								Toast.makeText(
										getActivity(),
										source.getName() + "さんが"
												+ favoritedStatus.getText()
												+ "をお気に入りに登録しました",
										Toast.LENGTH_SHORT).show();
							}

							favVib = sharedPreferences.getBoolean(
									"FAVORITE_REGISTRATION_VIBE", false);
							if (favVib) {
								vibrator.vibrate(500);
							}

						}
					});
				}
			}).start();

		}

		@Override
		public void onUnfavorite(final User source, User target,
				final Status unfavoritedStatus) {
			// TODO 自動生成されたメソッド・スタブ
			super.onUnfavorite(source, target, unfavoritedStatus);

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO 自動生成されたメソッド・スタブ
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO 自動生成されたメソッド・スタブ

							unfavSound = sharedPreferences.getBoolean(
									"FAVORITE_RELEASE", false);
							if (unfavSound) {

								Toast.makeText(
										getActivity(),
										source.getName() + "さんが"
												+ unfavoritedStatus.getText()
												+ "をお気に入を解除しました",
										Toast.LENGTH_SHORT).show();

							}

							unfavVib = sharedPreferences.getBoolean(
									"FAVORITE_RELEASE_VIBE", false);
							if (unfavVib) {
								vibrator.vibrate(500);
							}

						}
					});
				}
			}).start();

		}

		private void onRetweet(Status status) {

			if (status.isRetweet()
					&& sharedPreferences.getBoolean("RETWEET_SOUND", false)) {
				Toast.makeText(context,
						status.getUser().getName() + "がリツイートしました",
						Toast.LENGTH_SHORT).show();
			}

			if (sharedPreferences.getBoolean("RT_VIB", false)) {
				vibrator.vibrate(500);
			}

		}

		private void onReply(Status status) {

			if (sharedPreferences.getBoolean("REPLY_SOUND", false)
					&& status.getText().indexOf(user.screenName) != -1) {
				Toast.makeText(context,
						status.getUser().getName() + "からリプライが来ました",
						Toast.LENGTH_SHORT).show();
			}

			if (sharedPreferences.getBoolean("REPLY_VIB", false)) {
				vibrator.vibrate(500);
			}

		}
	}

	// おまけ：ツイート内容から通知を発行する
	/*
	 * private static void notify(Context context, long statusId, String
	 * statusText, long userId, String userScreenName) { //
	 * NotificationManager取得 NotificationManager nm = (NotificationManager)
	 * context .getSystemService(Context.NOTIFICATION_SERVICE); //
	 * Notification構築 Notification notification = new
	 * Notification(R.drawable.icon, statusText, System.currentTimeMillis()); //
	 * 通知をタップした時に起動するペンディングインテント PendingIntent contentIntent =
	 * PendingIntent.getActivity( context, 0, // ウェブのURLを処理するアプリを起動する new
	 * Intent(Intent.ACTION_VIEW, // 通知で表示されているツイートのURL
	 * Uri.parse("http://twitter.com/#!/" + userId + "/status/" + statusId)),
	 * Intent.FLAG_ACTIVITY_NEW_TASK); // 通知に表示する内容を設定
	 * notification.setLatestEventInfo(context, statusText, userScreenName,
	 * contentIntent); // 通知を発行 nm.notify(0, notification); }
	 */
}
