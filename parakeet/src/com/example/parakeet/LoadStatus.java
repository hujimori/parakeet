package com.example.parakeet;

import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterResponse;
import twitter4j.User;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/*�^�C�����C���擾
 *
 *
 */
public class LoadStatus {

	private TweetAdapter mAdapter;
	private DirectMessageAdapter directMessageAdapter;
	private Twitter mTwitter;
	private Context mContext;
	private PullToRefreshListView mPullToRefreshListView;
//	private FollowsAdpater mFollowsAdpater;
	private PagableResponseList<User> user;

	public LoadStatus(TweetAdapter mAdapter, Twitter mTwitter, Context mcontext) {
		this.mAdapter = mAdapter;
		this.mTwitter = mTwitter;
		this.mContext = mcontext;

	}

	public LoadStatus(DirectMessageAdapter directMessageAdapter,
			Twitter mTwitter, Context mContext) {

		this.directMessageAdapter = directMessageAdapter;
		this.mTwitter = mTwitter;
		this.mContext = mContext;
	}

	/*public LoadStatus(FollowsAdpater mFollowsAdpater, Twitter mTwitter,
			Context mContext) {

		this.mFollowsAdpater = mFollowsAdpater;
		this.mTwitter = mTwitter;
		this.mContext = mContext;
	}
*/
	public LoadStatus(TweetAdapter mAdapter, Twitter mTwitter,
			Context mContext, PullToRefreshListView mPullToRefreshListView) {

		this.mAdapter = mAdapter;
		this.mTwitter = mTwitter;
		this.mContext = mContext;
		this.mPullToRefreshListView = mPullToRefreshListView;

	}
	// HomeTimeLine取得
	
	public void loadTimeLine() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return mTwitter.getHomeTimeline(new Paging(1).count(30));
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
				} else {
					showToast("タイムラインの取得に失敗しました");

				}
			}

		};
		task.execute();
	}

	// MntionsTimeLine取得
	public void loadMention() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return mTwitter.getMentionsTimeline();
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
				}

				else {
					showToast("リプライの取得に失敗しました。");
				}

				if (mPullToRefreshListView != null)
					mPullToRefreshListView.onRefreshComplete();

			}

		};

		task.execute();

	}

	// �ߋ�20����status�擾
	public void loadPastTimeline(final int count) {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return mTwitter
							.getHomeTimeline(new Paging(count).count(30));
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {

					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
				} else {
					showToast("過去のタイムラインの取得に失敗しました");

				}
				mPullToRefreshListView.onRefreshComplete();
			}

		};
		task.execute();
	}

	public void loadDirectMessage() {
		AsyncTask<Void, Void, ResponseList<DirectMessage>> task = new AsyncTask<Void, Void, ResponseList<DirectMessage>>() {
			@Override
			protected ResponseList<DirectMessage> doInBackground(Void... params) {
				try {
					return mTwitter.getDirectMessages();
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(ResponseList<DirectMessage> result) {
				if (result != null) {

					for (DirectMessage dm : result) {
						directMessageAdapter.add(dm);
					}
				}

				else {
					showToast("���v���C�̎擾�Ɏ��s���܂���");
				}

				if (mPullToRefreshListView != null)
					mPullToRefreshListView.onRefreshComplete();

			}

		};

		task.execute();

	}

	public void loadUserTimeLine() {

		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {

			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {
					return mTwitter.getUserTimeline();
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(List<twitter4j.Status> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {

					for (twitter4j.Status status : result) {

						mAdapter.add(status);

					}
					showToast("タイムラインの取得に成功しました。");
				} else {
					showToast("タイムラインの取得に失敗しました。");
				}

			}

		};
		task.execute();

	}

/*	public void loadFollows() {

		AsyncTask<Void, Void, PagableResponseList<User>> task = new AsyncTask<Void, Void, PagableResponseList<User>>() {

			@Override
			protected PagableResponseList<User> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					user = mTwitter.getFriendsList(mTwitter.getId(), -1);

					return user;

				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(PagableResponseList<User> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {

					for (User user : result) {

						mFollowsAdpater.add(user);
					}
				} else {
					showToast("取得に失敗しました。");
				}

			}

		};
		task.execute();

	}
*/
/*	public void loadFollowers() {

		AsyncTask<Void, Void, PagableResponseList<User>> task = new AsyncTask<Void, Void, PagableResponseList<User>>() {

			@Override
			protected PagableResponseList<User> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					user = mTwitter.getFollowersList(mTwitter.getId(), -1);

					return user;

				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			protected void onPostExecute(PagableResponseList<User> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {

					for (User user : result) {

						mFollowsAdpater.add(user);
					}
				} else {
					showToast("取得に失敗しました。");
				}

			}

		};
		task.execute();

	}
	*/
	public void loadFavorites() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return mTwitter.getFavorites(new Paging(1).count(30));
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				if (result != null) {
					mAdapter.clear();
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
				} else {
					showToast("取得できませんでした。");

				}
			}

		};
		task.execute();
	}

	private void showToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

}
