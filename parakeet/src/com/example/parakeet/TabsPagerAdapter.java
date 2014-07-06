package com.example.parakeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	public TabsPagerAdapter(FragmentManager fm, Context cn) {
		super(fm);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		this.context = cn;
	}

	@Override
	public android.support.v4.app.Fragment getItem(int position) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		SharedPreferences pref;
		switch (position) {
		case 0:
			pref = context
					.getSharedPreferences("content", Context.MODE_PRIVATE);

			if (pref.getBoolean("HomeTimeLine", true)) {
				HomeTimeLine homeTimeLine = HomeTimeLine.getInstance(position);
				return homeTimeLine;
			}

		case 1:
			pref = context
					.getSharedPreferences("content", Context.MODE_PRIVATE);

			if (pref.getBoolean("Mention", true)) {
				Mention mention = Mention.getInstance(position);
				return mention;
			}

		case 2:
			pref = context.getSharedPreferences("LIST", Context.MODE_PRIVATE);
			
			if (pref.getBoolean("LIST_BOOL", false)) {
				ListTimeLineFragment listTimeLineFragment = ListTimeLineFragment
						.getInstance(pref.getLong("LIST_ID", 0));
				Log.d("LIST_ID", String.valueOf(pref.getLong("LIST_ID", 0)));
				return listTimeLineFragment;
			}
			/*
			 * case 3: pref case 4:
			 */

			/*
			 * case 1: Mention mention = Mention.getInstance(position); return
			 * mention; default: DirectMessage directMessage = DirectMessage
			 * .getInstance(position); return directMessage;
			 */


		}
		return null;

	}

	@Override
	public int getCount() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		// return 3;
		return 3;
	}

	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "TimeLine";
		case 1:
			return "Mentions";
		case 2:
			return "DirectMessage";
		default:
			return null;
		}
	}

}
