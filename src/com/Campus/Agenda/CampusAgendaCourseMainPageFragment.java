package com.Campus.Agenda;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.Campus.R;
import com.Campus.Utility.SimpleEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CampusAgendaCourseMainPageFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle savedInstanceState){
		String Type = getArguments().getString("Type");
		if (Type.equals("Lessons")){
			System.out.println("Lessons");
			return Lessons(Parent, Inflater, getFragmentManager());
		}
		else if (Type.equals("Homework")){
			System.out.println("Homework");
			return Homework(Parent, Inflater, getFragmentManager());
		}
		else if (Type.equals("Exams")){
			System.out.println("Exams");
			return Exams(Parent, Inflater, getFragmentManager());
		}
		else {
			System.out.println("Null");
			return null;
		}
	}
	
	private View Lessons(ViewGroup root, LayoutInflater Inflater, final FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Lesson");
		LV.setAdapter(new ContentList("Lessons", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChangeFragment(FM, new LessonEditFragment(), "Lesson", 
						new Gson().toJson(parent.getItemAtPosition(position), 
								new TypeToken<HashMap<String, String>>(){}.getType()), "Lessons", 0);
			}
		});
		
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ChangeFragment(FM, new LessonEditFragment(), "Lessons", 0);
			}
		});
		return v;
	}
	
	private View Homework(ViewGroup root, LayoutInflater Inflater, final FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Homework");
		LV.setAdapter(new ContentList("Homework", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChangeFragment(FM, new HomeworkEditFragment(), "Homework", 
						new Gson().toJson(parent.getItemAtPosition(position), 
								new TypeToken<HashMap<String, String>>(){}.getType()), "Homework", 1);
			}
		});
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ChangeFragment(FM, new HomeworkEditFragment(), "Homework", 1);
			}
		});
		return v;
	}
	
	private View Exams(ViewGroup root, LayoutInflater Inflater, final FragmentManager FM){
		View v = Inflater.inflate(R.layout.campusagenda_general_fragment, root, false);
		ListView LV = (ListView) v.findViewById(R.id.CampusAgendaItemsList);
		LinearLayout LL = (LinearLayout) v.findViewById(R.id.CampusAgendaAddItem);
		((TextView) v.findViewById(R.id.CampusAgendaAddItemText)).setText("Click here to add new Exam");
		LV.setAdapter(new ContentList("Exams", Inflater));
		LV.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ChangeFragment(FM, new ExamEditFragment(), "Exam", new Gson().toJson(parent.getItemAtPosition(position),
						new TypeToken<HashMap<String, String>>(){}.getType()), "Exams", 2);
			}
		});
		LL.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ChangeFragment(FM, new ExamEditFragment(), "Exams", 2);
			}
		});
		return v;
	}
	
	private void ChangeFragment(FragmentManager mFragMan, Fragment F, String key, String value, String Tag, int i){
		Bundle a = new Bundle();
		a.putString(key, value);
		F.setArguments(a);
		CampusAgendaCourse CAC = (CampusAgendaCourse) getActivity();
		Bundle b = new Bundle();
		b.putString("Type", Tag);
		CampusAgendaCourseMainPageFragment Back = new CampusAgendaCourseMainPageFragment();
		Back.setArguments(b);
		CampusAgendaCourse.ChildFragments.add(new SimpleEntry<Integer, Fragment>(i, Back));
		CAC.Reset(i, F);
	}
	
	private void ChangeFragment(FragmentManager mFragMan, Fragment F, String Tag, int i){
		CampusAgendaCourse CAC = (CampusAgendaCourse) getActivity();
		Bundle b = new Bundle();
		b.putString("Type", Tag);
		CampusAgendaCourseMainPageFragment Back = new CampusAgendaCourseMainPageFragment();
		Back.setArguments(b);
		CampusAgendaCourse.ChildFragments.add(new SimpleEntry<Integer, Fragment>(i, Back));
		CAC.Reset(i, F);
	}
	
private class ContentList extends BaseAdapter {
		
		private Context c;
		private ArrayList<HashMap<String, String>> ContentList;
		private Tracker mTracker;
		private String contentDescription;
		private LayoutInflater Inflater;
		
		public ContentList(String s, LayoutInflater Inflater){
			this.c = Inflater.getContext();
			this.Inflater = Inflater;
			this.contentDescription = s;
			mTracker = new Tracker(c);
			
			if(s.equals("Lessons")){
				ContentList = mTracker.getLessons(CampusAgendaCourse.Course);
			}
			else if (s.equals("Homework")){
				ContentList = mTracker.getHomework(CampusAgendaCourse.Course);
			}
			else if (s.equals("Exams")){
				ContentList = mTracker.getExams(CampusAgendaCourse.Course);
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
					convertView = Lessons(ContentList.get(position));
				}
				
				else if (contentDescription.equals("Homework")){
					convertView = Homework(ContentList.get(position));
				}
				
				else if (contentDescription.equals("Exams")){
					convertView = Exams(ContentList.get(position));
				}
			}
			return convertView;
		}
		
		private View Lessons(HashMap<String, String> Lesson){
			View v = Inflater.inflate(R.layout.campusagenda_lessons_fragment_row, null);
			TextView LessonTitle = (TextView) v.findViewById(R.id.LessonTitle);
			TextView LessonDays = (TextView) v.findViewById(R.id.LessonDays);
			TextView LessonTeacher_Room = (TextView) v.findViewById(R.id.LessonTeacher);
			TextView LessonTime = (TextView) v.findViewById(R.id.LessonTime);
			LessonTitle.setText(Lesson.get("LessonTitle"));
			LessonDays.setText(Lesson.get("LessonDays"));
			LessonTeacher_Room.setText(Lesson.get("LessonTeacher") + "," + Lesson.get("LessonRoom"));
			LessonTime.setText(Lesson.get("LessonTime"));
			return v;
		}
		
		private View Homework(HashMap<String, String> Homework){
			View v = Inflater.inflate(R.layout.campusagenda_homework_fragment_row, null);
			TextView HomeworkTitle = (TextView) v.findViewById(R.id.HomeworkTitle);
			TextView HomeworkDaysRemaining = (TextView) v.findViewById(R.id.HomeworkDaysRemaining);
			TextView HomeworkDescription = (TextView) v.findViewById(R.id.HomeworkDescription);
			TextView HomeworkDueDay = (TextView) v.findViewById(R.id.HomeworkDueDay);
			TextView HomeworkDueDate_Time = (TextView) v.findViewById(R.id.HomeworkDueDate);
			CheckBox HomeworkCompleted = (CheckBox) v.findViewById(R.id.HomeworkCompleted);
			HomeworkTitle.setText(Homework.get("HomeworkTitle"));
			HomeworkDaysRemaining.setText(Homework.get("HomeworkDaysRemaining"));
			HomeworkDescription.setText(Homework.get("HomeworkDescription"));
			HomeworkDueDay.setText(Homework.get("HomeworkDueDay"));
			HomeworkDueDate_Time.setText(Homework.get("HomeworkDueDate") + "," + Homework.get("HomeworkTime"));
			HomeworkCompleted.setChecked(Boolean.valueOf(Homework.get("HomeworkCompleted")));
			return v;
		}
		
		private View Exams(HashMap<String, String> Exam){
			View v = Inflater.inflate(R.layout.campusagenda_exams_fragment_row, null);
			TextView ExamTitle = (TextView) v.findViewById(R.id.ExamTitle);
			TextView ExamDaysUntil = (TextView) v.findViewById(R.id.ExamDaysUntil);
			TextView ExamRoom = (TextView) v.findViewById(R.id.ExamRoom);
			TextView ExamDay = (TextView) v.findViewById(R.id.ExamDay);
			TextView ExamDateTime = (TextView) v.findViewById(R.id.ExamDateTime);
			CheckBox ExamCompleted = (CheckBox) v.findViewById(R.id.ExamCompleted);
			ExamTitle.setText(Exam.get("ExamTitle"));
			ExamDaysUntil.setText(Exam.get("ExamDaysUntil"));
			ExamRoom.setText(Exam.get("ExamRoom"));
			ExamDay.setText(Exam.get("ExamDay"));
			ExamDateTime.setText(Exam.get("ExamDate") + "," + Exam.get("ExamTime"));
			ExamCompleted.setChecked(Boolean.valueOf(Exam.get("ExamCompleted")));
			return v;
		}
		
	}
}
