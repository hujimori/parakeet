package com.example.parakeet;

import twitter4j.Twitter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

/**
 *
 * @author Yoshimori
 *
 */
public class DetailedScreenFragment extends Fragment {

	//----------------------------------------------------------------------------------------------
	// Constant declaration
	//----------------------------------------------------------------------------------------------
	private static final int RESULT_OK = 2; // RESULT_CODE

	//----------------------------------------------------------------------------------------------
	// Field declaration
	//----------------------------------------------------------------------------------------------
	private Twitter mTwitter;
	private String screenName;
	private String userName;
	private String iconUrl;
	private String tweetText;
	private String date;
	private Long statusId;
	private Bundle args;
	private TwitterUpdate update;
	private Button replyButton;
	private Button favButton;
	private Button retweetButton;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private SmartImageView iconImage;

	/**
	 * Called when the Fragment is created
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		View v = (View) inflater.inflate(R.layout.detailed_screen_fragmnet, container, false);

		// Create objects
		args = getArguments();
		mTwitter = TwitterUtils.getTwitterInstance(getActivity());
		update = new TwitterUpdate(mTwitter, getActivity());

		// Get the statusID
		statusId = args.getLong("statusId");

		// Get the screenName and set it
		screenName = args.getString("screenname");
		textView1 = (TextView) v.findViewById(R.id.screen_name);
		textView1.setText("@" + screenName);

		// Get the userName and set it
		userName = args.getString("name");
		textView2 = (TextView) v.findViewById(R.id.name);
		textView2.setText(userName);

		// Get the profileIcon and set it
		iconUrl = args.getString("url");
		iconImage = (SmartImageView) v.findViewById(R.id.icon);
		iconImage.setImageUrl(iconUrl);

		// Get the tweetText and set it
		tweetText = args.getString("text");
		textView3 = (TextView) v.findViewById(R.id.text);
		textView3.setText(tweetText);

		// Get the tweetDate and set it
		date = args.getString("date");
		textView4 = (TextView) v.findViewById(R.id.tweetTime);
		textView4.setText(date);

		// Click the button and send the reply
		replyButton = (Button) v.findViewById(R.id.reply_button);
		replyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Create the intent and start TweetActivity
				Intent intent = new Intent();
				intent.putExtra("screenname", screenName);
				intent.putExtra("url", iconUrl);
				intent.putExtra("statusId", statusId);
				intent.setClass(getActivity(), TweetActivity.class);
				startActivityForResult(intent, 0);
				getActivity().finish();

			}
		});

		// Click the button and add to the favorite
		favButton = (Button) v.findViewById(R.id.favorite_button);
		favButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int position = args.getInt("position");

				// Create the intent and set valuse
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				bundle.putLong("statusID", statusId);
				intent.putExtras(bundle);
				getActivity().setResult(RESULT_OK, intent);
				getActivity().finish();

			}
		});

		// Click the button and retweet
		retweetButton = (Button) v.findViewById(R.id.retweet_button);
		retweetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				update.retweet(statusId); //retweet
				getActivity().finish();

			}
		});

		return v;

	}

}
