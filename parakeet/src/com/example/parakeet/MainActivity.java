package com.example.parakeet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.R.string;
import com.loopj.android.image.SmartImageView;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MainActivity
 * 
 * @author Yoshimori
 * 
 */
public class MainActivity extends FragmentActivity {

	// ----------------------------------------------------------------------------------------------
	// Field declaration
	// ----------------------------------------------------------------------------------------------
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ActionBarDrawerToggle mToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	// ----------------------------------------------------------------------------------------------
	// Array declaration
	// ----------------------------------------------------------------------------------------------
	private int[] mImages = {

	R.drawable.ic_menu_home, R.drawable.ic_menu_search,
			R.drawable.ic_contact_picture, R.drawable.ic_sysbar_quicksettings };

	private String[] mTitles = {

	"Home", "Search", "Profile", "Setting"

	};

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

				ActionBar mActionBar = getActionBar();

				mActionBar.setDisplayHomeAsUpEnabled(true);

				mActionBar.setHomeButtonEnabled(true);

				setDrawerLayout();

				setTabs();

				setListAdapter();

			}
		}
	}

	private void setTabs() {

		PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab);
		ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
		TabsPagerAdapter mAdapter = new TabsPagerAdapter(
				getSupportFragmentManager());

		mPagerTabStrip.setTabIndicatorColor(getResources().getColor(
				R.color.main_color_light));

		mPagerTabStrip.setDrawFullUnderline(true);

		mViewPager.setAdapter(mAdapter); // Set adapter

	}

	private void setListAdapter() {

		mDrawerList = (ListView) findViewById(R.id.left_drawer_list);

		List<BindData> objects = new ArrayList<BindData>();
		for (int i = 0; i < mTitles.length; i++) {
			BindData bindData = new BindData(mTitles[i], mImages[i]);
			objects.add(bindData);
		}

		mDrawerList.setAdapter(new ImageAdapter(this, objects));

		mDrawerList.setOnItemClickListener(new DrawerListItemclickListener());
	}

	private void setDrawerLayout() {

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mTitle = mDrawerTitle = getTitle();

		String userData = TwitterUtils.loadStatus(this,
				TwitterUtils.loadID(this));
		Gson gson = new Gson();
		User user = gson.fromJson(userData, User.class);

		ImageView icon = (ImageView) findViewById(R.id.icon);

		DownLoadTask task = new DownLoadTask(icon);
		task.setIcon(user.profileImageUrl);

		// SmartImageView header = (SmartImageView) findViewById(R.id.header);
		// header.setImageUrl(user.profileBannerUrl);

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

		case R.id.menu_profile:
			showAccount();

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

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO 自動生成されたメソッド・スタブ

			selectItem(position);

		}

		private void selectItem(int position) {

			Intent intent = new Intent();

			switch (position) {

			case 0:

			case 2:

				intent.setClass(MainActivity.this, Profile.class);
				startActivity(intent);

				mDrawerList.setItemChecked(position, true);
				setTitle(mTitles[position]);
				mDrawerLayout.closeDrawer(mDrawerList);

			}
		}
	}

}
