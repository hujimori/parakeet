package com.example.parakeet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

/**
 *
 * @author Yoshimori
 *
 */
public class DownLoadTask extends AsyncTask<String, Void, Drawable> {

	//----------------------------------------------------------------------------------------------
	// Field declaration
	private ImageView imageView;
	private String tag;

	/**
	 *
	 * @param imageView
	 */
	public DownLoadTask(ImageView imageView) {

		this.imageView = imageView;
		this.tag = imageView.getTag().toString(); // substitution tag set ImageView for fieled

	}

	@Override
	protected Drawable doInBackground(String... urls) {
		// TODO 自動生成されたメソッド・スタブ

		try {

			Drawable image = ImageCache.getImage(urls[0]); //Creat image from cache

			if (image == null) {

				URL url = new URL(urls[0]);
				image = Drawable.createFromStream((InputStream) url.getContent(), "");
				ImageCache.setImage(urls[0], image);

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

	protected void onPostExecute(Drawable result) {
		// TODO 自動生成されたメソッド・スタブ

		// メンバのタグと imageView にセットしたタグが一致すれば
		// 画像をセットする
		if (this.tag.equals(this.imageView.getTag())) {

			if (result != null) {

				this.imageView.setVisibility(View.VISIBLE);
				this.imageView.setImageDrawable(result);

			}
		}

	}

}
