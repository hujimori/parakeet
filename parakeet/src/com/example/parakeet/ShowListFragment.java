package com.example.parakeet;

import java.util.ArrayList;
import java.util.List;

import twitter4j.TwitterException;
import twitter4j.UserList;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity.ListItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShowListFragment extends Fragment implements DialogListener {

	private ListView listView;
	private Button button;
	private ListAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ

		View view = inflater.inflate(R.layout.show_list_fragment, null);
		listView = (ListView) view.findViewById(R.id.list);
		button = (Button) view.findViewById(R.id.button);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(savedInstanceState);

		ListAdapter adapter = new ListAdapter(getActivity());
		LoadStatus mLoadStatus = new LoadStatus(adapter, getActivity());
	//	mLoadStatus.loadLists();

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				showDialog();

			}
		});

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自動生成されたメソッド・スタブ
				UserList list = (UserList) parent.getItemAtPosition(position);
				ItemListDialog mDialog = ItemListDialog.getInstance(list
						.getId());
				mDialog.show(getFragmentManager(), "dialog");

			}

		});

		listView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				return false;
			}
		});
	}

	private void showDialog() {
		ShowListDialog mDialog = new ShowListDialog();
		mDialog.setDialogListener(this);
		mDialog.show(getFragmentManager(), "dialog");
	}

	@Override
	public void onPositiveClick(String listName, boolean isPublic,
			String discription) {
		// TODO 自動生成されたメソッド・スタブ
		ListControl mControl = new ListControl(getActivity(), adapter);
		mControl.createList(listName, isPublic, discription);

	}

	@Override
	public void onNegativeClick() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public static class ItemListDialog extends DialogFragment {

		private ListView listView;

		public static ItemListDialog getInstance(long listId) {
			ItemListDialog instance = new ItemListDialog();
			Bundle mBundle = new Bundle();
			mBundle.putLong("LIST_ID", listId);
			instance.setArguments(mBundle);
			return instance;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ

			LayoutInflater mInflater = getActivity().getLayoutInflater();

			View mView = mInflater.inflate(R.layout.item_list_dialog, null);

			listView = (ListView) mView.findViewById(R.id.list);

			AlertDialog.Builder mBuilder = new AlertDialog.Builder(
					getActivity());
			mBuilder.setTitle("メニュー");
			mBuilder.setView(mView);
			return mBuilder.create();
		}

		@Override
		public void onActivityCreated(Bundle mBundle) {
			// TODO 自動生成されたメソッド・スタブ
			super.onActivityCreated(mBundle);

			final String[] strings = { "タイムライン", "メンバー表示", "メンバー追加", "リスト編集",
					"リスト削除", "ホームに追加" };
			List<String> objects = new ArrayList<String>();

			for (int i = 0; i < strings.length; i++) {
				objects.add(strings[i]);
			}

			ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1, objects);

			listView.setAdapter(mAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO 自動生成されたメソッド・スタブ

					Intent mIntent = new Intent();
					switch (position) {

					case 0:
						mIntent.setClass(getActivity().getApplicationContext(),
								ListTimeLineActivity.class);
						mIntent.putExtra("LIST_ID",
								getArguments().getLong("LIST_ID"));
						mIntent.putExtra("ID", position);
						getActivity().startActivity(mIntent);
						dismiss();
						/*
						 * ListTimeLineFragment line =
						 * ListTimeLineFragment.getInstance
						 * (getArguments().getInt("POSITION")); FragmentManager
						 * manager = getFragmentManager(); FragmentTransaction
						 * transaction = manager .beginTransaction();
						 * transaction.add(android.R.id.content, line).commit();
						 */

					}
				}

			});
		}

	}

	public static class ShowListDialog extends DialogFragment {

		private AlertDialog alertDialog;
		private EditText nameText;
		private DialogListener listener = null;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ

			LayoutInflater mInflater = getActivity().getLayoutInflater();

			View mView = mInflater.inflate(R.layout.show_list_dialog, null);

			nameText = (EditText) mView.findViewById(R.id.list_name);

			final EditText discription = (EditText) mView
					.findViewById(R.id.description);

			AlertDialog.Builder mBuilder = new AlertDialog.Builder(
					getActivity());

			mBuilder.setTitle("リスト作成");

			mBuilder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO 自動生成されたメソッド・スタブ
							listener.onPositiveClick(nameText.getText()
									.toString(), false, discription.getText()
									.toString());
							dismiss();

						}
					});

			mBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO 自動生成されたメソッド・スタブ
							listener.onNegativeClick();
							dismiss();
						}
					});

			mBuilder.setView(mView);
			alertDialog = mBuilder.create();
			alertDialog.show();
			return alertDialog;

		}

		@Override
		public void onActivityCreated(Bundle arg0) {
			// TODO 自動生成されたメソッド・スタブ
			super.onActivityCreated(arg0);

			final Button positiveButton = alertDialog
					.getButton(AlertDialog.BUTTON_POSITIVE);

			positiveButton.setEnabled(false);

			nameText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO 自動生成されたメソッド・スタブ

					if (!nameText.getText().toString().equals("")) {

						positiveButton.setEnabled(true);
					} else {
						positiveButton.setEnabled(false);
					}

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO 自動生成されたメソッド・スタブ

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO 自動生成されたメソッド・スタブ

				}
			});

		}

		public void setDialogListener(DialogListener listener) {
			this.listener = listener;
		}

		public void removeDialogListener() {
			this.listener = null;
		}

	}

}
