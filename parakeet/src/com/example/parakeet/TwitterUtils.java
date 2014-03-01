/*���̃N���X�͈ȉ��̃��\�b�h��񋟂��܂��B
*Twitter�C���X�^���X�̎擾
*�A�N�Z�X�g�[�N���̕ۑ�
*�A�N�Z�X�g�[�N���̓Ǎ�
*�A�N�Z�X�g�[�N�����ۑ�����Ă��邩�m�F
*/
package com.example.parakeet;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TwitterUtils {

	private static final String TOKEN = "token";
	private static final String TOKEN_SECRET = "token_secret";
	private static final String PREF_NAME = "twitter_access_token";

	//Twitter�C���X�^���X���擾���܂��B�A�N�Z�X�g�[�N�����ۑ�����Ă���Ύ����I�ɃZ�b�g���܂��B

	public static Twitter getTwitterInstance(Context cn) {
		String consumerKey = cn.getString(R.string.twitter_consumer_key);
		String consumerSecret = cn.getString(R.string.twitter_consumer_secret);

		ConfigurationBuilder conf = new ConfigurationBuilder().setUseSSL(true);

		Twitter twitter = new TwitterFactory(conf.build()).getInstance();

		//		TwitterFactory factory = new TwitterFactory();
		//		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);

		if (hasAccessToken(cn)) {
			twitter.setOAuthAccessToken(loadAccessToken(cn));
		}
		return twitter;

	}

	//�A�N�Z�X�g�[�N�����v���t�@�����X�ɕۑ����܂��B

	public static void storeAccessToken(Context cn, AccessToken accessToken) {
		SharedPreferences preferences = cn.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

		Editor editor = preferences.edit();
		editor.putString(TOKEN, accessToken.getToken());
		editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
		editor.commit();
	}

	//�A�N�Z�X�g�[�N�����v���t�@�����X����ǂݍ��݂܂��B

	public static AccessToken loadAccessToken(Context cn) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		SharedPreferences preferences = cn.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

		String token = preferences.getString(TOKEN, null);
		String tokenSecret = preferences.getString(TOKEN_SECRET, null);
		if (token != null && tokenSecret != null) {
			return new AccessToken(token, tokenSecret);
		}
		return null;
	}

	public static boolean hasAccessToken(Context cn) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return loadAccessToken(cn) != null;
	}



}
