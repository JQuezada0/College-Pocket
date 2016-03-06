package com.Campus.BulletinBoard;

import java.util.ArrayList;
import java.util.List;

import com.Campus.R;
import com.Campus.Agenda.CampusAgenda;
import com.Campus.Utility.CampusController;
import com.Campus.Utility.DigiDialog;
import com.Campus.Utility.MessengerCallBack;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity{
	
	private String[] DigiDrawerListItems, DigiHome = new String[0], DigiProfile = new String[0], DigiShop = new String[]{"Buy Stuff", "Want Stuff"}, DigiLife = new String[]{"Events", "Housing", "Tutoring", "Odd Jobs", "Car Pooling"}, DigiSchool = new String[0], DigiMessages = new String[0], DigiInfo = new String[0], LogOut = new String[0];
	public static Spinner ActionBarSorterSpinner;
	private DrawerLayout DigiDrawer;
	private ExpandableListView DigiDrawerList;
	private Resources DigiResources;
	private FragmentManager mFragmentManager;
	private Drawable[] DigiDrawerListIcons;
	private final ArrayList<String[]> ExpDigiDrawerListItems = new ArrayList<String[]>();
	private ActionBarDrawerToggle mDrawerToggle;
	private FragmentTransaction mFragmentTransaction;
	public SharedPreferences Settings;
	private BulletinBoard GenFrag;
	private ImageButton NewDigiPost;
	private final Bundle Args = new Bundle();
	private final Bundle SavedInfo = new Bundle();
	private static MainActivity mMainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CampusController.MainActivityActive = true;
		ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NI = CM.getActiveNetworkInfo();
		if(NI.isConnected()){
			setContentView(R.layout.activity_main);
			Settings = getSharedPreferences("Settings", 0);
			CampusController.Username = Settings.getString("Username", "");
			Initialize();
			Restore(savedInstanceState);
			mMainActivity = this;
		}
		else {
			setContentView(R.layout.digi_init_activity_frame);
			Toast.makeText(this, "No Network!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public static MainActivity getInstance(){
		return mMainActivity;
	}
	
	private void Restore(Bundle Info){
		if (Info == null){
			System.out.println("Null");
		}
		if (Info!=null && Info.getString("Pattern")!=null){
		SavedInfo.putAll(Info);
		GenFrag = new BulletinBoard();
		Args.putString("Location", "PostType");
		Args.putString("Pattern", Info.getString("Pattern"));
		GenFrag.setArguments(Args);
		DigiDrawerList.setItemChecked(Info.getInt("GroupPosition"), true);
		DigiDrawerList.setSelectedGroup(Info.getInt("GroupPosition"));
		DigiDrawerList.setSelectedChild(Info.getInt("GroupPosition"), Info.getInt("childPosition"), true);
		DigiDrawer.closeDrawer(DigiDrawerList);
		mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
		mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
		}
	}
	
	class DigiDrawerController implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			switch(groupPosition){
			case 2:
				switch(childPosition){
				case 0:
					GenFrag = new BulletinBoard();
					SavedInfo.putString("Pattern", "Sell");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Sell");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					break;
				case 1:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Want");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "Want");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				}
				break;
			case 3:
				switch(childPosition){
				case 0:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Event");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "Event");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				case 1:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Housing");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "Housing");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				case 2:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Tutoring");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "Tutoring");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				case 3:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "OddJob");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "OddJob");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				case 4:
					GenFrag = new BulletinBoard();
					Args.putString("Location", "PostType");
					Args.putString("Pattern", "Carpooling");
					GenFrag.setArguments(Args);
					DigiDrawerList.setItemChecked(groupPosition, true);
					DigiDrawerList.setSelectedGroup(groupPosition);
					DigiDrawerList.setSelectedChild(groupPosition, childPosition, true);
					DigiDrawer.closeDrawer(DigiDrawerList);
					mFragmentTransaction = mFragmentManager.beginTransaction();
					mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
					mFragmentTransaction.replace(R.id.content_frame, GenFrag); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
					SavedInfo.putString("Pattern", "Carpooling");
					SavedInfo.putInt("GroupPosition", groupPosition);
					SavedInfo.putInt("childPosition", childPosition);
					break;
				}
				break;
			}
			return false;
		}

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			switch (groupPosition) {
			case 0: 
				DigiDrawerList.setItemChecked(groupPosition, true);
				DigiDrawerList.setSelectedGroup(groupPosition);
				DigiDrawer.closeDrawer(DigiDrawerList);
				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out);
				mFragmentTransaction.replace(R.id.content_frame, new CampusHome()); mFragmentTransaction.addToBackStack(null); mFragmentTransaction.commit(); 
				break;
			case 1:
				
				break;
			case 4:
				startActivity(new Intent(MainActivity.this, CampusAgenda.class));
				break;
			case 5:
		//		startActivity(new Intent(MainActivity.this, CampusMessaging.class));
				break;
			case 7:
				Bundle bundle = new Bundle();
				bundle.putString("Type", "LogOut");
				DigiDialog mDialog = new DigiDialog();
				mDialog.setArguments(bundle);
				mDialog.LogOutDialog(MainActivity.this);
				mDialog.show(mFragmentManager, "LogOutDialog");
			default: 
				break;
			}
			return false;
		}
	}
	
	private void Initialize(){
		ExpDigiDrawerListItems.add(DigiHome);
		ExpDigiDrawerListItems.add(DigiProfile);
		ExpDigiDrawerListItems.add(DigiShop);
		ExpDigiDrawerListItems.add(DigiLife);
		ExpDigiDrawerListItems.add(DigiSchool);
		ExpDigiDrawerListItems.add(DigiMessages);
		ExpDigiDrawerListItems.add(DigiInfo);
		ExpDigiDrawerListItems.add(LogOut);
		DigiDrawer = (DrawerLayout) findViewById(R.id.DigiDrawer);
		DigiDrawerList = (ExpandableListView) findViewById(R.id.DigiDrawerList);
		DigiResources = getResources();
		DigiDrawerListItems = DigiResources.getStringArray(R.array.DigiDrawerList);
		mFragmentManager = getSupportFragmentManager();
		DigiDrawerListIcons = new Drawable[]{DigiResources.getDrawable(R.drawable.digidrawer_home), DigiResources.getDrawable(R.drawable.digidrawer_profile), DigiResources.getDrawable(R.drawable.digidrawer_shop), DigiResources.getDrawable(R.drawable.digidrawer_life), DigiResources.getDrawable(R.drawable.digidrawer_school), DigiResources.getDrawable(R.drawable.digidrawer_message), DigiResources.getDrawable(R.drawable.digidrawer_info), DigiResources.getDrawable(R.drawable.digidrawer_logout)};
		mDrawerToggle = new ActionBarDrawerToggle(this, DigiDrawer, R.drawable.ic_drawer, R.string.app_name, R.string.app_name){
			@Override
			public void onDrawerClosed(View view){
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle("DigiQlik");
                invalidateOptionsMenu();
			}
			@Override
			public void onDrawerOpened(View view){
				super.onDrawerOpened(DigiDrawer);
                getSupportActionBar().setTitle("DigiQlik");
                invalidateOptionsMenu();
			}
		};
		DigiDrawerList.setAdapter(new MainActivity_DrawerAdapter(DigiDrawerListItems, DigiDrawerListIcons, getLayoutInflater(), ExpDigiDrawerListItems));
		mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.replace(R.id.content_frame, new CampusHome());
		mFragmentTransaction.commit();
		DigiDrawer.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setCustomView(R.layout.actionbar_sorter);
		ActionBarSorterSpinner = (Spinner) getSupportActionBar().getCustomView().findViewById(R.id.ActionBarSorterSpinner);
		ActionBarSorterSpinner.setAdapter(new com.Campus.Utility.SorterAdapter(new String[]{"Popular", "Views", "New"}, this));
		ActionBarSorterSpinner.setVisibility(View.GONE);
		DigiDrawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        DigiDrawerList.setOnGroupClickListener(new DigiDrawerController());
        DigiDrawerList.setOnChildClickListener(new DigiDrawerController());
        DigiDrawerList.setItemChecked(0, true);
        NewDigiPost = (ImageButton) findViewById(R.id.NewDigiPostIMGBTN);
        NewDigiPost.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, NewBoardItem.class));
			}
        });
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        return super.onPrepareOptionsMenu(menu);
	    }
	 
	 @Override
	 protected void onSaveInstanceState(Bundle outState){
		 super.onSaveInstanceState(outState);
		 outState.putAll(SavedInfo);
		 
	 }
	 
	 @Override
	 protected void onResume() {
	   super.onResume();
	   CampusController.MainActivityActive = true;
	 }
	 
	 @Override
	 protected void onStart(){
		 super.onStart();
		 CampusController.MainActivityActive = true;
	 }
	 
	 @Override
	 protected void onRestart(){
		 super.onRestart();
		 CampusController.MainActivityActive = true;
	 }

	 @Override
	 protected void onPause() {
	   super.onPause();
	  CampusController.MainActivityActive = false;
	 }
	 
	 @Override
	 protected void onStop(){
		 super.onStop();
		 CampusController.MainActivityActive = false;
	 }
	 
	 @Override
	 protected void onDestroy(){
		 super.onDestroy();
		 CampusController.MainActivityActive = false;
	 }

}
