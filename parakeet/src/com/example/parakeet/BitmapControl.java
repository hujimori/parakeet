package com.example.parakeet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.handmark.pulltorefresh.extras.listfragment.R.string;

import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaRecorder.OutputFormat;
import android.net.Uri;
import android.provider.OpenableColumns;

public class BitmapControl {

	// ----------------------------------------------------------------------------------------------
	// Constant declaration
	// ----------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------
	// Field Declaration
	// ----------------------------------------------------------------------------------------------
	private String filePath;
	private int srcWidth;
	private int srcHeight;
	private int baseSize = 128;
	private int thumnSize = 90;
	private Matrix matrix;
	private Bitmap bitmap;

	public BitmapControl(String filePath) {

		this.filePath = filePath;

	}

	public BitmapControl() {

	}

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

			Bitmap thum = makeBitmap(bitmap, thumnSize);

			return thum;

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();

			return null;
		}

	}

	public Bitmap decodeBitmap() {

		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(filePath, options);

		srcWidth = options.outWidth;
		srcHeight = options.outHeight;

		if (srcWidth < srcHeight) {
			options.inSampleSize = srcHeight / baseSize;
		} else {
			options.inSampleSize = srcWidth / baseSize;
		}

		options.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeFile(filePath, options);

		Bitmap tmp = makeBitmap(bitmap, baseSize);

		return tmp;
	}

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
