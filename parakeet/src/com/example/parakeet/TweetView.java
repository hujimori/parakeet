package com.example.parakeet;

import twitter4j.Twitter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//�_�C�A���O����
public class TweetView extends DialogFragment {

	// 変数宣言
	private Twitter mTwitter;
	private EditText editText;
	private TwitterUpdate update;
	private Button button;
	private View view;
	private ImageButton imageButton;
	private String filePath;
	private ImageView imageView;
	private ImageView icon;
	private LinearLayout layout;
	private LayoutInflater inflater;
	private TextView textCount;
	

	private static int MODE;
	
	public static long STATUS_ID;
	public static int TWEET_MODE = 0;
	public static int REPLY_MODE = 1;
	public static int RETWEET_MODE = 2;
	
	// 定数宣言
	private final static int REQUEST_CODE = 1;

	/**
	 * 
	 * ファクトリーメソッド
	 */

	public static TweetView newInstance() {
		TweetView tweetView = new TweetView();
		return tweetView;
	}
	
	public static TweetView newInstance(int mode) {
		TweetView tweetView = new TweetView();
		MODE = mode;
		return tweetView;
	}
	
	public static TweetView newInstance(int mode, long id) {
		TweetView tweetView = new TweetView();
		MODE = mode;
		STATUS_ID = id;
		return tweetView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_MyDialog);
	}

	/**
	 * ダイアログコンテナを生成する。
	 */
	@Override
	public Dialog onCreateDialog(Bundle b) {
		Dialog dialog = super.onCreateDialog(b);

		// タイトル
		dialog.setTitle("My Custom Dialog");
		// ダイアログ外タップで消えないように設定
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}

	/**
	 * UIを生成する。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {

		this.inflater = inflater;

		view = this.inflater.inflate(R.layout.tweet_view, null);

		imageView = (ImageView) view.findViewById(R.id.image);
		imageView.setVisibility(View.GONE);

		//icon = (ImageView) view.findViewById(R.id.icon);
		//icon.setImageBitmap(BitmapControl.loadProfileIcon("icon1",
			//	getActivity()));

		button = (Button) view.findViewById(R.id.button);

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(140);

		textCount = (TextView) view.findViewById(R.id.text);

		editText = (EditText) view.findViewById(R.id.edit);
		editText.setFilters(filters);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO 自動生成されたメソッド・スタブ

				int textLength = s.length();

				textCount.setText(Integer.toString(140 - textLength));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO 自動生成されたメソッド・スタブ

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO 自動生成されたメソッド・スタブ

			}
		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				mTwitter = TwitterUtils.getTwitterInstance(getActivity());

				update = new TwitterUpdate(mTwitter, getActivity());

				if (filePath == null) {
					
					switch (MODE) {
					case 1:
						update.reply(editText.getText().toString(), STATUS_ID);
					
					case 2:
						update.retweet(STATUS_ID);
					
					default:
						update.tweet(editText.getText().toString());

					}
				} else {
					update.uploadTwitpic(editText.getText().toString(), filePath);
					filePath = null;
				}
				dismiss();

			}
		});

		imageButton = (ImageButton) view.findViewById(R.id.gal);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ

				intent();
			}

		});

		return view;

	}

	public void intent() {

		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/");
		startActivityForResult(intent, REQUEST_CODE);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ

		if (requestCode == REQUEST_CODE) {

			if (resultCode == Activity.RESULT_OK) {

				String[] columns = { MediaColumns.DATA };
				Cursor cur = getActivity().getContentResolver().query(
						data.getData(), columns, null, null, null);
				cur.moveToNext();

				filePath = cur.getString(0);
				if (filePath == null) {
					Toast.makeText(getActivity(), "画像を読み込めませんでした",
							Toast.LENGTH_SHORT).show();
					return;
				}

				BitmapControl control = new BitmapControl();

				Bitmap bitmap = control.decodeBitmap(filePath);

				// layout = (LinearLayout) view.findViewById(R.id.parent);

				// imageView = (ImageView) inflater.inflate(R.layout.imageview,
				// null);

				// layout.addView(imageView);

				imageView.setVisibility(View.VISIBLE);
				imageView.setImageBitmap(bitmap);
			}
		}

	}
}
