package com.example.parakeet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager.LayoutParams;

public class TweetActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(bundle);
		setContentView(R.layout.activity_tweet);
		
		  this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  

		
	}
	
	

}
