package com.Campus.Agenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.Campus.R;
import com.Campus.Utility.SimpleEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;

public class CampusAgendaCourse extends ActionBarActivity {
	
	public static HashMap<String, String> Course;
	private ViewPager mPager;
	public static FrameLayout CourseContainer;
	private FragmentManager FM;
	public static Fragment[] Fragments;
	public static ArrayList<SimpleEntry<Integer, Fragment>> ChildFragments = new ArrayList<SimpleEntry<Integer, Fragment>>();
	
	@Override
	protected void onCreate(Bundle onSavedInstanceState){
		super.onCreate(onSavedInstanceState);
		setContentView(R.layout.campusagenda_course);
		String Course;
		if (getIntent().getExtras() != null){
			Course = getIntent().getExtras().getString("Course");
		}
		else {
			Course = null;
		}
		if (Course != null){
			CampusAgendaCourse.Course = new Gson().fromJson(this.getIntent().getExtras().getString("Course"), 
					new TypeToken<HashMap<String, String>>(){}.getType());
		}
		else {
			CampusAgendaCourse.Course = Tracker.createCourse();
		}
		mPager = (ViewPager) findViewById(R.id.CoursePager);
		CourseContainer = (FrameLayout) findViewById(R.id.CourseContainer);
		FM = getSupportFragmentManager();
		FM.executePendingTransactions();
		Fragments = new Fragment[]{Lessons(new Bundle()), Homework(new Bundle()), Exams(new Bundle())};
		mPager.setAdapter(new mAdapter(FM));
		final ActionBar mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setText("Lessons").setTabListener(TabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Homework").setTabListener(TabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Exams").setTabListener(TabListener));
		mPager.setOnPageChangeListener(new OnPageChangeListener(){
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageSelected(int position) {
				mActionBar.setSelectedNavigationItem(position);
			}
		});
	}
	
ActionBar.TabListener TabListener = new ActionBar.TabListener() {

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mPager.setCurrentItem(tab.getPosition(), true);
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}
		
		
	};
	
	public HashMap<String, String> getCourse(){
		return Course;
	}
	
	private Fragment Lessons(Bundle b){
		b = new Bundle();
		b.putString("Type", "Lessons");
		CampusAgendaCourseMainPageFragment A = new CampusAgendaCourseMainPageFragment();
		A.setArguments(b);
		return A;
	}
	
	private Fragment Homework(Bundle b){
		b = new Bundle();
		b.putString("Type", "Homework");
		CampusAgendaCourseMainPageFragment A = new CampusAgendaCourseMainPageFragment();
		A.setArguments(b);
		return A;
	}
	
	private Fragment Exams(Bundle b){
		b = new Bundle();
		b.putString("Type", "Exams");
		CampusAgendaCourseMainPageFragment A = new CampusAgendaCourseMainPageFragment();
		A.setArguments(b);
		return A;
	}
	
	public void Reset(int i, Fragment F){
		FragmentTransaction T = FM.beginTransaction();
		T.remove(F);
		T.commit();
		Fragments[i] = F;
		mPager.setAdapter(new mAdapter(FM));
		mPager.setCurrentItem(i, true);
		
	}
	
	@Override
	public void onBackPressed(){	
		Iterator<SimpleEntry<Integer, Fragment>> IT = ChildFragments.iterator();
		if (ChildFragments.size() > 0){
			while (IT.hasNext()){
				SimpleEntry<Integer, Fragment> H = IT.next();
				Reset(H.getKey(), H.getValue());
				IT.remove();
				}
			
		} else {
			super.onBackPressed();
		}
	}
	
	private class mAdapter extends FragmentStatePagerAdapter {
		

		public mAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return Fragments[position];
		}

		@Override
		public int getCount() {
			return Fragments.length;
		}
		
	}
	


}
