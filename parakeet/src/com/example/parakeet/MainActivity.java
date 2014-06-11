package com.example.parakeet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity
 * 
 * @author Yoshimori
 * 
 */
public class MainActivity extends FragmentActivity {

	// ---------------------------------------------------------------------------------------------
	// Field declaration
	// ---------------------------------------------------------------------------------------------
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private Gson gson = new Gson();
	private User user;
	private DrawerAdapter mAdapter;

	private static final String USER = "user";

	private final int buttonImage = R.drawable.btn_radio_off_holo_dark;
	// ---------------------------------------------------------------------------------------------
	// Array declaration
	// ---------------------------------------------------------------------------------------------
	private final int[] mImages = {

	R.drawable.ic_contact_picture, R.drawable.ic_menu_search,
			R.drawable.ic_sysbar_quicksettings };

	private final String[] mTitles = { "アカウント", "検索", "設定" };

	/**
	 * Called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (TwitterUtils.loadID(this) == null) {
			Intent intent = new Intent(this, TwitterOAuthActivity.class);
			startActivity(intent);
			finish();
		} else {
			if (savedInstanceState == null) {
				setContentView(R.layout.activity_main);
			}
			ActionBar mActionBar = getActionBar();

			mActionBar.setDisplayHomeAsUpEnabled(true);

			mActionBar.setHomeButtonEnabled(true);

			setPager();

			setDrawerLayout();

			setListAdapter();

		}
	}

	/**
	 * Set tabs
	 */
	private void setPager() {

		PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab);
		ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
		TabsPagerAdapter mAdapter = new TabsPagerAdapter(
				getSupportFragmentManager());

		mPagerTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.main_color_light));

		mPagerTabStrip.setDrawFullUnderline(true);

		mViewPager.setAdapter(mAdapter); // Set adapter

	}

	/**
	 * 
	 * Set list adapter
	 */
	private void setListAdapter() {

		mDrawerList = (ListView) findViewById(R.id.left_drawer_list);

		List<BindData> objects = new ArrayList<BindData>();

		SharedPreferences sharedPreferences = this.getSharedPreferences(USER,
				Context.MODE_PRIVATE);

		Map<String, ?> map = sharedPreferences.getAll();

		BindData bindData;
		/*
		 * for (String key : map.keySet()) { user =
		 * gson.fromJson(sharedPreferences.getString(key, null), User.class);
		 * bindData = new BindData(user.screenName, buttonImage, false,
		 * user.id); objects.add(bindData);
		 * 
		 * }
		 */
		for (int i = 0; i < mTitles.length; i++) {
			bindData = new BindData(mTitles[i], mImages[i]);
			objects.add(bindData);
		}

		mAdapter = new DrawerAdapter(this, objects);

		mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new DrawerListItemclickListener());
	}

	/**
	 * Set drawer
	 */
	private void setDrawerLayout() {

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mTitle = mDrawerTitle = getTitle();

		ClipImageView icon = (ClipImageView) findViewById(R.id.icon);
		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Profile.class);
				startActivity(intent);

			}
		});
		BitmapControl bitmapControl = new BitmapControl();
		String url = TwitterUtils.loadUserIcon(this, TwitterUtils.loadID(this));
		bitmapControl.roundBitmap(this, url, icon);
		TextView textView = (TextView) findViewById(R.id.text);
		user = gson.fromJson(TwitterUtils.loadUser(this), User.class);
		textView.setText(user.name);

		mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.open, R.string.close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO 自動生成されたメソッド・スタブ
				super.onDrawerClosed(drawerView);

				setTitle(mTitle);

			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO 自動生成されたメソッド・スタブ
				super.onDrawerOpened(drawerView);

				setTitle(mDrawerTitle);

			}

		};

	

		mDrawerLayout.setDrawerListener(mToggle);
	}

	// @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event

		switch (item.getItemId()) {
		case R.id.menu_edit:
			showTweetDialog();
			break;

		case R.id.menu_serach:
			showAccount();
			break;
		}
		if (mToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Called when the menu is created
	 * */
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);

		return true;
	}

	private void showTweetDialog() {

		TweetView tweetView = TweetView.newInstance();
		tweetView.show(getSupportFragmentManager(), "tweetView");
	}

	private void showAccount() {

		AccountView accountView = new AccountView();
		accountView.show(getSupportFragmentManager(), "accountView");

	}

	class DrawerListItemclickListener implements OnItemClickListener {

		// private int pos;

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO 自動生成されたメソッド・スタブ

			selectItem(position, id);

		}

		private void selectItem(int position, long id) {

			Intent intent = new Intent();

			switch (position) {

			case 0:
				intent.setClass(MainActivity.this, Profile.class);
				startActivity(intent);
				break;

			case 1:
				intent.setClass(MainActivity.this, Prefrence.class);
				startActivity(intent);
				break;

			}
		}
	}

}
