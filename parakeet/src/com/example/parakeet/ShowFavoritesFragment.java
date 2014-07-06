package com.example.parakeet;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowFavoritesFragment extends ListFragment {

	private StatusAdapter adapter;
	private LoadStatus loadStatus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		
		adapter = new StatusAdapter(getActivity());
		
		loadStatus = new LoadStatus(adapter, getActivity());
		
		loadStatus.loadFavorites();
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
		
		setListAdapter(adapter);
	}

	
}
