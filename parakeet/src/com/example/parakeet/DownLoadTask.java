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
	private ImageView imageView;
	private String tag;

	/**
	 * 
	 * @param imageView
	 */
	public DownLoadTask(ImageView imageView) {

		this.imageView = imageView;
		this.tag = imageView.getTag().toString();

	}

	/**
	 * 
	 * @param urls
	 */
	public void setThumn(final String urls, final LinearLayout ll) {
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

				if (tag.equals(imageView.getTag())) {

					if (result != null) {

						imageView.setImageBitmap(result);
						ll.setVisibility(View.VISIBLE);
						ll.addView(imageView);
					}
				}

			}

		};
		asyncTask.execute();
	}

	public void setProfileIcon(final String urls) {
		AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					Bitmap image = ImageCache.getImage(urls);

					tag = imageView.getTag().toString();

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

				if (tag.equals(imageView.getTag())) {

					if (result != null) {

						imageView.setImageBitmap(result);
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

					imageView.setImageBitmap(result);
					// layout.addView(imageView);
				}
			}

		};
		asyncTask.execute();
	}
}
