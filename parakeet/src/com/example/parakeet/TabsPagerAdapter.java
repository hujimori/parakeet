package com.example.parakeet;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	@Override
	public android.support.v4.app.Fragment getItem(int position) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		switch (position) {
		case 0:
			HomeTimeLine homeTimeLine = HomeTimeLine.getInstance(position);
			return homeTimeLine;
			/*
			 * case 1: Mention mention = Mention.getInstance(position); return
			 * mention; default: DirectMessage directMessage =
			 * DirectMessage.getInstance(position); return directMessage;
			 */
		default:
			return null;
		}

	}

	@Override
	public int getCount() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		// return 3;
		return 1;
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
