import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.parakeet.R;
import com.example.parakeet.R.id;

import twitter4j.Status;
import twitter4j.media.ImageUpload;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseExpandableListAdapter {

	private class ParentViewHolder {
		TextView screenName;
		TextView text;
		TextView via;
		DateFormat dateFormat;
		ImageView icon;
		ImageView[] thumnail = new ImageView[6];
	}

	private class ChildViewHolder {
		ImageView retweet;
		ImageView reply;
		ImageView favorite;
		ImageView delete;
	}

	private ParentViewHolder pHolder;
	private ChildViewHolder cHolder;
	private LayoutInflater mInflater;
	private CharSequence charSequence;
	private Context context;
	private Status status;
	private LayoutInflater inflater;

	public TestAdapter(Status status, Context mContext) {
		this.status = status;
		this.inflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_list_item, null);
			
			cHolder = new ChildViewHolder();
			
			cHolder.retweet =(ImageView) convertView.findViewById(R.id.retweet);
			
			cHolder.reply = (ImageView) convertView.findViewById(R.id.reply);
			
			cHolder.favorite = (ImageView) convertView.findViewById(R.id.delete);
		
			convertView.setTag(cHolder);
		}
		else{
			cHolder = (ChildViewHolder) convertView.getTag();
		}
		
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
