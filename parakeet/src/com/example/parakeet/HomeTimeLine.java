package com.example.parakeet;

import java.text.SimpleDateFormat;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

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
public class HomeTimeLine extends PullToRefreshListFragment implements OnRefreshListener2<ListView> {

	//----------------------------------------------------------------------------------------------
	// Constant declaration
	//----------------------------------------------------------------------------------------------
	private static final String ConsumerKey = "w9MLMH6oVTiPgsTjp3EPQ";
	private static final String ConsumerSecret = "UQ62vgzN4jFEPFGABXGVnm8IKtHyw4vtolmUtVSJIvU";
	private static final int REQUEST_CODE = 1; //REQUEST_CODE
	private static final int RESULT_OK = 2; //RESULT_CODE'
	public static int API_COUNT = 2; // API usage count
	public static final String ARG_SECTION_NUMBER = "position_number"; // This fragment's tag

	//----------------------------------------------------------------------------------------------
	// Field declaration
	//----------------------------------------------------------------------------------------------
	private TweetAdapter mAdapter;
	private Twitter mTwitter;
	private MyUserStreamAdapter myUserStreamAdapter;
	private TwitterStream twitterStream;
	private LoadStatus mLoadStatus;
	private PullToRefreshListView mPullToRefreshListView;
	private TwitterUpdate update;
	private Bundle bundle;

	/**
	 * Factory method
	 * @pFram position
	 * @return fragment object
	 */
	public static HomeTimeLine getInstance(int position) {
		HomeTimeLine hm = new HomeTimeLine();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, position);
		hm.setArguments(bundle);
		return hm;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {

			// Create objects
			mTwitter = TwitterUtils.getTwitterInstance(getActivity());
			mAdapter = new TweetAdapter(getActivity(), mTwitter);
			mLoadStatus = new LoadStatus(mAdapter, mTwitter, getActivity());
			myUserStreamAdapter = new MyUserStreamAdapter();

			/**
			 *  Set
			 *  ConsumerKey,ConsumerSecret
			 *  AccessToken,AccessTokenSecret
			 */
			twitterStream = new TwitterStreamFactory().getInstance();
			twitterStream.setOAuthConsumer(ConsumerKey, ConsumerSecret);
			twitterStream.setOAuthAccessToken(TwitterUtils.loadAccessToken(getActivity()));

			mLoadStatus.loadTimeLine();

			/**
			 * Start UserStream
			 */
			twitterStream.addListener(myUserStreamAdapter);
			twitterStream.user();

		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		/**
		 * Scroll setting
		 */
		getListView().setScrollingCacheEnabled(false);
		getListView().setScrollbarFadingEnabled(true);
		getListView().setFastScrollEnabled(true);

		setListAdapter(mAdapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ
		super.onListItemClick(l, v, position, id);

		Status status = (Status) getListView().getItemAtPosition(position);

		showContent(status, position);

	}

	private void showContent(Status status, int position) {

		/**
		 * Create intent and startActivity
		 */
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.putExtra("statusId", status.getId());
		intent.putExtra("screenname", status.getUser().getScreenName());
		intent.putExtra("text", status.getText());
		intent.putExtra("url", status.getUser().getBiggerProfileImageURL());
		intent.putExtra("date", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(status.getCreatedAt()));
		intent.putExtra("name", status.getUser().getName());
		intent.setClass(getActivity(), DetailedScreenActivity.class);
		startActivityForResult(intent, REQUEST_CODE);

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
			if (resultCode == RESULT_OK) {

				int position = bundle.getInt("position");
				Long statusId = bundle.getLong("statusID");
				Status status = (Status) getListView().getItemAtPosition(position);

				update = new TwitterUpdate(mTwitter, getActivity());
				update.favorite(statusId, mAdapter, position, status);

			}

		default:
			break;

		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState == null) {
			mPullToRefreshListView = getPullToRefreshListView();

		}
		mPullToRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullToRefreshListView.setOnRefreshListener(this);

	}

	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

		mLoadStatus = new LoadStatus(mAdapter, mTwitter, getActivity(), mPullToRefreshListView);
		mLoadStatus.loadPastTimeline(API_COUNT);

		API_COUNT++;

	}

	@Override
	public void onDestroyView() {
		// TODO 自動生成されたメソッド・スタブ
		setListAdapter(null);
		super.onDestroyView();

	}

	/**
	 * Called when receive status
	 * @author Yoshimori
	 *
	 */
	class MyUserStreamAdapter extends UserStreamAdapter {
		Handler handler = new Handler();


		@Override
		public void onStatus(final Status status) {
			super.onStatus(status);
			new Thread(new Runnable() {

				@Override
				public void run() {
					//TODO 自動生成されたメソッド・スタブ
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO 自動生成されたメソッド・スタブ

							mAdapter.insert(status, 0);

						}
					});
				}
			}).start();

		}
	}

}
