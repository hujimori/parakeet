package com.example.parakeet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*タブ生成
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public Fragment getItem(int position) {
		// TODO 自動生成されたメソッド・スタブ
		switch (position) {
		case 0:
			HomeTimeLine homeTimeLine = HomeTimeLine.getInstance(position);
			return homeTimeLine;

		case 1:
			Mention mention = Mention.getInstance(position);
			return mention;
		default:
			DirectMessage directMessage = DirectMessage.getInstance(position);
			return directMessage;
		}

	}

	@Override
	public int getCount() {
		// TODO 自動生成されたメソッド・スタブ
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
