package com.example.parakeet;

import twitter4j.Twitter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Mention extends PullToRefreshListFragment implements
		OnRefreshListener2<ListView> {

	private StatusAdapter mAdapter;
	private PullToRefreshListView mPullToRefreshListView;

	public static final String ARG_SECTION_NUMBER = "position_number";

	private LoadStatus mLoadStatus;

	public static Mention getInstance(int position) {
		Mention mention = new Mention();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_SECTION_NUMBER, position);
		mention.setArguments(bundle);
		return mention;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {

			// mTwitter = TwitterUtils.getTwitterInstance(getActivity());

			// mAdapter = new TweetAdapter(getActivity());

			mLoadStatus = new LoadStatus(mAdapter, getActivity());
			mLoadStatus.loadMention();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onViewCreated(view, savedInstanceState);

		mPullToRefreshListView = getPullToRefreshListView();

		mPullToRefreshListView.setOnRefreshListener(this);

	}

	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		mLoadStatus.loadMention();

	}

	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
