package com.Campus.Utility;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Set;

import com.Campus.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DigiDialog extends DialogFragment {
	
	private EditText mEditText;
	private TextView mTitle;
	private View DigiDialogTitle;
	private Activity mContext;
	private DialogInterface.OnClickListener mDialogClickListener;
	
	@Override
	public void onResume(){
		super.onResume();
		Dialog d = this.getDialog();
		int DividerID = d.getContext().getResources().getIdentifier("titleDivider", "id", "android");
		View v = d.findViewById(DividerID);
		v.setBackgroundColor(Color.TRANSPARENT);
	}
	
	public void LogOutDialog(final Activity activity){
		mDialogClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		};
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		String Type = this.getArguments().getString("Type");
		mContext = this.getActivity();
		DigiDialogTitle = LayoutInflater.from(mContext).inflate(R.layout.digi_dialog_title, null);
		mTitle = (TextView) DigiDialogTitle.findViewById(R.id.DigiDialogTitleTV);
		AlertDialog.Builder Builder = new AlertDialog.Builder(mContext);
		Builder.setCustomTitle(DigiDialogTitle);
		
		if (Type.equals("NewMessage")){
		mEditText = new EditText(mContext);
		mTitle.setText("Enter Username");
		Builder.setView(mEditText);
		Builder.setPositiveButton("New Message", mDialogClickListener);
		}
		
		if (Type.equals("LogOut")){
		TextView TV = new TextView(mContext);
		mTitle.setText("LogOut?");
		TV.setText("Are you sure you want to Sign Out?");
		TV.setGravity(Gravity.CENTER);
		Builder.setMessage("Are you sure you want to Sign Out?");
		Builder.setPositiveButton("LogOut!", mDialogClickListener);
		}
		
		return Builder.create();
	}

}
