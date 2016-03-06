package com.Campus.BulletinBoard;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Campus.R;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

/**
 * Fragment that serves as the general display for any CampusItems. Any time a new section
 * is selected, this same fragment is used, and different information is loaded. 
 * @author Johnil
 *
 */


public class BulletinBoard extends Fragment implements GeneralCallback {
	
	private static final HashMap<String, String> Info = new HashMap<String, String>();
	private JSONArray Posts;
	private LinearLayout BulletinBoardLayout;
	private LinearLayout.LayoutParams BoardItemRowLP;
	private View BoardItem;
	private static ProgressBar mProgress;
	private static FragmentActivity ParentActivity;
	private TableRow BoardItemRow;
	private View BulletinBoardFragment;
	int DigiViewWidth;
	
	@Override
	public View onCreateView(LayoutInflater Inflater, ViewGroup Container, Bundle SavedInstanceState){
		View v = Inflater.inflate(R.layout.digigenfrag_fragment, Container, false);
		BulletinBoardFragment = v;
		return v;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Init(BulletinBoardFragment);
		Download(this);
	}
	
	/**
	 * Sets the information for the AsyncTask from a bundle. The information consists of a location, which is a column
	 * in the database of BoardItems, and a pattern, which denotes which rows to select from the column. Also sets the
	 * variable {@link #ParentActivity}, which serves as the context for this fragment.  
	 * @param Args The bundle passed by {@link MainActivity}
	 * @param a The FragmentActivity to set {@link #ParentActivity} to.
	 */
	
	
	private static final void setInfo(Bundle Args, FragmentActivity a){
		Info.put("Location", Args.getString("Location"));
		Info.put("Pattern", Args.getString("Pattern"));
		ParentActivity = a;
	}
	
	
	
	/**
	 * Starts the AsyncTask to download BoardItems
	 * @param O Instance of GeneralCallback to call when the AsyncTask is completed
	 */
	
	
	
	private static final void Download(GeneralCallback O){
			NetworkUtility Network = new NetworkUtility(ParentActivity);
			Network.DownloadNewDigiPost(Info, O);
			Network.execute();
	}
	
	
	
	/**
	 * Is called by {@link BulletinBoard#onTaskCompleted(HashMap)}.
	 *All posts are placed into a JSONArray and then passed in to this method as one of the mappings in the HashMap 
	 * @param H Contains the information that was given by the completed AsyncTask. In one of the mappings
	 * is a JSONArray containing the retrieved BoardItems
	 * @throws JSONException
	 */
	
	
	
	private void Decode(HashMap<String, String> H) throws JSONException{
		Posts = new JSONArray(H.get("Message"));
		JSONObject Post;
		int DigiViewNumber = 0;
		for (int x = 0; x<Posts.length(); x++){
			Post = Posts.getJSONObject(x);
			BoardItem = getActivity().getLayoutInflater().inflate(R.layout.digi_view_template, BoardItemRow, false);
			int Screen = getActivity().getWindow().getDecorView().getWidth();
			BulletinItemView.CreateBulletinItem(getActivity(), BoardItemRow);
			BulletinItemView.setInformation(Post);
			BulletinItemView.setSize((Screen/2) - 20);
			BoardItem = BulletinItemView.getDigiView();
			BoardItemRow.addView(BoardItem);
			DigiViewNumber++;
			if (DigiViewNumber == 2){
				InitRow();
				DigiViewNumber = 0;
			}
		}
		BulletinBoardLayout.setPadding(5, 5, 5, 5);
	}
	
	
	
	/**
	 * Is called when there is not any more room left on the previous row. A new row is initialized 
	 * and added to the screen. 
	 */
	
	
	
	private void InitRow(){
		BoardItemRow = new TableRow(getActivity());
		BoardItemRow.setLayoutParams(BoardItemRowLP);
		BulletinBoardLayout.addView(BoardItemRow);
	}
	
	
	
	/**
	 * Should the AsyncTask fail to retrieve the BoardItems, this method is called to handle any errors.
	 * @param H The HashMap containing the error messages passed by the AsyncTask
	 */
	
	
	
	private void ErrorHandling(HashMap<String, String> H){
		System.out.println(H.get("Response"));
		System.out.println(H.get("Error"));
		System.out.println(H.get("Message"));
		Toast.makeText(getActivity(), H.get("Response"), Toast.LENGTH_SHORT).show();
	}
	
	
	
	/**
	 * Initializes the page by setting each view to it's corresponding view in the XML layout. 
	 * Additionally by calling {@link BulletinBoard#setInfo} and {@link BulletinBoard#startLoadingAnimation} as well as setting
	 * the visibility of the sorter in the action bar to true. 
	 * @param v The view of the entire fragment 
	 */
	
	
	
	private void Init(View v){
		setInfo(getArguments(), getActivity()); 
		BoardItemRow = (TableRow) v.findViewById(R.id.DigiGenFragTR); //The Table Row to hold each BoardItem
		BulletinBoardLayout = (LinearLayout) v.findViewById(R.id.DigiGenFragLayout); //The Layout to add each Table Row to
		mProgress = (ProgressBar) v.findViewById(R.id.DigiGenFragProgressBar); //The ProgressBar view to show until the page is loaded
		BoardItemRowLP = (LayoutParams) BoardItemRow.getLayoutParams();
		BoardItemRow = new TableRow(ParentActivity);
		BoardItemRow.setLayoutParams(BoardItemRowLP);
		startLoadingAnimation();
		MainActivity.ActionBarSorterSpinner.setVisibility(View.VISIBLE);
	
		BulletinBoardLayout.addView(BoardItemRow);
	}
	
	
	
	/**
	 * Starts the loading animation to display while each BoardItem is loaded
	 */
	
	
	
	private static final void startLoadingAnimation(){
		AnimationDrawable DigiLoading = (AnimationDrawable) mProgress.getProgressDrawable();
		DigiLoading.start();
	}
	
	

	@Override
	public void onTaskCompleted(HashMap<String, String> H) {
		if(Integer.parseInt(H.get("Error")) != 0){
			ErrorHandling(H);
		}
		else{
		try {
			this.Decode(H);
			mProgress.setVisibility(View.GONE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		}
	}
	
	

@Override
public void onActivityResult(String Path) {
}

@Override
public void onSort(int i) {
	
	
}



}
