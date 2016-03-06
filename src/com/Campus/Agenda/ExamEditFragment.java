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

public class ExamEditFragment extends Fragment {
	
	private EditText EditExamTitle;
	private TextView EditExamDate;
	private EditText EditExamRoom;
	private EditText EditExamDescription;
	private EditText EditExamGrade;
	private HashMap<String, String> OldExam;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle onInstanceSave){
		View v = Inflater.inflate(R.layout.campusagenda_exams_fragment_edit, Parent, false);
		setFields(v, getArguments());
		setHasOptionsMenu(true);
		return v;
	}
	
	private void setFields(View v, Bundle b){
		EditExamTitle = (EditText) v.findViewById(R.id.EditExamTitle);
		EditExamDate = (TextView) v.findViewById(R.id.EditExamDate);
		EditExamRoom = (EditText) v.findViewById(R.id.EditExamRoom);
		EditExamDescription = (EditText) v.findViewById(R.id.EditExamDescription);
		EditExamGrade = (EditText) v.findViewById(R.id.EditExamGrade);
		
		if (b!=null){
			HashMap<String, String> Exam = new Gson().fromJson(b.getString("Exam"), new TypeToken<HashMap<String, String>>(){}.getType());
			OldExam = Exam;
			EditExamTitle.setText(Exam.get("ExamTitle"));
			EditExamDate.setText(Exam.get("ExamDate"));
			EditExamRoom.setText(Exam.get("ExamRoom"));
			EditExamDescription.setText(Exam.get("ExamDescription"));
			EditExamGrade.setText(Exam.get("ExamGrade"));
		}
		
		EditExamDate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new DatePicker().show(getFragmentManager(), "ExamDate");
			}
			
		});
		
		
	}
	
	private void Save(){
		Tracker mTracker = new Tracker(getActivity());
		HashMap<String, String> Exam = new HashMap<String, String>();
		Exam.put("ExamTitle", EditExamTitle.getText().toString().trim());
		Exam.put("ExamDate", EditExamDate.getText().toString().trim());
		Exam.put("ExamRoom", EditExamRoom.getText().toString().trim());
		Exam.put("ExamDescription", EditExamDescription.getText().toString().trim());
		Exam.put("ExamGrade", EditExamGrade.getText().toString().trim());
		mTracker.setExams(((CampusAgendaCourse) getActivity()).getCourse(), Exam, OldExam);
		getActivity().onBackPressed();
	}
	
	public void DatePickerCallback(String d){
		EditExamDate.setText(d);
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
	
	public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			final Calendar c = Calendar.getInstance();
			int Year = c.get(Calendar.YEAR);
			int Month = c.get(Calendar.MONTH);
			int Day = c.get(Calendar.DAY_OF_MONTH);
			
			return new DatePickerDialog(getActivity(), this, Year, Month, Day);
			
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth) {
			ExamEditFragment mExamEditFragment = (ExamEditFragment) CampusAgendaCourse.Fragments[2];
			mExamEditFragment.DatePickerCallback(monthOfYear + "/" + dayOfMonth + "/" + year);
		}
		
	}

}
