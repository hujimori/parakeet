package com.example.parakeet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AccountView extends DialogFragment {

	private ListView listView;
	private AccountViewAdapter mAdapter;
	private Button button;
	private String mCallBackURL;
	private Twitter twitter;
	private RequestToken mRequestToken;
	private int check = 0;
	
	private final String USER = "user";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ

		View view = inflater.inflate(R.layout.dialog_view, null);

		listView = (ListView) view.findViewById(R.id.list);

		String consumerKey = getActivity().getString(
				R.string.twitter_consumer_key);
		String consumerSecret = getActivity().getString(
				R.string.twitter_consumer_secret);

		ConfigurationBuilder conf = new ConfigurationBuilder().setDebugEnabled(true);

		twitter = new TwitterFactory(conf.build()).getInstance();

		twitter.setOAuthConsumer(consumerKey, consumerSecret);

	
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USER, Context.MODE_PRIVATE);
		

		Map<String, ?> map = sharedPreferences.getAll();

		List<BindData> objects = new ArrayList<BindData>();
		
		BindData bindData;
		
		for (String key : map.keySet()) {

			Gson gson = new Gson();
			User user = gson.fromJson(sharedPreferences.getString(key, null), User.class);

			bindData = new BindData(user.screenName, false, user.id);
			
			objects.add(bindData);
		}
		
		mAdapter = new AccountViewAdapter(getActivity(), objects);

		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自動生成されたメソッド・スタブ
				for (int i = 0; i < mAdapter.getCount(); i++) {
					if (mAdapter.getItemId(i) == id) {
						// mAdapter.getItem(i).setIsChecked(true);
						TwitterUtils.saveID(getActivity(), mAdapter.getItem(i)
								.getId());
					} else {
						// mAdapter.getItem(i).setIsChecked(false);
					}
				}

				// アダプタの内容を即時反映する
				mAdapter.notifyDataSetChanged();
			}

		});

		return view;
	}

	/*
	 * @Override public Dialog onCreateDialog(Bundle savedInstanceState) { //
	 * TODO 自動生成されたメソッド・スタブ
	 * 
	 * LayoutInflater inflater = getActivity().getLayoutInflater();
	 * 
	 * View view = inflater.inflate(R.layout.dialog_view, null);
	 * 
	 * String consumerKey = getActivity().getString(
	 * R.string.twitter_consumer_key); String consumerSecret =
	 * getActivity().getString( R.string.twitter_consumer_secret);
	 * 
	 * ConfigurationBuilder conf = new ConfigurationBuilder().setUseSSL(true);
	 * 
	 * twitter = new TwitterFactory(conf.build()).getInstance();
	 * 
	 * twitter.setOAuthConsumer(consumerKey, consumerSecret);
	 * 
	 * mAdapter = new AccountViewAdapter(getActivity());
	 * 
	 * SharedPreferences sp = PreferenceManager
	 * .getDefaultSharedPreferences(getActivity());
	 * 
	 * Map<String, ?> map = sp.getAll();
	 * 
	 * for (String key : map.keySet()) {
	 * 
	 * mAdapter.add(sp.getString(key, null)); }
	 * 
	 * AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	 * builder.setTitle("アカウント"); builder.setSingleChoiceItems(mAdapter, check,
	 * new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * TODO 自動生成されたメソッド・スタブ
	 * 
	 * } });
	 * 
	 * builder.setPositiveButton("アカウント追加", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * TODO 自動生成されたメソッド・スタブ
	 * 
	 * } }); builder.setView(view);
	 * 
	 * return builder.create(); }
	 */
	// @Override

	/*
	 * public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 * Bundle savedInstanceState) { // TODO 自動生成されたメソッド・スタブ
	 * 
	 * View view = inflater.inflate(R.layout.account_view, null);
	 * 
	 * listView = (ListView) view.findViewById(R.id.list);
	 * 
	 * button = (Button) view.findViewById(R.id.add_account);
	 * 
	 * mCallBackURL = getString(R.string.twitter_callback_url2);
	 * 
	 * String consumerKey = getActivity().getString(
	 * R.string.twitter_consumer_key); String consumerSecret =
	 * getActivity().getString( R.string.twitter_consumer_secret);
	 * 
	 * ConfigurationBuilder conf = new ConfigurationBuilder().setUseSSL(true);
	 * 
	 * twitter = new TwitterFactory(conf.build()).getInstance();
	 * 
	 * twitter.setOAuthConsumer(consumerKey, consumerSecret);
	 * 
	 * button.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO 自動生成されたメソッド・スタブ
	 * 
	 * Intent intent = new Intent(); intent.setClass(getActivity(),
	 * IntentActivity.class); startActivity(intent); // startAuthorize();
	 * 
	 * } });
	 * 
	 * mAdapter = new AccountViewAdapter(getActivity());
	 * 
	 * SharedPreferences sp = PreferenceManager
	 * .getDefaultSharedPreferences(getActivity());
	 * 
	 * Map<String, ?> map = sp.getAll();
	 * 
	 * for (String key : map.keySet()) {
	 * 
	 * mAdapter.add(sp.getString(key, null)); }
	 * 
	 * listView.setAdapter(mAdapter); return view; }
	 */
	private void startAuthorize() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				try {
					mRequestToken = twitter.getOAuthRequestToken(mCallBackURL);

					return mRequestToken.getAuthorizationURL();
				} catch (TwitterException e) {
					e.printStackTrace();
				}

				return null;
			}

			public void onPostExecute(String url) {
				if (url != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse(url));

					startActivity(intent);
				} else {
					// ���s
				}
			}

		};
		task.execute();

	}

	public void onNewIntent(Intent intent) {

		showToast("認証中");

		if (intent == null || intent.getData() == null
				|| !intent.getData().toString().startsWith(mCallBackURL)) {
			return;
		}
		String verifier = intent.getData().getQueryParameter("oauth_verifier");

		AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
			protected AccessToken doInBackground(String... params) {
				try {
					return twitter
							.getOAuthAccessToken(mRequestToken, params[0]);

				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(AccessToken accessToken) {

				if (accessToken != null) {

					showToast("認証に成功しました");
					successOAuth(accessToken);
				} else {
					showToast("認証に失敗しました");
				}
			}
		};
		task.execute(verifier);

	}

	private void successOAuth(AccessToken accessToken) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		// TwitterUtils.storeAccessToken(getActivity(), accessToken);
		Intent intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	private void showToast(String text) {

		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

	}
}
