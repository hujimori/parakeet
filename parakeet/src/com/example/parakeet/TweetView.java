package com.example.parakeet;

import twitter4j.Twitter;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//�_�C�A���O����
public class TweetView extends DialogFragment {

	//変数宣言
	private Twitter mTwitter;
	private EditText editText;
	private TwitterUpdate update;
	private Button button;
	private View content;
	private ImageButton imageButton;
	private String path;

	//定数宣言
	private final static int REQUEST_CODE = 1;

	/**
	 *
	 * ファクトリーメソッド
	 */

	public static TweetView newInstance() {
		TweetView tweetView = new TweetView();

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
	public Dialog onCreateDialog(Bundle b)
	{
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
	public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b)
	{
		View content = i.inflate(R.layout.tweet_view, null);

		button = (Button) content.findViewById(R.id.button);
		editText = (EditText) content.findViewById(R.id.edit);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				mTwitter = TwitterUtils.getTwitterInstance(getActivity());

				update = new TwitterUpdate(mTwitter, getActivity());

				if(path == null) {
				update.tweet(editText);
				}
				else {
					update.media(editText, path);
					path = null;
				}
				dismiss();

			}
		});

		imageButton = (ImageButton) content.findViewById(R.id.gal);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/");
				startActivityForResult(intent, REQUEST_CODE);
			}
		});

		return content;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動生成されたメソッド・スタブ

		String str = data.getDataString();
		if (str != null) {
			Uri uri = data.getData();
			Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
			cursor.moveToPosition(0);
			path = cursor.getString(1);


			cursor.close();
		}

		if (path != null) {
			Toast.makeText(getActivity(), path,Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getActivity(), "nullだ", Toast.LENGTH_SHORT).show();
		}

		}
	}

