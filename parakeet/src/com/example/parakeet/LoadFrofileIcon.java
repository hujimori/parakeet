package com.example.parakeet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadFrofileIcon extends AsyncTask<Void, Void, Void> {

	private String urls;
	private Context mContext;
	private Twitter mTwitter;
	private Long id;

	public LoadFrofileIcon(Context mContext) {

		this.mContext = mContext;

	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO 自動生成されたメソッド・スタブ

		try {

		//	mTwitter = TwitterUtils.getTwitterInstance(mContext);

			id = mTwitter.getId();

			urls = mTwitter.verifyCredentials().getBiggerProfileImageURL();

			URL url = new URL(urls);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			InputStream is = connection.getInputStream();

			Bitmap bitmap = BitmapFactory.decodeStream(is);

			BitmapControl.saveProfileIcon(mContext, "icon1", bitmap);

		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO 自動生成されたメソッド・スタブ
		super.onPostExecute(result);
	}

}
