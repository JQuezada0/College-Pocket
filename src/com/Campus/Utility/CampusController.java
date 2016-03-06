package com.Campus.Utility;

public class CampusController {
	
	public static boolean MainActivityActive = false;
	
	public static boolean MessagingActivityActive = false;
	
	public static boolean CreateAccountActive = false;
	
	public static String Username = "";
	
	public static boolean Active(){
		if (MainActivityActive || MessagingActivityActive){
			return true;
		}
		else if (!MainActivityActive && !MessagingActivityActive) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static void ChangePage(){
		
	}

}
