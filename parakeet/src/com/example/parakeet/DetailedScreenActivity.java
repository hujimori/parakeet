package com.example.parakeet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * DetailedScreenActivity
 * 
 * @author Yoshimori
 * 
 */
public class DetailedScreenActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_screen);

		if (savedInstanceState == null) {
			DetailedScreenFragment detailedScreenFragment = new DetailedScreenFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("serial",
					getIntent().getSerializableExtra("serial"));
			// bundle.putString(key, value)
			// detailedScreenFragment.setArguments(getIntent().getExtras());
			detailedScreenFragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content, detailedScreenFragment).commit();
		}

	}

}
