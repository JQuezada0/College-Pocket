package com.Campus.BulletinBoard;

import java.util.HashMap;

/**
 * Interface for handling various callbacks. See {@link #onTaskCompleted(HashMap)}, 
 * See {@link #onActivityResult(String)}, See {@link #onSort(int)}
 * @author Johnil
 *
 */

public interface GeneralCallback {
	
	/**
	 * Called by an AsyncTask to denote the finishing of the Task.
	 * @param H The results of the AsyncTask. Mappings could contain Error Messages or downloaded objects. 
	 */

	public void onTaskCompleted(HashMap<String, String> H);
	
	public void onActivityResult(String Path);
	
	public void onSort(int i);

}