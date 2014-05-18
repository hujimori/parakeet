package com.example.parakeet;

import java.text.SimpleDateFormat;

import twitter4j.DirectMessage;
import twitter4j.User;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.loopj.android.image.SmartImageView;

/**
 *
 * @author Yoshimori
 *
 */
public class DirectMessageAdapter extends ArrayAdapter<DirectMessage> {

	//----------------------------------------------------------------------------------------------
	// instance field
	//----------------------------------------------------------------------------------------------
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	private User user;

	/**
	 *
	 * @param context
	 */
	public DirectMessageAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		// TODO 自動生成されたメソッド・スタブ
		inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @return convertview
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ

		DirectMessage dm = getItem(position); // Get object

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.list_item_tweet, null);

			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			viewHolder.screenName = (TextView) convertView.findViewById(R.id.screen_name);

			viewHolder.text = (TextView) convertView.findViewById(R.id.text);

			viewHolder.tweetTime = (TextView) convertView.findViewById(R.id.tweetTime);

			viewHolder.date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		//	viewHolder.icon = (SmartImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();

		}

		viewHolder.screenName.setText("@" + dm.getSenderScreenName());

		user = dm.getSender(); // Get sender's user object

		viewHolder.name.setText(user.getName());

		viewHolder.text.setText(dm.getText());

		viewHolder.tweetTime.setText(viewHolder.date.format(dm.getCreatedAt()));

	//	viewHolder.icon.setImageUrl(user.getProfileImageURL());

		return convertView;

	}

}
