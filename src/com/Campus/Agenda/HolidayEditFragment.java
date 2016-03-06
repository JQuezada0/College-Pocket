package com.Campus.Agenda;

import java.util.Calendar;
import java.util.HashMap;

import com.Campus.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
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

public class HolidayEditFragment extends Fragment{
	
	private EditText EditHolidayName;
	private TextView EditHolidayStartingOn;
	private TextView EditHolidayEndingOn;
	private TextView EditHolidayColor;
	private HashMap<String, String> OldHoliday;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Parent, Bundle savedInstanceState){
		View v = Inflater.inflate(R.layout.campusagenda_holidays_fragment_edit, Parent, false);
		setFields(v, getArguments());
		this.setHasOptionsMenu(true);
		return v;
	}
	
	private void setFields(View v, Bundle b){
		EditHolidayName = (EditText) v.findViewById(R.id.EditHolidayName);
		EditHolidayStartingOn = (TextView) v.findViewById(R.id.EditHolidayStartingOn);
		EditHolidayEndingOn = (TextView) v.findViewById(R.id.EditHolidayEndingOn);
		EditHolidayColor = (TextView) v.findViewById(R.id.EditHolidayColor);
		
		EditHolidayStartingOn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				DatePicker p = new DatePicker();
				Bundle b = new Bundle();
				b.putString("Type", "Start");
				p.setArguments(b);
				p.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentStyle);
			    p.show(getFragmentManager(), "StartDate");
			}
		});
		
		EditHolidayEndingOn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				DatePicker p = new DatePicker();
				Bundle b = new Bundle();
				b.putString("Type", "End");
				p.setArguments(b);
			    p.show(getFragmentManager(), "EndDate");
			}
		});
		
		EditHolidayColor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				new ColorPickerDialog().show(getFragmentManager(), "ColorPicker");
			}
			
		});
		
		if (b!=null){
			HashMap<String, String> Holiday = new Gson().fromJson(b.getString("Holiday"), new TypeToken<HashMap<String, String>>(){}.getType());
			OldHoliday = Holiday;
			EditHolidayName.setText(Holiday.get("Title"));
			EditHolidayStartingOn.setText(Holiday.get("StartDate"));
			EditHolidayEndingOn.setText(Holiday.get("EndDate"));
			EditHolidayColor.setBackgroundColor(Color.parseColor(Holiday.get("Color")));
			EditHolidayColor.setTag(Holiday.get("Color"));
		}
	}
	
	private void Save(){
		Tracker mTracker = new Tracker(getActivity());
		HashMap<String, String> Holiday = new HashMap<String, String>();
		Holiday.put("Title", EditHolidayName.getText().toString().trim());
		Holiday.put("StartDate", EditHolidayStartingOn.getText().toString());
		Holiday.put("EndDate", EditHolidayEndingOn.getText().toString());
		Holiday.put("Color", (String) EditHolidayColor.getTag());
		mTracker.setHolidays(Holiday, OldHoliday);
		getActivity().onBackPressed();
	}
	
	public void DatePickerCallback(String d, String s){
		if (s.equals("Start")){
			EditHolidayStartingOn.setText(d);
		}
		if (s.equals("End")){
			EditHolidayEndingOn.setText(d);
		}
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
	
	public static class ColorPickerDialog extends DialogFragment  {
		
		@Override
		public Dialog onCreateDialog(Bundle b){
			final ColorPicker Picker = new ColorPicker(getActivity());
			AlertDialog.Builder Builder = new AlertDialog.Builder(getActivity());
			Builder.setView(Picker);
			Builder.setPositiveButton("Set Color", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					((HolidayEditFragment) CampusAgenda.ViewPagerFragments[2]).onColorChanged(Picker.getColor());
				}
			});
			return Builder.create();
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
			HolidayEditFragment mHolidayEditFragment = (HolidayEditFragment) CampusAgenda.ViewPagerFragments[2];
			mHolidayEditFragment.DatePickerCallback(monthOfYear + "/" + dayOfMonth + "/" + year, getArguments().getString("Type"));
		}
		
	}

	public void onColorChanged(int color) {
		String ColorS = String.format("#%06X", 0xFFFFFF & color);
		EditHolidayColor.setBackgroundColor(Color.parseColor(ColorS));
		EditHolidayColor.setTag(ColorS);
	}

}
