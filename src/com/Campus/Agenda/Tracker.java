package com.Campus.Agenda;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;

public class Tracker {
	
	private SharedPreferences Prefs;
	private SharedPreferences.Editor Editor;
	private final Gson gson = new Gson();
	private Type gsonType;
	private Type gsonType2;
	
	public Tracker(Context c){
		Prefs = c.getSharedPreferences("CampusAgenda", 0);
		Editor = Prefs.edit();
		gsonType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
		gsonType2 = new TypeToken<HashSet<String>>(){}.getType();
	}
	
	public void setCourses(HashMap<String, String> Course, HashMap<String, String> OldCourse){
		ArrayList<HashMap<String, String>> Courses = getCourses();
		int Index = Courses.indexOf(OldCourse);
		if (Index >= 0){
			Courses.remove(Index);
			Courses.add(Index, Course);
		}
		else {
			Courses.add(Course);
		}
		Editor.putString("Courses", gson.toJson(Courses, gsonType));
		Editor.commit();
	}
	
	public void setTeachers(HashMap<String, String> Teacher, HashMap<String, String> OldTeacher){
		ArrayList<HashMap<String, String>> Teachers = getTeachers();
		int Index = Teachers.indexOf(OldTeacher);
		if (Index >= 0){
			Teachers.remove(Index);
			Teachers.add(Index, Teacher);
		}
		else {
			Teachers.add(Teacher);
		}
		Editor.putString("Teachers", gson.toJson(Teachers, gsonType));
		Editor.commit();
	}
	
	public void setHolidays(HashMap<String, String> Holiday, HashMap<String, String> OldHoliday){
		ArrayList<HashMap<String, String>> Holidays = getHolidays();
		int Index = Holidays.indexOf(OldHoliday);
		if (Index >= 0){
			Holidays.remove(Index);
			Holidays.add(Index, Holiday);
		}
		else{
			Holidays.add(Holiday);
		}
		Editor.putString("Holidays", gson.toJson(Holidays, gsonType));
		Editor.commit();
	}
	
	public void setLessons(HashMap<String, String> Course, HashMap<String, String> Lesson, HashMap<String, String> OldLesson){
		ArrayList<HashMap<String, String>> Courses = getCourses();
		ArrayList<HashMap<String, String>> Lessons = getLessons(Course);
		int Index = Courses.indexOf(Course);
		if (Index >=0){
			Courses.remove(Index);
		}
		int LessonIndex = Lessons.indexOf(OldLesson);
		if (LessonIndex >= 0){
			Lessons.remove(OldLesson);
			Lessons.add(LessonIndex, Lesson);
		}
		else {
		Lessons.add(Lesson);
		}
		Course.put("Lessons", gson.toJson(Lessons, gsonType));
		if (Index >=0){
			Courses.add(Index, Course);
		}
		else {
			Courses.add(Course);
		}
		Editor.putString("Courses", gson.toJson(Courses, gsonType));
		Editor.commit();
	}
	
	public void setHomework(HashMap<String, String> Course, HashMap<String, String> Homework, HashMap<String, String> OldHomework){
		ArrayList<HashMap<String, String>> Homeworks = getHomework(Course);
		ArrayList<HashMap<String, String>> Courses = getCourses();
		int Index = Courses.indexOf(Course);
		if (Index >=0){
			Courses.remove(Index);
		}
		int HomeworkIndex = Homeworks.indexOf(OldHomework);
		if (HomeworkIndex >= 0){
			Homeworks.remove(HomeworkIndex);
			Homeworks.add(Index, Homework);
		}
		else {
		Homeworks.add(Homework);
		}
		Course.put("Homework", gson.toJson(Homeworks, gsonType));
		if (Index >=0){
			Courses.add(Index, Course);
		}
		else{
		Courses.add(Course);
		}
		Editor.putString("Courses", gson.toJson(Courses, gsonType));
		Editor.commit();
	}
	
	public void setExams(HashMap<String, String> Course, HashMap<String, String> Exam, HashMap<String, String> OldExam){
		ArrayList<HashMap<String, String>> Courses = getCourses();
		ArrayList<HashMap<String, String>> Exams = getExams(Course);
		int Index = Courses.indexOf(Courses);
		if (Index >=0){
			Courses.remove(Index);
		}
		int ExamIndex = Exams.indexOf(OldExam);
		if (ExamIndex >= 0){
			Exams.remove(Index);
			Exams.add(Index, Exam);
		}
		else {
		Exams.add(Exam);
		}
		Course.put("Exams", gson.toJson(Exams, gsonType));
		if (Index >=0){
			Courses.add(Index, Course);
		}
		else {
			Courses.add(Course);
		}
		Editor.putString("Courses", gson.toJson(Courses, gsonType));
		Editor.commit();
	}
	
	public static HashMap<String, String> createCourse(){
		return new HashMap<String, String>();
	}
	
	public ArrayList<HashMap<String, String>> getCourses(){
		String s = Prefs.getString("Courses", gson.toJson(new ArrayList<HashMap<String, String>>(), gsonType));
		return gson.fromJson(s, gsonType);
	}
	
	public ArrayList<HashMap<String, String>> getTeachers(){
		String s = Prefs.getString("Teachers", gson.toJson(new ArrayList<HashMap<String, String>>(), gsonType));
		return gson.fromJson(s, gsonType);
	}
	
	public ArrayList<HashMap<String, String>> getHolidays(){
		String s = Prefs.getString("Holidays", gson.toJson(new ArrayList<HashMap<String, String>>(), gsonType));
		return gson.fromJson(s, gsonType);
	}
	
	public ArrayList<HashMap<String, String>> getLessons(HashMap<String, String> Course){
		if (Course.get("Lessons") == null)
			return new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> Lessons = gson.fromJson(Course.get("Lessons"), gsonType);
		return Lessons;
	}
	
	public ArrayList<HashMap<String, String>> getHomework(HashMap<String, String> Course){
		if (Course.get("Homework") == null)
			return new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> Homework = gson.fromJson(Course.get("Homework"), gsonType);
		return Homework;
	}
	
	public ArrayList<HashMap<String, String>> getExams(HashMap<String, String> Course){
		if (Course.get("Exams") == null)
			return new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> Exam = gson.fromJson(Course.get("Exams"), gsonType);
		return Exam;
	}
	
	public void deleteHoliday(HashMap<String, String> H){
		ArrayList<HashMap<String, String>> Holidays = getHolidays();
		Holidays.remove(H);
		Editor.putString("Holidays", gson.toJson(Holidays, gsonType));
		Editor.commit();
	}
	
	public void deleteTeacher(HashMap<String, String> H){
		ArrayList<HashMap<String, String>> Teachers = getTeachers();
		Teachers.remove(H);
		Editor.putString("Teachers", gson.toJson(Teachers, gsonType));
		Editor.commit();
	}
	
	public void deleteLesson(HashMap<String, String> H, HashMap<String, String> Course){
		
	}
	
	public HashSet<String> getAlarms(){
		HashSet<String> AlarmsSet = gson.fromJson(Prefs.getString("Alarms", gson.toJson(new HashSet<String>(), gsonType2)), gsonType2);
		HashSet<String> Alarms = new HashSet<String>();
		Alarms.addAll(AlarmsSet);
		return Alarms;
	}
	
	public void setAlarm(String uri){
		HashSet<String> AlarmsSet = gson.fromJson(Prefs.getString("Alarms", gson.toJson(new HashSet<String>(), gsonType2)), gsonType2);
		HashSet<String> Alarms = new HashSet<String>();
		Alarms.addAll(AlarmsSet);
		Alarms.add(uri);
		Editor.remove("Alarms");
		Editor.commit();
		Editor.putString("Alarms", gson.toJson(Alarms, gsonType2));
		Editor.commit();
	}
	
	public void RemoveAlarm(String uri){
		HashSet<String> AlarmsSet = gson.fromJson(Prefs.getString("Alarms", gson.toJson(new HashSet<String>(), gsonType2)), gsonType2);
		HashSet<String> Alarms = new HashSet<String>();
		Alarms.addAll(AlarmsSet);
		Alarms.remove(uri);
		Editor.remove("Alarmas");
		Editor.commit();
		Editor.putString("Alarms", gson.toJson(Alarms, gsonType2));
		Editor.commit();
	}
	
	

}
