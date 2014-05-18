package com.example.parakeet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView.BufferType;


public class TweetActivity extends FragmentActivity {

	private String screenname;
	private String url;
	private Twitter mTwitter;
	private EditText editText;
	private Handler mHandler;
	private Long fav;

	private TwitterUpdate update;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ�ｽ�ｽ�ｽ\�ｽb�ｽh�ｽE�ｽX�ｽ^�ｽu
		super.onCreate(arg0);
		setContentView(R.layout.activity_tweet);

		Intent intent = getIntent();

	//	mTwitter = TwitterUtils.getTwitterInstance(this);

		update = new TwitterUpdate(mTwitter, this);

		fav = intent.getLongExtra("fav", 0);

		editText = (EditText) findViewById(R.id.edit);
		if (null != intent) {
			screenname = intent.getStringExtra("screenname");

			url = intent.getStringExtra("url");
		}

		if (null != url) {
			//imageIcon = (SmartImageView) findViewById(R.id.icon);
			//imageIcon.setImageUrl(url);

		}
		else if (null == url) {
			loadUrl(mTwitter);


		}

		if (null != screenname) {

			editText.setText("@" + screenname + " ", BufferType.NORMAL);
			CharSequence text = editText.getText().toString();
			editText.setSelection(text.length());
		}

	}

	public void loadUrl(final Twitter mTwitter) {

		mHandler = new Handler();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ�ｽ�ｽ�ｽ\�ｽb�ｽh�ｽE�ｽX�ｽ^�ｽu
				try {
					//�ｽX�ｽN�ｽ�ｽ�ｽ[�ｽ�ｽ�ｽl�ｽ[�ｽ�ｽ�ｽ謫ｾ
					screenname = mTwitter.getScreenName();
					url = mTwitter.showUser(screenname).getProfileImageURL();
				} catch (TwitterException e) {
					// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ catch �ｽu�ｽ�ｽ�ｽb�ｽN
					e.printStackTrace();
				}

				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ�ｽ�ｽ�ｽ\�ｽb�ｽh�ｽE�ｽX�ｽ^�ｽu

				//imageIcon = (SmartImageView) findViewById(R.id.icon);
					//	imageIcon.setImageUrl(url);





					}
				});

			}
		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ�ｽ�ｽ�ｽ\�ｽb�ｽh�ｽE�ｽX�ｽ^�ｽu
		getMenuInflater().inflate(R.menu.tweet_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ�ｽ黷ｽ�ｽ�ｽ�ｽ\�ｽb�ｽh�ｽE�ｽX�ｽ^�ｽu
		switch (item.getItemId()) {

		case R.id.menu_edit:

			update.reply(editText, fav);
			finish();

		}

		return super.onOptionsItemSelected(item);
	}

}