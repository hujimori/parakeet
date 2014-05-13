package com.example.parakeet;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowFollowerFragment extends ListFragment {

	private FriendsAdapter friendsAdapter;
	private LoadStatus loadStatus;

	
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		
		friendsAdapter = new FriendsAdapter(getActivity());
		
		loadStatus = new LoadStatus(friendsAdapter, getActivity());
		
		loadStatus.loadFollowers();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);
		
		setListAdapter(friendsAdapter);
	
	}

	
}
