package com.example.parakeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ListTimeLineActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(arg0);
		ListTimeLineFragment line = ListTimeLineFragment.getInstance(getIntent().getExtras().getLong("LIST_ID"));
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager
				.beginTransaction();
		transaction.add(android.R.id.content, line).commit();
	}

	
}
