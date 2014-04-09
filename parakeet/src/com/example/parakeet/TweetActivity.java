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

import com.loopj.android.image.SmartImageView;

public class TweetActivity extends FragmentActivity {

	private String screenname;
	private SmartImageView imageIcon;
	private String url;
	private Twitter mTwitter;
	private EditText editText;
	private Handler mHandler;
	private Long fav;

	private TwitterUpdate update;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
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
			imageIcon = (SmartImageView) findViewById(R.id.icon);
			imageIcon.setImageUrl(url);

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
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				try {
					//�X�N���[���l�[���擾
					screenname = mTwitter.getScreenName();
					url = mTwitter.showUser(screenname).getProfileImageURL();
				} catch (TwitterException e) {
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}

				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO �����������ꂽ���\�b�h�E�X�^�u

						imageIcon = (SmartImageView) findViewById(R.id.icon);
						imageIcon.setImageUrl(url);





					}
				});

			}
		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		getMenuInflater().inflate(R.menu.tweet_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (item.getItemId()) {

		case R.id.menu_edit:

			update.reply(editText, fav);
			finish();

		}

		return super.onOptionsItemSelected(item);
	}

}