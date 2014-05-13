package com.example.parakeet;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Profile extends FragmentActivity {

	private final static String USER = "user";

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

		Gson gson = new Gson();
		User user = gson.fromJson(TwitterUtils.loadUser(this), User.class);

		String[] strings = { "TWEETS", "FOLLOW", "FOLLOWER", "FAVORITE", "LIST" };

		String[] counts = { user.statuses_count, user.friends_count,
				user.followers_count, user.favorites_count, "" };

		List<BindData> objects = new ArrayList<BindData>();
		for (int i = 0; i < strings.length; i++) {

			objects.add(new BindData(strings[i], counts[i]));

		}

		ProfileAdapter mAdapter = new ProfileAdapter(this, objects);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new ListItemOnClickListener());

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
			User user = gson.fromJson(TwitterUtils.loadUser(getActivity()),
					User.class);
			ClipImageView icon = (ClipImageView) view.findViewById(R.id.icon);

			BitmapControl bitmapControl = new BitmapControl();
			TextView screenName = (TextView) view
					.findViewById(R.id.screen_name);
			screenName.setText(user.screenName);
			// TextView name = (TextView) view.findViewById(R.id.name);
			// name.setText(user.name);
			bitmapControl.roundBitmap(
					getActivity(),
					TwitterUtils.loadUserIcon(getActivity(),
							TwitterUtils.loadID(getActivity())), icon);

			return view;
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
			return inflater.inflate(R.layout.fragment2, null);
		}

	}

}
