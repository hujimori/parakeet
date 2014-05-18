package com.example.parakeet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.Toast;

public class BitmapControl {

	
	// ---------------------------------------------------------------------------------------------
	// instance field
	// ---------------------------------------------------------------------------------------------
	private int srcWidth = 0;
	private int srcHeight = 0;
	private int baseSize = 128;
	private int thumnSize = 90;
	private Matrix matrix;
	private Bitmap bitmap;

	
	public BitmapControl() {
		
	}
	
	/**
	 * decode bitmap from url
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap dedcodeBitmmapUrl(URL url) {

		try {

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inJustDecodeBounds = true;

			InputStream is = url.openStream();

			BitmapFactory.decodeStream(is, null, options);

			srcWidth = options.outWidth;
			srcHeight = options.outHeight;

			if (srcWidth < srcHeight) {
				options.inSampleSize = srcHeight / baseSize;
			} else {
				options.inSampleSize = srcWidth / baseSize;
			}

			options.inJustDecodeBounds = false;

			InputStream ris = url.openStream();

			bitmap = BitmapFactory.decodeStream(ris, null, options);

			Bitmap thumnail = makeBitmap(bitmap, thumnSize);

			return thumnail;

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();

			return null;
		}

	}

	/**
	 * decode bitmap from filepath
	 * @param filePath
	 * @return
	 */
	  public Bitmap decodeBitmap(String filePath) {
	  
	  BitmapFactory.Options options = new BitmapFactory.Options();
	  
	  options.inJustDecodeBounds = true;
	  
	  BitmapFactory.decodeFile(filePath, options);
	  
	  srcWidth = options.outWidth; srcHeight = options.outHeight;
	  
	  if (srcWidth < srcHeight) { options.inSampleSize = srcHeight / baseSize;
	  } else { options.inSampleSize = srcWidth / baseSize; }
	  
	  options.inJustDecodeBounds = false;
	  
	  bitmap = BitmapFactory.decodeFile(filePath, options);
	  
	  Bitmap tmp = makeBitmap(bitmap, baseSize);
	  
	  return tmp; 
	  }
	 
	
	/**
	 * make bitmap
	 * @param src
	 * @param size
	 * @return
	 */
	private Bitmap makeBitmap(Bitmap src, int size) {

		matrix = new Matrix();

		int w = src.getWidth();
		int h = src.getHeight();

		float scale = 1.0f;

		Bitmap dest = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(dest);

		int rw = (int) ((float) w * scale);
		int rh = (int) ((float) h * scale);

		int offsetX = (size / 2) - (rw / 2);
		int offsetY = (size / 2) - (rh / 2);

		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		matrix.postTranslate(offsetX, offsetY);
		canvas.drawBitmap(src, matrix, paint);

		return dest;
	}

	/**
	 * make round bitmap
	 * @param context
	 * @param imageUrl
	 * @param clipImageView
	 */
	public void roundBitmap(final Context context, final String imageUrl,
			final ClipImageView clipImageView) {

		AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {
					// create bitmap from url
					URL url = new URL(imageUrl);
					InputStream is = url.openStream();
					Bitmap image = BitmapFactory.decodeStream(is);
					return image;
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;

			}

			@Override
			protected void onPostExecute(Bitmap image) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(image);

				if (image != null) {

					clipImageView.setImageBitmap(image);
					

				} else {
					Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
				}
			}

		};
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}

	public static void saveProfileIcon(Context mContext, String name, Bitmap bmp) {

		try {

			OutputStream os = mContext.openFileOutput(name,
					Context.MODE_PRIVATE);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static Bitmap loadProfileIcon(String name, Context mContext) {

		InputStream is = null;
		Bitmap res = null;

		try {

			is = mContext.openFileInput(name);
			res = BitmapFactory.decodeStream(is);
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static boolean hasProfileIcon(Context mContext) {

		return loadProfileIcon("icon1", mContext) != null;
	}

}
