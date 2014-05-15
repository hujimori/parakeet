package com.example.parakeet;

import twitter4j.UserList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class ListTimeLineFragment extends ListFragment {

	public static ListTimeLineFragment getInstance(long listId) {
		ListTimeLineFragment instance = new ListTimeLineFragment();
		Bundle mBundle = new Bundle();
		mBundle.putLong("LIST_ID", listId);
		instance.setArguments(mBundle);
		return instance;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);
		
		StatusAdapter mAdapter = new StatusAdapter(getActivity());
		LoadStatus loadStatus = new LoadStatus(mAdapter, getActivity());
		loadStatus.loadListTimeLine(getArguments().getLong("LIST_ID"));
		setListAdapter(mAdapter);
		
	}
	
	
}
