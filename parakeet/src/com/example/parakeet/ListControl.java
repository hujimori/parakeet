package com.example.parakeet;

import java.net.URI;
import java.util.Date;
import java.util.List;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * this class makes and updates, delete list and so on
 * 
 * @author Yoshimori
 * 
 */
public class ListControl {
	// ---------------------------------------------------------------------------------------------
	// instance field
	// ---------------------------------------------------------------------------------------------
	private Context context;
	private ListAdapter listAdapter;
	private StatusAdapter statusAdapter;

	public ListControl(Context context, ListAdapter adapter) {
		this.context = context;
		this.listAdapter = adapter;
	}

	public ListControl(Context context, StatusAdapter adapter) {
		this.context = context;
		this.statusAdapter = adapter;
	}

	/**
	 * create a new list
	 * 
	 * @param listName
	 * @param isPublic
	 * @param discription
	 */
	public void createList(final String listName, final boolean isPublic,
			final String discription) {
		AsyncTask<Void, Void, UserList> task = new AsyncTask<Void, Void, UserList>() {

			@Override
			protected UserList doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					return TwitterUtils.getTwitterInstance(context)
							.createUserList(listName, isPublic, discription);
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(UserList list) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(list);

				if (list != null) {
					listAdapter.add(list);
					listAdapter.notifyDataSetChanged();
					showToast("リストを作成しました");
				} else {
					showToast("リストの作成に失敗しました");
				}
			}
		};
		task.execute();
	}

	/**
	 * load user lists
	 */
	public void loadLists(final String screenName) {
		AsyncTask<Void, Void, ResponseList<UserList>> task = new AsyncTask<Void, Void, ResponseList<UserList>>() {

			@Override
			protected ResponseList<UserList> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					return TwitterUtils.getTwitterInstance(context)
							.getUserLists(screenName);
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
				} else {
					showToast("リストの取得に失敗しました");
				}
			}
		};
		task.execute();
	}

	public void loaAllLists(final String screenName) {
		AsyncTask<Void, Void, ResponseList<UserList>> task = new AsyncTask<Void, Void, ResponseList<UserList>>() {

			@Override
			protected ResponseList<UserList> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					return TwitterUtils.getTwitterInstance(context)
							.getUserLists(screenName);
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
				} else {
					showToast("リストの取得に失敗しました");
				}
			}
		};
		task.execute();
	}
	/**
	 * update a list
	 * 
	 * @param listId
	 * @param newListName
	 * @param isPublic
	 * @param discription
	 * @param oldList
	 * @param position
	 */
	public void listUpdate(final long listId, final String newListName,
			final boolean isPublic, final String discription, final int position) {
		AsyncTask<Void, Void, UserList> task = new AsyncTask<Void, Void, UserList>() {
			UserList oldList;
			@Override
			protected UserList doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				Twitter mTwitter = TwitterUtils.getTwitterInstance(context);
				try {
					oldList = mTwitter.showUserList(listId);
					return mTwitter.updateUserList(listId, newListName,
							isPublic, discription);
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
			protected void onPostExecute(UserList newList) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(newList);

				if (newList != null) {
					listAdapter.insert(newList, position);
					listAdapter.remove(oldList);

					listAdapter.notifyDataSetChanged();
					showToast("リストを更新しました");
				} else {
					showToast("リストの更新に失敗しました");
				}
			}
		};
		task.execute();
	}

	/**
	 * destroy a list
	 * 
	 * @param listId
	 */
	public void destroyList(final long listId) {
		AsyncTask<Void, Void, UserList> task = new AsyncTask<Void, Void, UserList>() {

			@Override
			protected UserList doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				try {
					return TwitterUtils.getTwitterInstance(context)
							.destroyUserList(listId);
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
			protected void onPostExecute(UserList destroyedList) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(destroyedList);

				if (destroyedList != null) {
					listAdapter.remove(destroyedList);
					showToast("リストを削除しました");
				} else {
					showToast("リストの削除に失敗しました");
				}
			}
		};
		task.execute();
	}

	/**
	 * load listtimeline
	 * 
	 * @param id
	 */
	public void loadListTimeLine(final long id) {
		AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {

			@Override
			protected List<twitter4j.Status> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				int page = 1;

				try {
					return TwitterUtils.getTwitterInstance(context)
							.getUserListStatuses(id, new Paging(page++));
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
					for (twitter4j.Status status : result)
						statusAdapter.add(status);
				} else
					showToast("タイムラインの取得に失敗しました");
			}
		};
		task.execute();
	}

	public void loadListmembers(final long listId) {
		AsyncTask<Void, Void, PagableResponseList<User>> task = new AsyncTask<Void, Void, PagableResponseList<User>>() {

			@Override
			protected PagableResponseList<User> doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {
					long cursor = -1;
					return TwitterUtils.getTwitterInstance(context)
							.getUserListMembers(listId, cursor);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;

			}

			@Override
			protected void onPostExecute(PagableResponseList<User> result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
					for (User user : result)
						statusAdapter.add(user.getStatus());
				} else
					showToast("タイムラインの取得に失敗しました");
			}
		};

		task.execute();
	}

	public void addListMember(final long listId, final long userId) {
		AsyncTask<Void, Void, UserList> task = new AsyncTask<Void, Void, UserList>() {

			@Override
			protected UserList doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					return TwitterUtils.getTwitterInstance(context)
							.createUserListMember(listId, userId);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;

			}

			@Override
			protected void onPostExecute(UserList result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
					listAdapter.add(result);
				} else
					showToast("タイムラインの取得に失敗しました");
			}
		};

		task.execute();
	}

	public void loadList(final long listId) {
		AsyncTask<Void, Void, UserList> task = new AsyncTask<Void, Void, UserList>() {

			@Override
			protected UserList doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {
					return TwitterUtils.getTwitterInstance(context)
							.showUserList(listId);
				} catch (Exception e) {
					// TODO: handle exception
				}
				return null;

			}

			@Override
			protected void onPostExecute(UserList result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				if (result != null) {
				}

			}
		};

		task.execute();
	}

	private void showToast(String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
}
