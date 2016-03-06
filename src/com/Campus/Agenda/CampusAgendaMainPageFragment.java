package com.Campus.Agenda;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.Campus.R;
import com.Campus.Utility.SimpleEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CampusAgendaMainPageFragment extends Fragment {
	
	FrameLayout Container;
	LinearLayout Layout;	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle savedInstanceState){
		String Type = getArguments().getString("Type");
		if (Type.equals("Courses")){
			System.out.println("Courses");
			return Courses(Parent, Inflater, getFragmentManager());
		}
		else if (Type.equals("Teachers")){
			System.out.println("Teachers");
			return Teachers(Parent, Inflater, getFragmentManager());
		}
		else if (Type.equals("Holidays")){
			System.out.println("Holidays");
			return Holidays(Parent, Inflater, getFragmentManager());
		}
		else{
			System.out.println("Null");
			return null;
		}
	}
	
	private View Courses(ViewGroup root, LayoutInflater Inflater, FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		Container = (FrameLayout) v.findViewById(R.id.CampusAgendaGeneralFragmentContainer);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Course");
		LV.setAdapter(new ContentList("Courses", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), CampusAgendaCourse.class);
				i.putExtra("Course", new Gson().toJson(parent.getItemAtPosition(position), 
						new TypeToken<HashMap<String, String>>(){}.getType()));
				getActivity().startActivity(i);
			}
			
		});
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), CampusAgendaCourse.class));
			}
		});
		
		return v;
	}
	
	private View Teachers(ViewGroup root, LayoutInflater Inflater, final FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		Container = (FrameLayout) v.findViewById(R.id.CampusAgendaGeneralFragmentContainer);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Teacher");
		LV.setAdapter(new ContentList("Teachers", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChangeFragment(FM, new TeacherEditFragment(), "Teacher", 
		 			new Gson().toJson(parent.getItemAtPosition(position), new TypeToken<HashMap<String, String>>(){}.getType()),
						"Teachers", 1);			
				
			}
			
		});
		
		LV.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view,
					final int position, long id) {
				new DialogFragment(){
					@Override
					public Dialog onCreateDialog(Bundle b){
						AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
						Builder.setTitle("Delete");
						Builder.setMessage("Are you sure you want to delete this Teacher?");
						Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Tracker T = new Tracker(getActivity());
								T.deleteTeacher((HashMap<String, String>) parent.getItemAtPosition(position));
								Bundle b = new Bundle();
								b.putString("Type", "Teachers");
								CampusAgendaMainPageFragment Back = new CampusAgendaMainPageFragment();
								Back.setArguments(b);
								CampusAgenda.ChildFragments.add(new SimpleEntry<Integer, Fragment>(1, Back));
								getActivity().onBackPressed();
								
							}
						});
						Builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						return Builder.create();
					}
				}.show(getFragmentManager(), "LongClick");
				return true;
			}
			
		});
		
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ChangeFragment(FM, new TeacherEditFragment(), "Teachers", 1);
			}
		});
		return v;
	}
	
	private View Holidays(ViewGroup root, LayoutInflater Inflater, final FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		Container = (FrameLayout) v.findViewById(R.id.CampusAgendaGeneralFragmentContainer);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Holiday");
		LV.setAdapter(new ContentList("Holidays", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChangeFragment(FM, new HolidayEditFragment(), "Holiday",
						new Gson().toJson(parent.getItemAtPosition(position), new TypeToken<HashMap<String, String>>(){}.getType()),
						"Holidays", 2);
			}
			
		});
		
		LV.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, View view,
					final int position, long id) {
				new DialogFragment(){
					@Override
					public Dialog onCreateDialog(Bundle b){
						AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
						Builder.setTitle("Delete");
						Builder.setMessage("Are you sure you want to delete this Holiday?");
						Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Tracker T = new Tracker(getActivity());
								T.deleteHoliday((HashMap<String, String>) parent.getItemAtPosition(position));
								Bundle b = new Bundle();
								b.putString("Type", "Holidays");
								CampusAgendaMainPageFragment Back = new CampusAgendaMainPageFragment();
								Back.setArguments(b);
								CampusAgenda.ChildFragments.add(new SimpleEntry<Integer, Fragment>(2, Back));
								getActivity().onBackPressed();
								
							}
						});
						Builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						return Builder.create();
					}
				}.show(getFragmentManager(), "LongClick");
				return true;
			}
			
		});
		
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ChangeFragment(FM, new HolidayEditFragment(), "Holidays", 2);
			}
		});
		return v;
	}
	
	private void ChangeFragment(FragmentManager mFragMan, Fragment F, String key, String value, String Tag, int i){
		Bundle a = new Bundle();
		a.putString(key, value);
		F.setArguments(a);
		CampusAgenda CA = (CampusAgenda) getActivity();
		Bundle b = new Bundle();
		b.putString("Type", Tag);
		CampusAgendaMainPageFragment Back = new CampusAgendaMainPageFragment();
		Back.setArguments(b);
		CampusAgenda.ChildFragments.add(new SimpleEntry<Integer, Fragment>(i, Back));
		CA.Reset(i, F);
	}
	
	private void ChangeFragment(FragmentManager mFragMan, Fragment F, String Tag, int i){
		CampusAgenda CA = (CampusAgenda) getActivity();
		Bundle b = new Bundle();
		b.putString("Type", Tag);
		CampusAgendaMainPageFragment Back = new CampusAgendaMainPageFragment();
		Back.setArguments(b);
		CampusAgenda.ChildFragments.add(new SimpleEntry<Integer, Fragment>(i, Back));
		CA.Reset(i, F);
	}
	
	private class ContentList extends BaseAdapter {
		
		private Context c;
		private ArrayList<HashMap<String, String>> ContentList;
		private Tracker mTracker;
		private String contentDescription;
		private LayoutInflater Inflater;
		
		public ContentList(String s, LayoutInflater Inflater){
			c = getActivity();
			this.Inflater = Inflater;
			contentDescription = s;
			mTracker = new Tracker(c);
			if (s.equals("Courses")){
				ContentList = mTracker.getCourses();
			}
			else if (s.equals("Teachers")){
				ContentList = mTracker.getTeachers();
			}
			else if (s.equals("Holidays")){
				ContentList = mTracker.getHolidays();
			}
		}

		@Override
		public int getCount() {
			return ContentList.size();
		}

		@Override
		public Object getItem(int position) {
			return ContentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				
			if (contentDescription.equals("Courses")){
				convertView = Courses(ContentList.get(position));
			}
			
			else if (contentDescription.equals("Teachers")){
				convertView = Teachers(ContentList.get(position));
			}
			
			else if (contentDescription.equals("Holidays")){
				convertView = Holidays(ContentList.get(position));
			}
		}
			return convertView;
		}
		
		private View Courses(HashMap<String, String> Content){
			View v = Inflater.inflate(R.layout.campusagenda_courses_fragment_row, null);
			TextView CourseTitle = (TextView) v.findViewById(R.id.CourseTitle);
			ImageView CourseColor = (ImageView) v.findViewById(R.id.CourseColor);
			CourseTitle.setText(Content.get("Title"));
			CourseColor.setBackgroundColor(Color.parseColor(Content.get("Color")));
			return v;
		}
		
		private View Teachers(HashMap<String, String> Content){
			View v = Inflater.inflate(R.layout.campusagenda_teachers_fragment_row, null);
			TextView TeacherTitle = (TextView) v.findViewById(R.id.TeacherTitle);
			TextView TeacherRoom = (TextView) v.findViewById(R.id.TeacherRoom);
			TextView TeacherTime = (TextView) v.findViewById(R.id.TeacherTime);
			TextView TeacherDays = (TextView) v.findViewById(R.id.TeacherDays);
			ImageView TeacherColor = (ImageView) v.findViewById(R.id.TeacherColor);
			TeacherTitle.setText(Content.get("Title"));
			TeacherRoom.setText(Content.get("Room"));
			TeacherTime.setText(Content.get("Time"));
			TeacherDays.setText(Content.get("Days"));
			TeacherColor.setBackgroundColor(Color.parseColor(Content.get("Color")));
			return v;
		}
		
		private View Holidays(HashMap<String, String> Content){
			View v = Inflater.inflate(R.layout.campusagenda_holidays_fragment_row, null);
			TextView HolidayTitle = (TextView) v.findViewById(R.id.HolidayTitle);
			TextView HolidayStartDate = (TextView) v.findViewById(R.id.HolidayStartDate);
			TextView HolidayEndDate = (TextView) v.findViewById(R.id.HolidayEndDate);
			TextView HolidayDaysRemaining = (TextView) v.findViewById(R.id.HolidayDaysRemaining);
			ImageView HolidayColor = (ImageView) v.findViewById(R.id.HolidayColor);
			HolidayTitle.setText(Content.get("Title"));
			HolidayStartDate.setText(Content.get("StartDate") + " -");
			HolidayEndDate.setText(Content.get("EndDate"));
			HolidayDaysRemaining.setText(getDaysRemaining(Content.get("StartDate"), Content.get("EndDate")));
			HolidayColor.setBackgroundColor(Color.parseColor(Content.get("Color")));
			return v;
		}
		
		private String getDaysRemaining(String StartDate, String EndDate){
			Calendar c = Calendar.getInstance();
			String[] EndArr = EndDate.split("/");
			c.set(Integer.parseInt(EndArr[2]), Integer.parseInt(EndArr[0]), Integer.parseInt(EndArr[1]));
			long Start = 0;
			long End = 0;
			
				Time t = new Time();
				t.setToNow();
				Start = t.toMillis(false);
				End = c.getTimeInMillis();
			
			return Long.toString((End - Start) / (1000*60*60*24));
			
		}
		
	}

}
