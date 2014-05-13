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
import twitter4j.UserList;
import android.R.string;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/*�^�C�����C���擾
 *
 *
 */
public class LoadStatus {

	private FriendsAdapter fAdapter;
	private StatusAdapter mAdapter;
	private DirectMessageAdapter directMessageAdapter;
	private ListAdapter listAdapter;
	private Context mContext;
	private PullToRefreshListView mPullToRefreshListView;
	private final int TWEET_NUMBER = 30;

	private static int API_COUNT = 2;

	public LoadStatus(StatusAdapter mAdapter, Context mContext) {

		this.mAdapter = mAdapter;
		this.mContext = mContext;

	}

	public LoadStatus(FriendsAdapter fAdapter, Context context) {

		this.fAdapter = fAdapter;
		this.mContext = context;
	}

	public LoadStatus(DirectMessageAdapter dAdapter, Context mContext) {

	}

	public LoadStatus(ListAdapter lAdapter, Context context) {

		this.listAdapter = lAdapter;
		this.mContext = context;

	}

	// HomeTimeLine取得

	public void loadTimeLine() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return TwitterUtils.getTwitterInstance(mContext)
							.getHomeTimeline(new Paging(1).count(30));
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
					return TwitterUtils.getTwitterInstance(mContext)
							.getMentionsTimeline();
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
	public void loadPastTimeline(
			final PullToRefreshListView mPullToRefreshListView) {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return TwitterUtils.getTwitterInstance(mContext)
							.getHomeTimeline(
									new Paging(API_COUNT).count(TWEET_NUMBER));
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
					API_COUNT++;
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
					return TwitterUtils.getTwitterInstance(mContext)
							.getDirectMessages();
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
					return TwitterUtils.getTwitterInstance(mContext)
							.getUserTimeline();
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

	public void loadFollows() {

		AsyncTask<Void, Void, PagableResponseList<User>> task = new AsyncTask<Void, Void, PagableResponseList<User>>() {

			@Override
			protected PagableResponseList<User> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					Twitter twitter = TwitterUtils.getTwitterInstance(mContext);
					return twitter.getFriendsList(twitter.getId(), -1);

				} catch (TwitterException e) { // TODO 自動生成された catch ブロック

					e.printStackTrace();

				}

				return null;

			}

			@Override
			protected void onPostExecute(PagableResponseList<User> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {

					for (User user : result) {

						fAdapter.add(user);
					}
				} else {
					showToast("取得に失敗しました。");
				}

			}

		};

		task.execute();
	}

	public void loadFollowers() {

		AsyncTask<Void, Void, PagableResponseList<User>> task = new AsyncTask<Void, Void, PagableResponseList<User>>() {

			@Override
			protected PagableResponseList<User> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				Twitter mTwitter = TwitterUtils.getTwitterInstance(mContext);

				try {
					return mTwitter.getFollowersList(mTwitter.getId(), -1);
				} catch (IllegalStateException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();

					return null;
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();

					return null;
				}
			}

			@Override
			protected void onPostExecute(PagableResponseList<User> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
					for (User user : result) {
						fAdapter.add(user);
					}
				} else {
					showToast("取得に失敗しました。");
				}
			}

		};
		task.execute();
	}

	/*
	 * public void loadFollowers() {
	 * 
	 * AsyncTask<Void, Void, PagableResponseList<User>> task = new
	 * AsyncTask<Void, Void, PagableResponseList<User>>() {
	 * 
	 * @Override protected PagableResponseList<User> doInBackground(Void...
	 * params) { // TODO 自動生成されたメソッド・スタブ
	 * 
	 * try {
	 * 
	 * user = mTwitter.getFollowersList(mTwitter.getId(), -1);
	 * 
	 * return user;
	 * 
	 * } catch (TwitterException e) { // TODO 自動生成された catch ブロック
	 * e.printStackTrace(); }
	 * 
	 * return null; }
	 * 
	 * protected void onPostExecute(PagableResponseList<User> result) { // TODO
	 * 自動生成されたメソッド・スタブ super.onPostExecute(result);
	 * 
	 * if (result != null) {
	 * 
	 * for (User user : result) {
	 * 
	 * mFollowsAdpater.add(user); } } else { showToast("取得に失敗しました。"); }
	 * 
	 * }
	 * 
	 * }; task.execute();
	 * 
	 * }
	 */
	public void loadFavorites() {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				try {
					return TwitterUtils.getTwitterInstance(mContext)
							.getFavorites(new Paging(1).count(30));
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
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	private void showToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	public void loadLists() {

		AsyncTask<Void, Void, ResponseList<UserList>> task = new AsyncTask<Void, Void, ResponseList<UserList>>() {

			@Override
			protected ResponseList<UserList> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				Twitter mTwitter = TwitterUtils.getTwitterInstance(mContext);
				try {
					return mTwitter.getUserLists(mTwitter.getScreenName());
				} catch (IllegalStateException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					return null;
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(ResponseList<UserList> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
					for (UserList list : result) {
						listAdapter.add(list);
					}
				}
			}

		};
		task.execute();
	}

	public void loadListTimeLine(final int id) {

		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {

			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					return TwitterUtils.getTwitterInstance(mContext)
							.getUserListStatuses(id, new Paging(1));
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
					for (twitter4j.Status status : result) {
						mAdapter.add(status);
					}
				}
			}

		};
		task.execute();
	}
}
