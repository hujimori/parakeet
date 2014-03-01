package com.example.parakeet;
import java.util.HashMap;

import android.graphics.drawable.Drawable;

public class ImageCache {
	private static HashMap<String, Drawable> cache = new HashMap<String, Drawable>();

	//キャッシュより画像データを取得
	public static Drawable getImage(String key) {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		//存在しない場合はNULLを返す
		return null;
	}

	//キャッシュに画像データを設定
	public static void setImage(String key, Drawable image) {
		cache.put(key, image);
	}

	//キャッシュの初期化（リスト選択終了時に呼び出し、キャッシュで使用していたメモリを解放する）
	public static void clearCache() {
		cache = null;
		cache = new HashMap<String, Drawable>();
	}

}
