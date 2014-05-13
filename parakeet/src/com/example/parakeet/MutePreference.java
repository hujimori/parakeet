package com.example.parakeet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MutePreference extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mute_activity);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
			
				MuteDialog dialog = new MuteDialog();
				dialog.show(getSupportFragmentManager(), "mute");
			}
		});
		

	}

	
	public static class MuteDialog extends DialogFragment {

		
	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// TODO 自動生成されたメソッド・スタブ
	
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.mute_dialog, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("ミュート");
			builder.setPositiveButton("OK", null);
			builder.setNegativeButton("NO", null);
			builder.setView(view);
			
			return builder.create();
			
			
		}
		
		
		
	}


}
