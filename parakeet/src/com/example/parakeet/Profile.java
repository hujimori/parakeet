package com.example.parakeet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class Profile extends FragmentActivity {

	private SmartImageView icon;
	private SmartImageView header;
	private Button tweet;
	private Button follows;
	private Button followers;
	private Button followes;
	private Button favorites;
	private TextView bio;
	private TextView url;
	private TextView entry;
	private TextView id;
	private Twitter mTwitter;
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(arg0);
		setContentView(R.layout.profile);

		mTwitter = TwitterUtils.getTwitterInstance(this);

		icon = (SmartImageView) findViewById(R.id.icon);
		header = (SmartImageView) findViewById(R.id.header);
		bio = (TextView) findViewById(R.id.bio_text);
		url = (TextView) findViewById(R.id.URL);
		entry = (TextView) findViewById(R.id.entryday);
		id = (TextView) findViewById(R.id.Id);

		load();

	}

	private void load() {

		AsyncTaskLoader loader = new AsyncTaskLoader(mTwitter, this, icon, header, bio, url, entry, id);
		loader.loadProfile();
		loader.loadHeader();
		loader.loadDescription();
		loader.loadEntryDay();
		loader.loadID();
		loader.loadUserURL();

	}

	class AsyncTaskLoader {

		private Twitter mTwitter;
		private ProgressDialog mDialog;
		private Context mContext;
		private SmartImageView icon;
		private SmartImageView header;
		private TextView bio;
		private TextView url;
		private TextView entry;
		private TextView id;
		private DateFormat date;

		public AsyncTaskLoader(Twitter mTwitter, Context mContext, SmartImageView icon, SmartImageView header,
				TextView bio, TextView url, TextView entry, TextView id) {
			this.mContext = mContext;
			this.mTwitter = mTwitter;
			this.icon = icon;
			this.header = header;
			this.bio = bio;
			this.entry = entry;
			this.id = id;
			this.url = url;
		}

		public void loadProfile() {
			AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

				@Override
				protected void onPreExecute() {
					// TODO 自動生成されたメソッド・スタブ
					super.onPreExecute();

					mDialog = new ProgressDialog(mContext);
					mDialog.setMessage("Now Loading....");
					mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mDialog.show();
				}

				@Override
				protected String doInBackground(Void... params) {
					try {

						return mTwitter.verifyCredentials().getProfileImageURL();

					} catch (TwitterException e) {
						e.printStackTrace();
						return null;
					}
				}

				@Override
				protected void onPostExecute(String result) {

					if (result != null) {

						icon.setImageUrl(result);

					}

					if (mDialog != null && mDialog.isShowing()) {
						mDialog.dismiss();
					}

				}
			};

			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		}

		public void loadHeader() {

			AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ

					try {
						return mTwitter.verifyCredentials().getProfileBannerURL();
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

					if (result != null) {

						header.setImageUrl(result);

					}
				}

			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		}

		public void loadDescription() {

			AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
				protected String doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ

					try {
						return mTwitter.verifyCredentials().getDescription();
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

					if (result != null) {

						bio.setText(result);

					}
				}

			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

		public void loadEntryDay() {

			AsyncTask<Void, Void, Date> task = new AsyncTask<Void, Void, Date>() {

				@Override
				protected Date doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ

					try {
						return mTwitter.verifyCredentials().getCreatedAt();
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Date result) {
					// TODO 自動生成されたメソッド・スタブ
					super.onPostExecute(result);

					if (result != null) {

						date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						entry.setText(date.format(result));

					}
				}

			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		}

		public void loadID() {

			AsyncTask<Void, Void, Long> task = new AsyncTask<Void, Void, Long>() {

				@Override
				protected Long doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ

					try {
						return mTwitter.verifyCredentials().getId();
					} catch (TwitterException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Long result) {
					// TODO 自動生成されたメソッド・スタブ
					super.onPostExecute(result);

					if (result != null) {

						id.setText(String.valueOf(result));

					}
				}

			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

		public void loadUserURL() {

			AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					// TODO 自動生成されたメソッド・スタブ
					try {
						return mTwitter.verifyCredentials().getURL();
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

					if (result != null) {

						url.setText(result);

					}
				}

			};
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

	}
}

/*	AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

		@Override
		protected String doInBackground(Void... params) {
			// TODO 自動生成されたメソッド・スタブ

			try {
				return mTwitter.verifyCredentials().getProfileBannerURL();
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return null;
			}
		}*/
/*
					@Override
					protected void onPostExecute(String result) {
						// TODO 自動生成されたメソッド・スタブ
						super.onPostExecute(result);

						if (result != null) {
							header.setImageUrl(result);
						}

						AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

							@Override
							protected String doInBackground(Void... params) {
								// TODO 自動生成されたメソッド・スタブ

								try {
									return mTwitter.verifyCredentials().getDescription();
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
								if (result != null) {
									bio.setText(result);
								}

								AsyncTask<Void, Void, Date> task = new AsyncTask<Void, Void, Date>() {

									@Override
									protected Date doInBackground(Void... params) {
										// TODO 自動生成されたメソッド・スタブ

										try {
											return mTwitter.verifyCredentials().getCreatedAt();
										} catch (TwitterException e) {
											// TODO 自動生成された catch ブロック
											e.printStackTrace();
										}
										return null;
									}

									@Override
									protected void onPostExecute(Date result) {
										// TODO 自動生成されたメソッド・スタブ
										super.onPostExecute(result);

										if (result != null) {
											date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
											entry.setText(date.format(result));

										}

										AsyncTask<Void, Void, Long> task = new AsyncTask<Void, Void, Long>() {

											@Override
											protected Long doInBackground(Void... params) {
												// TODO 自動生成されたメソッド・スタブ

												try {
													return mTwitter.verifyCredentials().getId();
												} catch (TwitterException e) {
													// TODO 自動生成された catch ブロック
													e.printStackTrace();
												}
												return null;
											}

											@Override
											protected void onPostExecute(Long result) {
												// TODO 自動生成されたメソッド・スタブ
												super.onPostExecute(result);

												if (result != null) {
													id.setText(String.valueOf(result));
												}

												AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

													@Override
													protected String doInBackground(Void... params) {
														// TODO 自動生成されたメソッド・スタブ
														try {
															return mTwitter.verifyCredentials().getURL();
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
														if (result != null) {
															url.setText(result);
															//
															//icon.setImage(re);
														}
													}

												};
												task.executeOnExecutor(THREAD_POOL_EXECUTOR);

											}

										};
										task.executeOnExecutor(THREAD_POOL_EXECUTOR);
									}
								};
								task.executeOnExecutor(THREAD_POOL_EXECUTOR);
							}

						};

						task.executeOnExecutor(THREAD_POOL_EXECUTOR);
					}

				};

				task.executeOnExecutor(THREAD_POOL_EXECUTOR);

			}
		};

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

	}*/

