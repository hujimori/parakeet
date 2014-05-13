package com.example.parakeet;

import twitter4j.UserList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class ListTimeLine extends ListFragment {

	public static ListTimeLine getInstance(int id) {
		ListTimeLine instance = new ListTimeLine();
		Bundle mBundle = new Bundle();
		mBundle.putInt("ID", id);
		instance.setArguments(mBundle);
		return instance;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);
		
		StatusAdapter mAdapter = new StatusAdapter(getActivity());
		LoadStatus loadStatus = new LoadStatus(mAdapter, getActivity());
		loadStatus.loadListTimeLine(getArguments().getInt("ID"));
		setListAdapter(mAdapter);
		
	}
	
	
}
