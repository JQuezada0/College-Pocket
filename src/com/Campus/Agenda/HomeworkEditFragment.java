package com.Campus.Agenda;

import java.util.Calendar;
import java.util.HashMap;

import com.Campus.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class HomeworkEditFragment extends Fragment {
	
	private EditText EditHomeworkTitle;
	private TextView EditHomeworkDueDate;
	private EditText EditHomeworkDescription;
	private EditText EditHomeworkGrade;
	private HashMap<String, String> OldHomework;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle savedInstanceState){
		View v = Inflater.inflate(R.layout.campusagenda_homework_fragment_edit, Parent, false);
		setFields(v, getArguments());
		setHasOptionsMenu(true);
		return v;
	}
	
	private void setFields(View v, Bundle b){
		EditHomeworkTitle = (EditText) v.findViewById(R.id.EditHomeworkTitle);
		EditHomeworkDueDate = (TextView) v.findViewById(R.id.EditHomeworkDueDate);
		EditHomeworkDescription = (EditText) v.findViewById(R.id.EditHomeworkDescription);
		EditHomeworkGrade = (EditText) v.findViewById(R.id.EditHomeworkGrade);
		
		if (b!=null){
			HashMap<String, String> Homework = new Gson().fromJson(b.getString("Homework"), new TypeToken<HashMap<String, String>>(){}.getType());
			OldHomework = Homework;
			EditHomeworkTitle.setText(Homework.get("HomeworkTitle"));
			EditHomeworkDueDate.setText(Homework.get("HomeworkDueDate"));
			EditHomeworkDescription.setText(Homework.get("HomeworkDescription"));
			EditHomeworkGrade.setText(Homework.get("HomeworkGrade"));
		}
		
		EditHomeworkDueDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				new DatePicker().show(getFragmentManager(), "HomeworkDueDate");
			}
		});
	}
	
	private void Save(){
		Tracker mTracker = new Tracker(getActivity());
		HashMap<String, String> Homework = new HashMap<String, String>();
		Homework.put("HomeworkTitle", EditHomeworkTitle.getText().toString().trim());
		Homework.put("HomeowrkDueDate", EditHomeworkDueDate.getText().toString().trim());
		Homework.put("HomeworkDescription", EditHomeworkDescription.getText().toString().trim());
		Homework.put("HomeworkGrade", EditHomeworkGrade.getText().toString().trim());
		mTracker.setHomework(((CampusAgendaCourse) getActivity()).getCourse(), Homework, OldHomework);
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
	
	public void DatePickerCallback(String d){
		EditHomeworkDueDate.setText(d);
	}
	
	public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle onSavedInstance){
			final Calendar c = Calendar.getInstance();
			int Year = c.get(Calendar.YEAR);
			int Month = c.get(Calendar.MONTH);
			int Day = c.get(Calendar.DAY_OF_MONTH);
			
			return new DatePickerDialog(getActivity(), this, Year, Month, Day);
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth) {
			HomeworkEditFragment mHomeworkEditFragment = (HomeworkEditFragment) CampusAgendaCourse.Fragments[1];
			mHomeworkEditFragment.DatePickerCallback(monthOfYear + "/" + dayOfMonth + "/" + year);
			
		}
		
	}

}
