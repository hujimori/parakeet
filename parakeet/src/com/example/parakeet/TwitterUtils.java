/*���̃N���X�͈ȉ��̃��\�b�h��񋟂��܂��B
 *Twitter�C���X�^���X�̎擾
 *�A�N�Z�X�g�[�N���̕ۑ�
 *�A�N�Z�X�g�[�N���̓Ǎ�
 *�A�N�Z�X�g�[�N�����ۑ�����Ă��邩�m�F
 */
package com.example.parakeet;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.DropBoxManager.Entry;
import android.preference.PreferenceManager;

public class TwitterUtils {

	private static final String TOKEN = "token";
	private static final String TOKEN_SECRET = "token_secret";
	private static final String PREF_NAME = "twitter_access_token";
	private static final String USER = "user";
	private static final String FILE = "file";
	private static final String ID = "id";

	// Twitter�C���X�^���X���擾���܂��B�A�N�Z�X�g�[�N�����ۑ�����Ă���Ύ����I�ɃZ�b�g���܂��B

	public static Twitter getTwitterInstance(Context cn) {
		String consumerKey = cn.getString(R.string.twitter_consumer_key);
		String consumerSecret = cn.getString(R.string.twitter_consumer_secret);

		ConfigurationBuilder conf = new ConfigurationBuilder().setUseSSL(true);

		Twitter twitter = new TwitterFactory(conf.build()).getInstance();

		// TwitterFactory factory = new TwitterFactory();
		// Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);

		String id = loadID(cn);
		if (id != null && hasAccessToken(cn, id)) {
			twitter.setOAuthAccessToken(loadAccessToken(cn, id));
		}
		return twitter;

	}

	// �A�N�Z�X�g�[�N�����v���t�@�����X�ɕۑ����܂��B

	public static void storeAccessToken(Context cn, AccessToken accessToken) {
		SharedPreferences preferences = cn.getSharedPreferences(
				String.valueOf(accessToken.getUserId()), Context.MODE_PRIVATE);

		Editor editor = preferences.edit();
		editor.putString(TOKEN, accessToken.getToken());
		editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
		editor.commit();

		Twitter mTwitter = getTwitterInstance(cn);
		saveStatus(mTwitter, cn, String.valueOf(accessToken.getUserId()));

	}

	// �A�N�Z�X�g�[�N�����v���t�@�����X����ǂݍ��݂܂��B

	public static AccessToken loadAccessToken(Context cn, String id) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		SharedPreferences preferences = cn.getSharedPreferences(id,
				Context.MODE_PRIVATE);

		String token = preferences.getString(TOKEN, null);
		String tokenSecret = preferences.getString(TOKEN_SECRET, null);
		if (token != null && tokenSecret != null) {
			return new AccessToken(token, tokenSecret);
		}
		return null;
	}

	public static boolean hasAccessToken(Context cn, String id) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return loadAccessToken(cn, id) != null;
	}

	public static void saveStatus(final Twitter tw, final Context context,
			final String id) {

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO 自動生成されたメソッド・スタブ

				try {

					Twitter mTw = tw;
					
				
					User user = mTw.verifyCredentials();
					Gson gson = new Gson();
					String data = gson.toJson(user);

					return data;
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO 自動生成されたメソッド・スタブ
				super.onPostExecute(result);

				SharedPreferences sharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(context);
				Editor editor = sharedPreferences.edit();
				editor.putString(id, result);

				editor.commit();
			}

		};

		task.execute();

	}

	public static String loadStatus(Context context, String id) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		String userData = sharedPreferences.getString(id, null);

		return userData;

	}

	public static String loadID(Context mContext) {

		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FILE, Context.MODE_PRIVATE);

		String id = sharedPreferences.getString(ID, null);

		return id;

	}

	public static void saveID(Context mContext, String id) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FILE, Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();

		editor.putString(ID, id);

		editor.commit();

	}
}
