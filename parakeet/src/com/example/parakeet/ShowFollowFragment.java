package com.example.parakeet;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

public class ShowFollowFragment extends ListFragment {

private FriendsAdapter friendsAdapter;
private LoadStatus loadStatus;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		
		friendsAdapter = new FriendsAdapter(getActivity());
		
		loadStatus = new LoadStatus(friendsAdapter,getActivity());
		
		loadStatus.loadFollows();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);
		
		setListAdapter(friendsAdapter);
	}
}
