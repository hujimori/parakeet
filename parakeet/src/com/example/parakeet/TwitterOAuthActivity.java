package com.example.parakeet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TwitterOAuthActivity extends Activity {

	private String mCallBackURL;
	private Twitter mTwitter;
	private RequestToken mRequestToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_oauth);

		mCallBackURL = getString(R.string.twitter_callback_url);
		mTwitter = TwitterUtils.getTwitterInstance(this);

		findViewById(R.id.action_start_oauth).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				startAuthorize();

			}

		});
	}

	//OAuth�F�؁i�����ɂ͔F�j���J�n���܂��B

	private void startAuthorize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				try {
					mRequestToken = mTwitter.getOAuthRequestToken(mCallBackURL);

					return mRequestToken.getAuthorizationURL();
				}
				catch (TwitterException e) {
					e.printStackTrace();
				}

				return null;
			}

			public void onPostExecute(String url) {
				if (url != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

					startActivity(intent);
				}
				else {
					//���s
				}
			}

		};
		task.execute();

	}

	public void onNewIntent(Intent intent) {
		if (intent == null || intent.getData() == null || !intent.getData().toString().startsWith(mCallBackURL)) {
			return;
		}
		String verifier = intent.getData().getQueryParameter("oauth_verifier");

		AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
			protected AccessToken doInBackground(String... params) {
				try {
					return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);

				}
				catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(AccessToken accessToken) {
				if (accessToken != null) {
					//�F�ؐ���

					showToast("認証に成功しました");
					successOAuth(accessToken);
				}
				else {
					showToast("認証に失敗しました");
				}
			}
		};
		task.execute(verifier);

	}

	private void successOAuth(AccessToken accessToken) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		TwitterUtils.storeAccessToken(this, accessToken);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void showToast(String text) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

	}

}
