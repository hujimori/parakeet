package com.example.parakeet;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Profile extends FragmentActivity {

	private final static String USER = "user";
	private Twitter twitter;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(bundle);

		setContentView(R.layout.profile);
		setHeader();
		setListAdapter();
		setPager();

	}

	private void setHeader() {

		SmartImageView header = (SmartImageView) findViewById(R.id.header);
		header.setImageUrl(TwitterUtils.loadBanner(this,
				TwitterUtils.loadID(this)));

	}

	private void setListAdapter() {


		ListView listView = (ListView) findViewById(R.id.list);
		getStatusCount(listView, this);
		listView.setOnItemClickListener(new ListItemOnClickListener());

	}
	
	private void getStatusCount(final ListView lv, final Context cn) {
		AsyncTask<Void, Void, int[]> task = new AsyncTask<Void, Void, int[]>() {

			@Override
			protected int[] doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ
				twitter = TwitterUtils.getTwitterInstance(cn);
				try {
					User user = twitter.verifyCredentials();
					int[] counts = { user.getStatusesCount(),
							user.getFriendsCount(), user.getFollowersCount(),
							 user.getFavouritesCount(),0 };

					return counts;
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(int[] result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);
				String[] strings = { "TWEETS", "FOLLOW", "FOLLOWER",
							"FAVOURITE", "LIST" };

					List<ProfileBindData> objects = new ArrayList<ProfileBindData>();
					for (int i = 0; i < strings.length; i++) {
						objects.add(new ProfileBindData(strings[i], result[i]));
						Log.d("count", String.valueOf(result[i]));
					}

					ProfileAdapter mAdapter = new ProfileAdapter(cn,
							objects);
					lv.setAdapter(mAdapter);
			}

		};
		task.execute();
		
	}


	private void setPager() {

		ProfilePagerAdapter mPagerAdapter = new ProfilePagerAdapter(
				getSupportFragmentManager());
		ViewPager mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mPagerAdapter);

	}

	private class ListItemOnClickListener implements OnItemClickListener {

		private final String KEY = "key";

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO 自動生成されたメソッド・スタブ

			Intent mIntent = new Intent();
			mIntent.setClass(getApplication(), ShowTweetActivity.class);

			switch (position) {

			case 0:
				mIntent.putExtra(KEY, position);
				startActivity(mIntent);
				break;

			case 1:
				mIntent.putExtra(KEY, position);
				startActivity(mIntent);
				break;

			case 2:
				mIntent.putExtra(KEY, position);
				startActivity(mIntent);
				break;

			case 3:
				mIntent.putExtra(KEY, position);
				startActivity(mIntent);
				break;

			case 4:
				mIntent.putExtra(KEY, position);
				startActivity(mIntent);
				break;
			}

		}

	}


	private class ProfilePagerAdapter extends FragmentPagerAdapter {

		public ProfilePagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public Fragment getItem(int position) {
			// TODO 自動生成されたメソッド・スタブ

			switch (position) {

			case 0:
				return Fragment1.newInstance(position);
			default:
				return Fragmnet2.newInstance(position);

			}
			
		}

		@Override
		public int getCount() {
			// TODO 自動生成されたメソッド・スタブ
			return 2;
		}

	
	}

	public static class Fragment1 extends Fragment {

		public static final String ARG_SECTION_NUMBER = "position_number"; // fragment

		public static Fragment newInstance(int position) {
			Fragment1 fragment1 = new Fragment1();
			Bundle bundle = new Bundle();
			bundle.putInt(ARG_SECTION_NUMBER, position);
			fragment1.setArguments(bundle);
			return fragment1;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ

			View view = inflater.inflate(R.layout.fragment1, null);

			Gson gson = new Gson();
			ClipImageView icon = (ClipImageView) view.findViewById(R.id.icon);
			BitmapControl bitmapControl = new BitmapControl();
			TextView screenName = (TextView) view
					.findViewById(R.id.screen_name);
			TextView name = (TextView) view.findViewById(R.id.name);
		//	getProfile(screenName, name);
			bitmapControl.roundBitmap(
					getActivity(),
					TwitterUtils.loadUserIcon(getActivity(),
							TwitterUtils.loadID(getActivity())), icon);

			return view;
		}
		
		private void getProfile(final TextView tv1, final TextView tv2) {
			AsyncTask<Void, Void, User> task = new AsyncTask<Void, Void, User>() {

				@Override
				protected User doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ
					try {
						return TwitterUtils.getTwitterInstance(getActivity()).verifyCredentials();
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						return null;
					}
				}

				@Override
				protected void onPostExecute(User user) {
					// TODO 自動生成されたメソッド・スタブ
					super.onPostExecute(user);
					
					if (user != null) {
						//tv1.setText(user.getScreenName());
						//tv2.setText(user.getName());
					}
				}
				
				
			};
			task.execute();
			
		}
	}
	
	public static class Fragmnet2 extends Fragment {

		public static final String ARG_SECTION_NUMBER = "position_number"; // fragment

		public static Fragment newInstance(int position) {

			Fragmnet2 fragment2 = new Fragmnet2();
			Bundle bundle = new Bundle();
			bundle.putInt(ARG_SECTION_NUMBER, position);
			fragment2.setArguments(bundle);
			return fragment2;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ
			View view = inflater.inflate(R.layout.fragment2, null);

		//	Gson gson = new Gson();
//			User user = gson.fromJson(TwitterUtils.loadUser(getActivity()),
	//				User.class);

//			TextView profile = (TextView) view.findViewById(R.id.profile);
		//	profile.setText(user.description);

//			TextView location = (TextView) view.findViewById(R.id.location);
		//	location.setText(user.location);

//			TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
		//	createdAt.setText(user.createdAt);

			return view;
		}

	}
	
	
}


