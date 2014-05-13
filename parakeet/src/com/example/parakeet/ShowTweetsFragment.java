package com.example.parakeet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

public class ShowTweetsFragment extends ListFragment {

	private StatusAdapter adapter;
	private LoadStatus loadStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		
		adapter = new StatusAdapter(getActivity());
		
		loadStatus = new LoadStatus(adapter, getActivity());
		
		loadStatus.loadUserTimeLine();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);
		
		setListAdapter(adapter);
	}

	
	
}
