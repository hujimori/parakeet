package com.example.parakeet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @author Yoshimori
 * 
 */
public class DownLoadTask {

	// ---------------------------------------------------------------------------------------------
	// instance field
	// ---------------------------------------------------------------------------------------------
	private ImageView image;
	private String tag;

	/**
	 * 
	 * @param imageView
	 */

	public DownLoadTask(ImageView _image) {
		this.image = _image;
		tag = this.image.getTag().toString();
	}

	/**
	 * 
	 * @param urls
	 */
	public void setThumnail(final String urls) {
		AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					// キャッシュより画像データを取得
					Bitmap image = ImageCache.getImage(urls);

					// キャッシュに画像がないならwebから画像取得

					if (image == null) {

						URL url = new URL(urls);

						// URLから画像をデコード
						BitmapControl control = new BitmapControl();
						image = control.dedcodeBitmmapUrl(url);

						// キャッシュに保存
						ImageCache.setImage(urls, image);

					}

					return image;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Bitmap result) {
				// TODO 自動生成されたメソッド・スタブ

				// tagが同じものか判定
				if (tag.equals(image.getTag())) {

					if (result != null) {

						// imageをLinearLayoutにセット
						image.setImageBitmap(result);
						image.setVisibility(View.VISIBLE);

					}

				}

			}

		};
		task.execute();
	}

	public void setProfileIcon(final String urls) {
		AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					Bitmap image = ImageCache.getImage(urls);

					if (image == null) {

						URL url = new URL(urls);

						BitmapControl control = new BitmapControl();

						image = control.dedcodeBitmmapUrl(url);

						ImageCache.setImage(urls, image);

					}

					return image;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Bitmap result) {
				// TODO 自動生成されたメソッド・スタブ

				if (tag.equals(image.getTag())) {

					if (result != null) {

						image.setImageBitmap(result);
						image.setVisibility(View.VISIBLE);

					}
				}

			}

		};
		asyncTask.execute();
	}

	/**
	 * 
	 * @param iconUrl
	 */
	public void setIcon(final String iconUrl) {
		AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					URL url = new URL(iconUrl);

					BitmapControl control = new BitmapControl();

					Bitmap image = control.dedcodeBitmmapUrl(url);

					return image;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Bitmap result) {
				// TODO 自動生成されたメソッド・スタブ

				if (result != null) {

					image.setImageBitmap(result);
					// layout.addView(imageView);
				}
			}

		};
		asyncTask.execute();
	}
}
