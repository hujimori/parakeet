package com.example.parakeet;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * MainActivity
 * @author Yoshimori
 *
 */
public class MainActivity extends FragmentActivity {

	//----------------------------------------------------------------------------------------------
	// Field declaration
	//----------------------------------------------------------------------------------------------
	private ViewPager mViewPager;
	private TabsPagerAdapter mTabsPagerAdapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (!TwitterUtils.hasAccessToken(this)) { // If you have not OAuth

			Intent intent = new Intent(this, TwitterOAuthActivity.class);
			startActivity(intent);
			finish();

		} else {
			if (savedInstanceState == null) {

				setContentView(R.layout.main);

				ActionBar actionBar = getActionBar();
				actionBar.setDisplayHomeAsUpEnabled(true);

				mViewPager = (ViewPager) findViewById(R.id.pager);
				mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager()); // Create TabsPagerAdapter instance
				mViewPager.setAdapter(mTabsPagerAdapter); // Set adapter

			}
		}
	}

	/**
	 * Called when the menu is created
	 * */
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);

		return true;
	}

	/**
	 * Called when the menu item is selected
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (android.R.id.home == item.getItemId()) {

			ImageView view = (ImageView) findViewById(android.R.id.home);
			PopupMenu menu = new PopupMenu(this, view);

			menu.inflate(R.menu.popup_menu);
			menu.show();
			menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO 自動生成されたメソッド・スタブ
					switch (item.getItemId()) {

					case R.id.profile:
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, Profile.class);
						startActivity(intent);

					default:
						break;
					}

					return false;
				}
			});
		}

		return super.onMenuItemSelected(featureId, item);

	}

	/**
	 * Called when the option item is selected
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_edit:
			showTweetDialog();
		}

		return super.onOptionsItemSelected(item);

	}

	private void showTweetDialog() {

		TweetView tweetDialog = TweetView.newInstance();
		tweetDialog.show(getSupportFragmentManager(), "tweetdialog");
	}

}