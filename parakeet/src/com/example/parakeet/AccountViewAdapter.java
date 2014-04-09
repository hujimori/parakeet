package com.example.parakeet;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AccountViewAdapter extends ArrayAdapter<AppData> {

	private List<AppData> appList = null;
	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	private Context mContext;
	private AppData appData;
	private int backgroundDrawable;

	public AccountViewAdapter(Context context, int resourceId,
			List<AppData> appList) {

		super(context, resourceId, appList);

		this.mContext = context;
		this.appList = appList;

		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int getCount() {
		return appList.size();
	}

	@Override
	public AppData getItem(int position) {
		return appList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		appData = (AppData) getItem(position);

		if (convertView == null) {

			mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.account_view_item, null);

			mViewHolder = new ViewHolder();

			mViewHolder.smartImageView = (SmartImageView) convertView
					.findViewById(R.id.image);

			mViewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.radiobutton);
			mViewHolder.textView = (TextView) convertView
					.findViewById(R.id.textview);

			convertView.setTag(mViewHolder);
		}

		else {

			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.smartImageView.setImageUrl(appData.getUrl());

		mViewHolder.textView.setText(TwitterUtils.loadID(mContext) + "/"
				+ appData.getId());

		if (TwitterUtils.loadID(mContext).equals(appData.getId())) {

			mViewHolder.textView.setText(appData.getScreenName());
			appData.setIsChecked(true);
			mViewHolder.imageView.setImageResource(R.drawable.btn_radio_on_pressed_holo_dark);

		} else {
			appData.setIsChecked(false);
			mViewHolder.imageView.setImageResource(R.drawable.btn_radio_off_pressed_holo_dark);

		}

		/*
		 * mViewHolder.radioButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO 自動生成されたメソッド・スタブ
		 * 
		 * // for (int i = 0; i < appList.size(); i++) {
		 * //appList.get(i).setIsChecked(false); //}
		 * 
		 * Toast.makeText(mContext, appData.getScreenName(),
		 * Toast.LENGTH_SHORT).show(); //TwitterUtils.saveID(mContext,
		 * appData.getId());
		 * 
		 * // クリックした箇所のみチェックする // appData.setIsChecked(true); // アダプタ内容を即時反映する
		 * notifyDataSetChanged(); }
		 * 
		 * });
		 * 
		 * /* Gson gson = new Gson(); User user = gson.fromJson(string,
		 * User.class);
		 * 
		 * mViewHolder.smartImageView.setImageUrl(user.profileImageUrl);
		 * 
		 * mViewHolder.textView.setText(user.id + "/" +
		 * TwitterUtils.loadID(mContext));
		 * 
		 * mViewHolder.radioButton.setId(Integer.valueOf(user.id));
		 * 
		 * if (mViewHolder.radioButton.getId() == Integer.valueOf(TwitterUtils
		 * .loadID(mContext))) {
		 * 
		 * // mViewHolder.radioButton.setChecked(true); }
		 * 
		 * /* mViewHolder.radioButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO 自動生成されたメソッド・スタブ
		 * 
		 * 
		 * mViewHolder.radioButton.setChecked(true); Toast.makeText(mContext,
		 * mViewHolder.radioButton.getId(), Toast.LENGTH_SHORT).show(); // int
		 * id = mViewHolder.radioButton.getId(); //TwitterUtils.saveID(mContext,
		 * String.valueOf(id)); } });
		 */
		return convertView;
	}
}