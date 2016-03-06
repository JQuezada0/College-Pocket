package com.Campus.BulletinBoard;


import java.util.HashMap;
import java.util.Map;

import com.Campus.R;
import com.Campus.Utility.CampusController;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager mFragmentManager = getSupportFragmentManager();
		SharedPreferences Settings = getSharedPreferences("Settings", 0);
		System.out.println(Settings.getString("Username", "") + "Username");
		if(Settings.getBoolean("LoggedIn", false)){
			startActivity(new Intent(this, MainActivity.class));
			this.finish();
		}
		else {
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.replace(R.id.DigiInitContainer, new FirstScreen());
		mFragmentTransaction.commit();
		setContentView(R.layout.digi_init_activity_frame);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override
	 protected void onResume() {
	   super.onResume();
	   CampusController.CreateAccountActive = true;
	 }

	 @Override
	 protected void onPause() {
	   super.onPause();
	  CampusController.CreateAccountActive = false;
	 }
	 
	 @Override
	 protected void onDestroy(){
		 super.onDestroy();
		 CampusController.CreateAccountActive = false;
	 }


public static class FirstScreen extends Fragment implements GeneralCallback{

	private FrameLayout DigiQlikInitForm;
	private Button LogIn;
	private Button SignUp;
	private EditText Username;
	private EditText Password;
	private Animation LogInAnim;
	private HashMap<String, String> Info;
	private ProgressBar Progress;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	private FragmentActivity mContext;
	private TextView DigiLogo;
	private SharedPreferences.Editor Editor;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Container, Bundle SavedInstanceState){
		View v = Inflater.inflate(R.layout.activity_create_account, Container, false);
		mContext = getActivity();
		Info = new HashMap<String, String>();
		DigiQlikInitForm = (FrameLayout) v.findViewById(R.id.StartScreenContainer);
		Editor = mContext.getSharedPreferences("Settings", 0).edit();
		LogIn = (Button) v.findViewById(R.id.DigiQlikInitFormLogInBtn);
		DigiLogo = (TextView) v.findViewById(R.id.DigiQlikLogo);
		Progress = (ProgressBar) v.findViewById(R.id.DigiSignUpProgress);
		DigiLogo.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Helvetica.ttf"));
		SignUp = (Button) v.findViewById(R.id.DigiQlikInitFormSignUpBtn);
		Username = (EditText) v.findViewById(R.id.LogInUsernameET);
		Password = (EditText) v.findViewById(R.id.LogInPasswordET);
		LogIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Info.put("Username", Username.getText().toString());
				Info.put("Password", Password.getText().toString());
				LogInAnim = AnimationUtils.loadAnimation(mContext, R.anim.digi_qlik_main_form_init);
				LogInAnim.setAnimationListener(LogInAnimListener);
				DigiQlikInitForm.startAnimation(LogInAnim);
				Progress.setVisibility(View.VISIBLE);
			}
		});
		SignUp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mFragmentManager = mContext.getSupportFragmentManager();
				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction.setCustomAnimations(R.anim.view_slide_in, R.anim.view_slide_out, R.anim.view_slide_in, R.anim.view_slide_out);
				mFragmentTransaction.replace(R.id.DigiInitContainer, new SignUpScreen());
				mFragmentTransaction.addToBackStack(null);
				mFragmentTransaction.commit();
			}
		});
		return v;
	}
	
	private AnimationListener LogInAnimListener = new AnimationListener(){
		
		@Override
		public void onAnimationStart(Animation animation) {	
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			LogIn(new NetworkUtility(mContext), Info, mContext, FirstScreen.this);
			DigiQlikInitForm.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	};
	
	private static void LogIn(NetworkUtility N, HashMap<String, String> H, FragmentActivity A, GeneralCallback O){
			N.LogIn(H, O);
			N.execute();
	}
	
	public void LogInFailed(){
		DigiQlikInitForm.setVisibility(View.VISIBLE);
		DigiQlikInitForm.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.view_slide_in));
		Progress.setVisibility(View.GONE);
	}

	@Override
	public void onTaskCompleted(HashMap<String, String> H) {
		System.out.println(H.get("Response"));
		System.out.println(H.get("Error"));
		System.out.println(H.get("Message"));
		try {
			if(Integer.parseInt(H.get("Error")) != 0){
				this.LogInFailed();
				Toast.makeText(mContext, H.get("Message"), Toast.LENGTH_SHORT).show();
			}
			else if (Integer.parseInt(H.get("Error")) == 0){
				Editor.putBoolean("LoggedIn", true);
				Editor.putString("Username", H.get("Username"));
				Editor.putString("Password", H.get("Password"));
				Editor.putString("Email", H.get("Email"));
				Editor.putString("School", H.get("School"));
				Editor.commit();
				startActivity(new Intent(mContext, MainActivity.class));
				mContext.finish();
			}
		} catch (NumberFormatException E){
			E.printStackTrace();
			this.LogInFailed();
			Toast.makeText(mContext, H.get("Message"), Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onActivityResult(String Path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSort(int i) {
		// TODO Auto-generated method stub
		
	}
}

public static class SignUpScreen extends Fragment implements GeneralCallback {
	
	private EditText Username;
	private EditText Password;
	private EditText ConfirmPassword;
	private EditText Email;
	private EditText ConfirmEmail;
	private Button SelectSchoolBtn;
	private Button Submit;
	private CreateAccount Activity;
	private Activity ParentActivity;
	private RelativeLayout SignUpForm;
	private ProgressBar Loading;
	private boolean SetSchool = false;
	private NetworkUtility Connection;
	private final Map<String, String> Info = new HashMap<String, String>();
	private String[] Schools;
	private AlertDialog d;
	private SharedPreferences.Editor Editor;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Container, Bundle SavedInstanceState){
		View v = Inflater.inflate(R.layout.fragment_sign_up, Container, false);
		Schools = new String[]{"SHU", "NYIT", "Rutgers"};
		ParentActivity = getActivity();
		Connection = new NetworkUtility(ParentActivity);
		Activity = (CreateAccount) ParentActivity;
		Editor = ParentActivity.getSharedPreferences("Settings", 0).edit();
		Username = (EditText) v.findViewById(R.id.SignUpUsernameET);
		Password = (EditText) v.findViewById(R.id.SignUpPasswordET);
		ConfirmPassword = (EditText) v.findViewById(R.id.SignUpConfirmPasswordET);
		Email = (EditText) v.findViewById(R.id.SignUpEmailET);
		ConfirmEmail = (EditText) v.findViewById(R.id.SignUpConfirmEmailET);
		SelectSchoolBtn = (Button) v.findViewById(R.id.SignUpSelectSchoolBtn);
		SelectSchoolBtn.setOnClickListener(SetSchoolBtnListener);
		Submit = (Button) v.findViewById(R.id.DigiQlikInitFormSignUpBtn);
		SignUpForm = (RelativeLayout) v.findViewById(R.id.SignUpContainer);
		Loading = (ProgressBar) v.findViewById(R.id.SignUpLoading);
		Submit.setOnClickListener(SubmitListener);
		return v;
	}
	
	private OnClickListener SubmitListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
		SignUpForm.setVisibility(View.GONE);
		Loading.setVisibility(View.VISIBLE);
		StoreInfo();
		AnimationDrawable LoadingAnim = (AnimationDrawable) Loading.getProgressDrawable();
		LoadingAnim.start();
		}
	};
	
	private OnClickListener SetSchoolBtnListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			AlertDialog.Builder b = new Builder(ParentActivity);
			View DialogView = LayoutInflater.from(ParentActivity).inflate(R.layout.digi_dialog, null);
			ListView LV = (ListView) DialogView.findViewById(R.id.SchoolPickerListView);
			LV.setAdapter(new SchoolSelectorAdapter(Schools, LayoutInflater.from(ParentActivity)));
			LV.setOnItemClickListener(SchoolSelectListener);
			d = b.create();
			d.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
			d.setView(DialogView);
			d.show();
			
		}
	};
	
	private void StoreInfo(){
		if (!Username.getText().toString().trim().equals("") &&
				!Password.getText().toString().trim().equals("") &&
				!ConfirmPassword.getText().toString().trim().equals("") &&
				!Email.getText().toString().trim().equals("") &&
				!ConfirmEmail.getText().toString().trim().equals("") &&
				SetSchool){
			if(Password.getText().toString().trim().equals(ConfirmPassword.getText().toString().trim())){
				if(Email.getText().toString().trim().equals(ConfirmEmail.getText().toString().trim())){
					Info.put("Username", Username.getText().toString().trim());
					Info.put("Password", Password.getText().toString().trim());
					Info.put("Email", Email.getText().toString().trim());
					Info.put("School", SelectSchoolBtn.getText().toString().trim());
					SignUp();
				}
				else if(!Email.getText().toString().trim().equals(ConfirmEmail.getText().toString().trim())){
					Toast.makeText(ParentActivity, "Email Mismatch", Toast.LENGTH_SHORT).show();
					SignUpForm.setVisibility(View.VISIBLE);
					Loading.setVisibility(View.GONE);
				}
			}
			else if(!Password.getText().toString().trim().equals(ConfirmPassword.getText().toString().trim())) {
				Toast.makeText(ParentActivity, "Password Mismatch", Toast.LENGTH_SHORT).show();
				SignUpForm.setVisibility(View.VISIBLE);
				Loading.setVisibility(View.GONE);
			}
			
		}
		else {
			Toast.makeText(ParentActivity, "Missing Field(s)", Toast.LENGTH_SHORT).show();
			SignUpForm.setVisibility(View.VISIBLE);
			Loading.setVisibility(View.GONE);
		}
	}
	
	private void SignUp(){
			Connection.UploadNewUser(Info, this);
		Connection.execute();
	}
	
	@Override
	public void onTaskCompleted(HashMap<String, String> H) {
		System.out.println(H.get("Response"));
		String Error = H.get("Error");
		System.out.println(Error);
		System.out.println(H.get("Message"));
		if (Integer.parseInt(Error) == 0){
			Editor.putBoolean("LoggedIn", true);
			Editor.putString("Username", Username.getText().toString().trim());
			Editor.putString("Password", Password.getText().toString().trim());
			Editor.putString("Email", Email.getText().toString().trim());
			Editor.putString("School", SelectSchoolBtn.getText().toString().trim());
			Editor.commit();
			startActivity(new Intent(ParentActivity, MainActivity.class));
			ParentActivity.finish();
		}
		else {
			Toast.makeText(ParentActivity, H.get("Response"), Toast.LENGTH_SHORT).show();
			SignUpForm.setVisibility(View.VISIBLE);
			Loading.setVisibility(View.GONE);
		}
	}
	
	private OnItemClickListener SchoolSelectListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			SelectSchoolBtn.setText((String)parent.getItemAtPosition(position));
			SetSchool = true;
			d.dismiss();
		}
		
	};
	
	private class SchoolSelectorAdapter extends BaseAdapter{
		
		String[] Schools;
		LayoutInflater Inflater;
		
		public SchoolSelectorAdapter(String[] s, LayoutInflater I){
			this.Schools = s;
			this.Inflater = I;
		}

		@Override
		public int getCount() {
			return Schools.length;
		}

		@Override
		public Object getItem(int position) {
			return Schools[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = Inflater.inflate(R.layout.digi_base_list_view, null);
			TextView TV = (TextView) convertView.findViewById(R.id.DigiBaseListViewTV);
			TV.setText(Schools[position]);
			return convertView;
		}
		
	}

	@Override
	public void onActivityResult(String Path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSort(int i) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
}
