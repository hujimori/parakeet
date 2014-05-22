package com.example.parakeet;

import java.util.List;


import twitter4j.User;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Yoshimori
 *
 */
public class FriendsAdapter extends ArrayAdapter<User> {

	/**
	 * 
	 * @author Yoshimori
	 *
	 */
	private class ViewHolder {
		TextView screenName;
		ImageView icon;
	}
	
	// ---------------------------------------------------------------------------------------------
	// instance field 
	// ---------------------------------------------------------------------------------------------
	private LayoutInflater inflater;
	private ViewHolder holder;
	private Context context;

	/**
	 * 
	 * @param context
	 */
	public FriendsAdapter(Context context) {
		super(context, 0);
		// TODO 自動生成されたコンストラクター・スタブ
		this.context = context;
		inflater = (LayoutInflater) this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ
		
		User user = getItem(position);
		
		if (convertView == null) {

		
		convertView = inflater.inflate(R.layout.friends_adapter_item, null);

		holder = new ViewHolder();

		holder.screenName = (TextView) convertView
				.findViewById(R.id.screen_name);
		
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		
		convertView.setTag(holder);

	
	}
	
		else {
		
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.screenName.setText("@" + user.getScreenName()
				+ "/" + user.getName());
		holder.icon.setTag(user.getBiggerProfileImageURL());
		
		DownLoadTask task1 = new DownLoadTask(holder.icon);
		
		task1.setProfileIcon(user.getBiggerProfileImageURL());
	
		return convertView;
	}

}
