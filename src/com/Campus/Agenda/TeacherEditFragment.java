package com.Campus.Agenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.Campus.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.larswerkman.holocolorpicker.ColorPicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class TeacherEditFragment extends Fragment {
	
	private EditText EditTeacher;
	private EditText EditOffice;
	private EditText EditEmail;
	private TextView EditWeekday;
	private TextView EditFrom;
	private TextView EditUntil;
	private TextView EditColor;
	private static ListView WeekdaysListView;
	private HashMap<String, String> OldTeacher;
	private WeekdaysDialog WD = new WeekdaysDialog();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup Parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.campusagenda_teachers_fragment_edit, Parent, false);
		setFields(v, getArguments());
		setHasOptionsMenu(true);
		return v;
	}
	
	private void Save(){
	//	if (EditTeacher.equals("") | EditOffice.equals("") | EditEmail.equals("") | EditWeekday.equals("")
	//			| )
		HashMap<String, String> Teacher = new HashMap<String, String>();
		Teacher.put("Title", EditTeacher.getText().toString().trim());
		Teacher.put("Room", EditOffice.getText().toString().trim());
		StringBuilder Builder = new StringBuilder();
		ArrayList<CheckBox> CheckBoxes = new ArrayList<CheckBox>();
		for (int x = 0; x<WeekdaysListView.getCount(); x++){
			
			CheckBox c = (CheckBox) WeekdaysListView.getItemAtPosition(x);
			if (c.isChecked()){
				CheckBoxes.add(c);
			}
		}
		
		Iterator<CheckBox> It = CheckBoxes.iterator();
		while (It.hasNext()){
			Builder.append(It.next().getText());
			if (It.hasNext()){
				Builder.append(",");
			}
		}
		Teacher.put("Days", Builder.toString());
		Teacher.put("Time", EditFrom.getText() + " - " + EditUntil.getText());
		Teacher.put("Days", Builder.toString());
		Teacher.put("Color", (String) EditColor.getTag());
		Tracker tracker = new Tracker(getActivity());
		tracker.setTeachers(Teacher, OldTeacher);
		getActivity().onBackPressed();
	}
	
	private void setFields(View v, Bundle b){
		EditTeacher = (EditText) v.findViewById(R.id.EditTeacherTitle);
		EditOffice = (EditText) v.findViewById(R.id.EditTeacherRoom);
		EditEmail = (EditText) v.findViewById(R.id.EditTeacherEmail);
		EditWeekday = (TextView) v.findViewById(R.id.EditTeacherWeekday);
		EditFrom = (TextView) v.findViewById(R.id.EditTeacherFrom);
		EditUntil = (TextView) v.findViewById(R.id.EditTeacherUntil);
		EditColor = (TextView) v.findViewById(R.id.EditTeacherColor);
		WeekdaysListView = new ListView(getActivity());
		WeekdaysListView.setAdapter(new WeekdayAdapter("", getActivity()));
		
		EditWeekday.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				WD.show(getFragmentManager(), "Days");
			}
			
		});
		
		
		EditFrom.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				FromUntil From = new FromUntil();
				Bundle b = new Bundle();
				b.putString("Type", "From");
				From.setArguments(b);
				From.show(getFragmentManager(), "From");
			}
		});
		EditUntil.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				FromUntil Until = new FromUntil();
				Bundle b = new Bundle();
				b.putString("Type", "Until");
				Until.setArguments(b);
				Until.show(getFragmentManager(), "Until");
			}
			
		});
		
		EditColor.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new ColorPickerDialog().show(getFragmentManager(), "ColorPicker");
			}
		});
		
		
		if (b!=null){
			HashMap<String, String> Teacher = new Gson().fromJson(b.getString("Teacher"), new TypeToken<HashMap<String, String>>(){}.getType());
			OldTeacher = Teacher;
			EditTeacher.setText(Teacher.get("Title"));
			EditOffice.setText(Teacher.get("Room"));
			EditEmail.setText(Teacher.get("Email"));
			EditColor.setBackgroundColor(Color.parseColor(Teacher.get("Color")));
			EditColor.setTag(Teacher.get("Color"));
			WeekdaysListView.setAdapter(new WeekdayAdapter(Teacher.get("Days"), getActivity()));
		}
	}
	
	public void TimePickerCallBack(String s, int Hour, int Minute){
		if (s.equals("From")){
			EditFrom.setText(Hour + ":" + Minute);
		}
		else if (s.equals("Until")){
			EditUntil.setText(Hour + ":" + Minute);
		}
	}
	
	public void onColorChanged(long i){
		EditColor.setBackgroundColor(Color.parseColor(String.format("#%06X", 0xFFFFFF & i)));
		EditColor.setTag(String.format("#%06X", 0xFFFFFF & i));
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
			return true;
		case R.id.AgendaGarbage:
			getActivity().onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class WeekdayAdapter extends BaseAdapter {
		
		private String Content;
		private Context c;
		private final String[] Weekdays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		private CheckBox[] CBs = new CheckBox[7];
		
		public WeekdayAdapter(String Content, Context c){
			this.Content = Content;
			this.c = c;
			for (int x = 0; x<Weekdays.length; x++){
				getView(x, null, null);
			}
		}

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int position) {
			return CBs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null){
				convertView = getCheckBox(Weekdays, c, position);
			}
			return convertView;
		}
		
		private CheckBox getCheckBox(String[] Weekdays, Context c, int Position){
			CheckBox CB = new CheckBox(c);
			CB.setText(Weekdays[Position]);
			String[] S = Content.split(",");
			for (String s : S){
				if (s.equals(Weekdays[Position])){
					CB.setChecked(true);
				}
			}
			CBs[Position] = CB;
			return CB;
		}
		
	}
	
	public static class WeekdaysDialog extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle B){
			AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
			if (WeekdaysListView.getParent() != null){	
				((ViewGroup) WeekdaysListView.getParent()).removeView(WeekdaysListView);
			}
			Builder.setView(WeekdaysListView);
			Builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			return Builder.create();
		}
	}
	
public static class ColorPickerDialog extends DialogFragment  {
		
		@Override
		public Dialog onCreateDialog(Bundle b){
			final ColorPicker Picker = new ColorPicker(getActivity());
			AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
			Builder.setView(Picker);
			Builder.setPositiveButton("Set Color", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					((TeacherEditFragment) CampusAgenda.ViewPagerFragments[1]).onColorChanged(Picker.getColor());
				}
			});
			return Builder.create();
		}
		
	}
	
public static class FromUntil extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR);
			int minute = c.get(Calendar.MINUTE);
			
			return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			TeacherEditFragment mTeacherEditFragment = (TeacherEditFragment) CampusAgenda.ViewPagerFragments[1];
			mTeacherEditFragment.TimePickerCallBack(getArguments().getString("Type"), hourOfDay > 12 ? hourOfDay - 12 : hourOfDay, minute);
		}
		
	}

}
