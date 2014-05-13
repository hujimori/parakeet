package com.example.parakeet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class PrerferenceFragment1 extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preference_child_fragment);
	}

	
		

}
