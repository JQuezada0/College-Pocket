package com.Campus.Agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.Campus.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LessonEditFragment extends Fragment {
	
	private EditText EditLessonTitle;
	private EditText EditLessonRoom;
	private EditText EditLessonTeacher;
	private TextView EditLessonWeekdays;
	private TextView EditLessonTimeFrom;
	private TextView EditLessonTimeUntil;
	private TextView EditLessonDateStartingOn;
	private TextView EditLessonDateEndingOn;
	private HashMap<String, String> OldLesson;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle onSavedInstanceState){
		View v = Inflater.inflate(R.layout.campusagenda_lessons_fragment_edit, Parent, false);
		SetFields(v, getArguments());
		setHasOptionsMenu(true);
		return v;
	}
	
	private void SetFields(View v, Bundle b){
		EditLessonTitle = (EditText) v.findViewById(R.id.EditLessonTitle);
		EditLessonRoom = (EditText) v.findViewById(R.id.EditLessonRoom);
		EditLessonTeacher = (EditText) v.findViewById(R.id.EditLessonTeacher);
		EditLessonWeekdays = (TextView) v.findViewById(R.id.EditLessonWeekday);
		EditLessonTimeFrom = (TextView) v.findViewById(R.id.EditLessonTimeFrom);
		EditLessonTimeUntil = (TextView) v.findViewById(R.id.EditLessonTimeUntil);
		EditLessonDateStartingOn = (TextView) v.findViewById(R.id.EditLessonDateStartingOn);
		EditLessonDateEndingOn = (TextView) v.findViewById(R.id.EditLessonDateEndingOn);
		
		if (b!=null){
			HashMap<String, String> Lesson = new Gson().fromJson(b.getString("Lesson"), new TypeToken<HashMap<String, String>>(){}.getType());
			OldLesson = Lesson;
			EditLessonTitle.setText(Lesson.get("LessonTitle"));
			EditLessonRoom.setText(Lesson.get("LessonRoom"));
			EditLessonTeacher.setText(Lesson.get("LessonTeacher"));
			EditLessonWeekdays.setText(Lesson.get("LessonWeekdays"));
			EditLessonTimeFrom.setText(Lesson.get("LessonTimeFrom"));
			EditLessonTimeUntil.setText(Lesson.get("LessonTimeUntil"));
			EditLessonDateStartingOn.setText(Lesson.get("LessonDateStartingOn"));
			EditLessonDateEndingOn.setText(Lesson.get("LessonDateEndingOn"));
		}
		
		
		EditLessonTimeFrom.setOnClickListener(setOnClickListener("From", 1));
		EditLessonTimeUntil.setOnClickListener(setOnClickListener("Until", 1));
		EditLessonDateStartingOn.setOnClickListener(setOnClickListener("StartingOn", 0));
		EditLessonDateEndingOn.setOnClickListener(setOnClickListener("EndingOn", 0));
		
	}
	
	private OnClickListener setOnClickListener(final String s, final int t){
		OnClickListener O = new OnClickListener(){
			@Override
			public void onClick(View v){
				Bundle b = new Bundle();
				if (t == 0){
					b.putString("Type", s);
					DatePicker Date = new DatePicker();
					Date.setArguments(b);
					Date.show(getFragmentManager(), s);
				}
				else if (t == 1){
					b.putString("Type", s);
					TimePicker Time = new TimePicker();
					Time.setArguments(b);
					Time.show(getFragmentManager(), s);
				}
			}
		};
		return O;
	}
	
	private void Save(){
		Tracker mTracker = new Tracker(getActivity());
		HashMap<String, String> Lesson = new HashMap<String, String>();
		Lesson.put("LessonTitle", EditLessonTitle.getText().toString().trim());
		Lesson.put("LessonRoom", EditLessonRoom.getText().toString().trim());
		Lesson.put("LessonTeacher", EditLessonTeacher.getText().toString().trim());
		Lesson.put("LessonWeekdays", EditLessonWeekdays.getText().toString().trim());
		Lesson.put("LessonTimeFrom", EditLessonTimeFrom.getText().toString().trim());
		Lesson.put("LessonTimeUntil", EditLessonTimeUntil.getText().toString().trim());
		Lesson.put("LessonDateStartingOn", EditLessonDateStartingOn.getText().toString().trim());
		Lesson.put("LessonDateEndingOn", EditLessonDateEndingOn.getText().toString().trim());
		mTracker.setLessons(((CampusAgendaCourse) getActivity()).getCourse(), Lesson, OldLesson);
		getActivity().onBackPressed();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater Inflater){
		Inflater.inflate(R.menu.campus_agenda, menu);
		super.onCreateOptionsMenu(menu, Inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.AgendaSave:
			Save();
			getActivity().onBackPressed();
			return true;
		case R.id.AgendaGarbage:
			getActivity().onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void DatePickerCallback(String d, String t){
		if (t.equals("StartingOn")){
			EditLessonDateStartingOn.setText(d);
		}
		else if (t.equals("EndingOn")){
			EditLessonDateEndingOn.setText(d);
		}
	}
	
	public void TimePickerCallback(int h, int m, String t){
		if (t.equals("From")){
			EditLessonTimeFrom.setText(h + ":" + m);
		}
		else if (t.equals("Until")){
			EditLessonTimeUntil.setText(h + ":" + m);
		}
	}
	
	public void DayPickerCallback(ArrayList<CheckBox> Days){
		StringBuilder Builder = new StringBuilder();
		for (CheckBox B : Days){
			if (B.isChecked()){
				Builder.append(B.getText() + ",");
			}
		}
		EditLessonWeekdays.setText(Builder.toString());
	}
	
	public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle b){
			final Calendar c = Calendar.getInstance();
			int Year = c.get(Calendar.YEAR);
			int Month = c.get(Calendar.MONTH);
			int Day = c.get(Calendar.DAY_OF_MONTH);
			
			return new DatePickerDialog(getActivity(), this, Year, Month, Day);
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth) {
			LessonEditFragment mLessonEditFragment = (LessonEditFragment) CampusAgendaCourse.Fragments[0];
			mLessonEditFragment.DatePickerCallback(monthOfYear + "/" + dayOfMonth + "/" + year, getArguments().getString("Type"));
		}
		
	}
	
	public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle b){
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR);
			int minute = c.get(Calendar.MINUTE);
			
			return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(android.widget.TimePicker view, int hourOfDay,
				int minute) {
			LessonEditFragment mLessonEditFragment = (LessonEditFragment) CampusAgendaCourse.Fragments[0];
			mLessonEditFragment.TimePickerCallback(hourOfDay, minute, getArguments().getString("Type"));
		}
		
	}
	
	public static class DayPicker extends DialogFragment {
		
		private DaysAdapter mDaysAdapter = new DaysAdapter();
				
		@Override
		public Dialog onCreateDialog(Bundle b){
			AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
			ListView LV = new ListView(getActivity());
			LV.setAdapter(mDaysAdapter);
			Builder.setView(LV);		
			Builder.setPositiveButton("OK", OKClickListener);
			Builder.setNegativeButton("Cancel", CancelClickListener);
			return Builder.create();
		}
		
		private DialogInterface.OnClickListener OKClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LessonEditFragment mLessonEditFragment = (LessonEditFragment) CampusAgendaCourse.Fragments[0];
				mLessonEditFragment.DayPickerCallback(mDaysAdapter.getDays());
			}
		};
		
		private DialogInterface.OnClickListener CancelClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		
		private class DaysAdapter extends BaseAdapter {
			
			private final String[] Weekdays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
			private final ArrayList<CheckBox> Days = new ArrayList<CheckBox>();
			
			@Override
			public int getCount() {
				return Weekdays.length;
			}

			@Override
			public Object getItem(int position) {
				return Weekdays[position];
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null){
					CheckBox b = new CheckBox(getActivity());
					b.setText(Weekdays[position]);
					convertView = b;
					Days.add(b);
				}
				return convertView;
			}
			
			public ArrayList<CheckBox> getDays(){
				return Days;
			}
			
		}
	}

}
