package com.example.parakeet;

import twitter4j.Twitter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Mention extends PullToRefreshListFragment implements OnRefreshListener2<ListView> {

	private TweetAdapter mAdapter;
	private Twitter mTwitter;
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
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {

			mTwitter = TwitterUtils.getTwitterInstance(getActivity());

			mAdapter = new TweetAdapter(getActivity(),mTwitter);


			mLoadStatus = new LoadStatus(mAdapter, mTwitter, getActivity());
			mLoadStatus.loadMention();
		}
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onViewCreated(view, savedInstanceState);

		mPullToRefreshListView = getPullToRefreshListView();

		mLoadStatus = new LoadStatus(mAdapter, mTwitter, getActivity(), mPullToRefreshListView);

		mPullToRefreshListView.setOnRefreshListener(this);

	}

	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

		mLoadStatus.loadMention();

	}

	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
