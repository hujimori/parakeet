package com.example.parakeet;

import twitter4j.Twitter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * 
 * @author Yoshimori
 * 
 */
public class DirectMessage extends ListFragment {

	// ---------------------------------------------------------------------------------------------
	// class field
	// ---------------------------------------------------------------------------------------------
	public static final String ARG_SECTION_NUMBER = "position_number";

	// ---------------------------------------------------------------------------------------------
	// instance field
	// ---------------------------------------------------------------------------------------------
	private DirectMessageAdapter adapter;
	private LoadStatus loadStatus;
	private Context context;

	/**
	 * Factory method
	 * 
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);

		context = getActivity();

		adapter = new DirectMessageAdapter(context);
		loadStatus = new LoadStatus(adapter, context);
		loadStatus.loadDirectMessage();
		setListAdapter(adapter);
	}

}
