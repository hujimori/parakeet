package com.example.parakeet;

import java.util.List;

import twitter4j.UserList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<UserList> {

	
	private class ViewHolder {
		
		TextView listName;
		
	}
	
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;
	
	public ListAdapter(Context context) {
		super(context, 0);
		// TODO 自動生成されたコンストラクター・スタブ
		
		this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ
		
		UserList mList = getItem(position);
		
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.list_adapter, null);
			
			holder = new ViewHolder();
			
			holder.listName = (TextView)convertView.findViewById(R.id.list_title);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.listName.setText(mList.getName());

		return convertView;
	}

}
