package com.example.parakeet;

import twitter4j.Twitter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 *
 * @author Yoshimori
 *
 */
public class DirectMessage extends ListFragment {

	//----------------------------------------------------------------------------------------------
	// Constant declaration
	//----------------------------------------------------------------------------------------------
	public static final String ARG_SECTION_NUMBER = "position_number"; // This fragment's tag

	//----------------------------------------------------------------------------------------------
	// Field declaration
	//----------------------------------------------------------------------------------------------
	private DirectMessageAdapter directMessageAdapter;
	private Twitter mTwitter;
	private LoadStatus loadStatus;

	/**
	 * Factory method
	 * @param position
	 * @return fragment object
	 */
	public static DirectMessage getInstance(int position) {

		DirectMessage directMessage = new DirectMessage();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, position);
		directMessage.setArguments(bundle);
		return directMessage;

	}

	/**
	 * Called when the activity is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {

			directMessageAdapter = new DirectMessageAdapter(getActivity());

			mTwitter = TwitterUtils.getTwitterInstance(getActivity());

			loadStatus = new LoadStatus(directMessageAdapter, mTwitter, getActivity());
			loadStatus.loadDirectMessage();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);

		setListAdapter(directMessageAdapter);
	}

}
