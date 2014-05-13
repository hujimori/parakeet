package com.example.parakeet;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Prefrence extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		// TODO 自動生成されたメソッド・スタブ
		super.onBuildHeaders(target);
		loadHeadersFromResource(R.xml.preference_fragment, target);
	}

}
