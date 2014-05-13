package com.example.parakeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ShowTweetActivity extends FragmentActivity {

	private final String KEY = "key";

	@Override
	protected void onCreate(Bundle mBundle) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(mBundle);

		Intent mIntent = getIntent();
		int position = mIntent.getIntExtra(KEY, 0);

		switch (position) {

		case 0:
			ShowTweetsFragment stFragment = new ShowTweetsFragment();
			transactionFragment(stFragment);
			break;
			
		case 1:
			ShowFollowFragment sfFragment = new ShowFollowFragment();
			transactionFragment(sfFragment);
			break;
			
		case 2:
			ShowFollowerFragment sfrFragment = new ShowFollowerFragment();
			transactionFragment(sfrFragment);
			break;
			
		case 3:
			ShowFavoritesFragment sfvFragment = new ShowFavoritesFragment();
			transactionFragment(sfvFragment);
			break;
			
		case 4:
			ShowListFragment listFragment = new ShowListFragment();
			transactionFragment(listFragment);
			break;
		}

	}
	
	private void transactionFragment(Fragment fragment) {
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(android.R.id.content, fragment).commit();
	}

}
