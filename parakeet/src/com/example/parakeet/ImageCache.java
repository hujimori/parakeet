package com.example.parakeet;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author Yoshimori
 *
 */
public class ImageCache {
	

	// ---------------------------------------------------------------------------------------------
	// class field declaration
	// ---------------------------------------------------------------------------------------------
	private static HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Bitmap getImage(String key) {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @param image
	 */
	public static void setImage(String key, Bitmap image) {
		cache.put(key, image);
	}

	public static void clearCache() {
		cache = null;
		cache = new HashMap<String, Bitmap>();
	}

}
