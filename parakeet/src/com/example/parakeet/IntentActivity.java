package com.example.parakeet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class IntentActivity extends FragmentActivity {

	private String mCallBackURL;
	private Twitter twitter;
	private RequestToken mRequestToken;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(arg0);
		setContentView(R.layout.activity_twitter_oauth);

		Button button = (Button) findViewById(R.id.action_start_oauth);

		mCallBackURL = getString(R.string.twitter_callback_url2);

		String consumerKey = this.getString(R.string.twitter_consumer_key);
		String consumerSecret = this
				.getString(R.string.twitter_consumer_secret);

		ConfigurationBuilder conf = new ConfigurationBuilder().setDebugEnabled(true);

		twitter = new TwitterFactory(conf.build()).getInstance();

		twitter.setOAuthConsumer(consumerKey, consumerSecret);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ

				startAuthorize();

			}
		});
	}

	private void startAuthorize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				try {
					mRequestToken = twitter.getOAuthRequestToken(mCallBackURL);

					return mRequestToken.getAuthorizationURL();
				} catch (TwitterException e) {
					e.printStackTrace();
				}

				return null;
			}

			public void onPostExecute(String url) {
				if (url != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse(url));

					startActivity(intent);
				} else {
					// ���s
				}
			}

		};
		task.execute();

	}

	public void onNewIntent(Intent intent) {

		showToast("認証中");

		if (intent == null || intent.getData() == null
				|| !intent.getData().toString().startsWith(mCallBackURL)) {
			return;
		}
		String verifier = intent.getData().getQueryParameter("oauth_verifier");

		AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
			protected AccessToken doInBackground(String... params) {
				try {
					return twitter
							.getOAuthAccessToken(mRequestToken, params[0]);

				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(AccessToken accessToken) {

				if (accessToken != null) {

					showToast("認証に成功しました");
					successOAuth(accessToken);
				} else {
					showToast("認証に失敗しました");
				}
			}
		};
		task.execute(verifier);

	}

	private void successOAuth(AccessToken accessToken) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		TwitterUtils.saveID(this, String.valueOf(accessToken.getUserId()));
		TwitterUtils.storeAccessToken(this, accessToken);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void showToast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

	}
}
