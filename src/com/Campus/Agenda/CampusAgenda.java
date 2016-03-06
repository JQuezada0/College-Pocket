package com.Campus.Agenda;

import java.util.ArrayList;
import java.util.Iterator;

import com.Campus.R;
import com.Campus.Utility.SimpleEntry;

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

public class CampusAgenda extends ActionBarActivity {
	
	private ViewPager mPager;
	private FragmentManager mFragmentManager;
	public static Fragment[] ViewPagerFragments;
	public static ArrayList<SimpleEntry<Integer, Fragment>> ChildFragments = new ArrayList<SimpleEntry<Integer, Fragment>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.campusagenda_campusagenda);
		mPager = (ViewPager) findViewById(R.id.pager);
		mFragmentManager = getSupportFragmentManager();
		ViewPagerFragments = new Fragment[]{Courses(), Teachers(), Holidays()};
		mPager.setAdapter(new mAdapter(mFragmentManager));
		final ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setCustomView(getLayoutInflater().inflate(R.layout.campusagenda_actionbar, null));
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar.newTab().setText("Courses").setTabListener(TabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Teachers").setTabListener(TabListener));
		mActionBar.addTab(mActionBar.newTab().setText("Holidays").setTabListener(TabListener));
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
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mPager.setCurrentItem(tab.getPosition(), true);
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}
	};
	
	private Fragment Courses(){
		Bundle b = new Bundle();
		b.putString("Type", "Courses");
		CampusAgendaMainPageFragment A = new CampusAgendaMainPageFragment();
		A.setArguments(b);
		return A;
	}
	
	private Fragment Teachers(){
		Bundle b = new Bundle();
		b.putString("Type", "Teachers");
		CampusAgendaMainPageFragment B = new CampusAgendaMainPageFragment();
		B.setArguments(b);
		return B;
	}
	
	private Fragment Holidays(){
		Bundle b = new Bundle();
		b.putString("Type", "Holidays");
		CampusAgendaMainPageFragment C = new CampusAgendaMainPageFragment();
		C.setArguments(b);
		return C;
	}
	
	public void Reset(int i, Fragment F){
		FragmentTransaction T = mFragmentManager.beginTransaction();
		T.remove(F);
		T.commit();
		ViewPagerFragments[i] = F;
		mPager.setAdapter(new mAdapter(mFragmentManager));
		mPager.setCurrentItem(i, true);
	}
	
	private class mAdapter extends FragmentStatePagerAdapter {
		
		public mAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ViewPagerFragments[position];
		}

		@Override
		public int getCount() {
			return ViewPagerFragments.length;
		}
		
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
	
}
