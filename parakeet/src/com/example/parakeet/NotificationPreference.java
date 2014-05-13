package com.example.parakeet;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class NotificationPreference extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.notification_preference);

	}

/*	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ

		//View view = inflater.inflate(R.layout.notification_fragment, container, false);
		//return view;
	}
*/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);
	}

}
