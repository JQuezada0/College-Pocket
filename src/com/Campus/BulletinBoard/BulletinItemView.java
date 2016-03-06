package com.Campus.BulletinBoard;

import org.json.JSONException;
import org.json.JSONObject;

import com.Campus.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Class for getting and using a BulletinItem. Upon calling the constructor, the method CreateDigiView must be called
 * before using any other methods. Failure to do so may result in an NPE. The constructor is passed an activity to
 * serve as the context for the BulletinItem.
 */

public class BulletinItemView {
	
	private static LayoutInflater Inflater;
	private static View BulletinItem;
	private static LinearLayout BulletinItemLinearLayout1;
	private static ImageView BulletinItemImageView;
	private static TextView BulletinItemTextView1;
	private static TextView BulletinItemTextView2;
	private static TextView BulletinItemTextView3;
	private static Button BulletinItemButton;
	private static PopupWindow P;
	
	public BulletinItemView(Activity activity){
		Inflater = LayoutInflater.from(activity);
	}
	
	
	
	/**
	 * Gets the BulletinItem Created. {@link #CreateBulletinItem(ViewGroup)} must be called first, or else the returned
	 * BulletinItem will be null. 
	 * @return DigiView Created.
	 */
	
	
	
	public static View getDigiView(){
		return BulletinItem;
	}
	
	
	
	/**
	 * Inflates a BulletinItem from the Layout XML file and assigns each View to the corresponding
	 * piece of the BulletinItem
	 */
	
	
	
	public static void CreateBulletinItem(Activity a, ViewGroup Parent){
		Inflater = LayoutInflater.from(a);
		BulletinItem = Inflater.inflate(R.layout.digi_view_template, Parent, false);
		BulletinItemLinearLayout1 = (LinearLayout) BulletinItem.findViewById(R.id.DigiViewTemplateLinearLayout1);
		BulletinItemImageView = (ImageView) BulletinItem.findViewById(R.id.DigiViewImageView);
		BulletinItemTextView1 = (TextView) BulletinItem.findViewById(R.id.DigiViewTextView1);
		BulletinItemTextView2 = (TextView) BulletinItem.findViewById(R.id.DigiViewTextView2);
		BulletinItemTextView3 = (TextView) BulletinItem.findViewById(R.id.DigiViewTextView3);
		BulletinItemButton = (Button) BulletinItem.findViewById(R.id.DigiViewButton);
		BulletinItemButton.setOnClickListener(OptionsClickListener);
		P = new PopupOptions().PopUpOptionsCreate(Inflater.getContext(), new String[]{"Test1", "Test2"});
	}
	
	
	
	/**
	 * Method to set the main image of the BulletinItem
	 * @param bm A bitmap to set the image to
	 */
	public void setImage(Bitmap bm){
		BulletinItemImageView.setImageBitmap(bm);
	}
	
	
	
	/**
	 * Method to set the main image of the BulletinItem
	 * @param Id The drawable ID to set the ImageView to
	 */
	
	
	
	public void setImage(int Id){
		BulletinItemImageView.setImageResource(Id);
	}
	
	
	
	/**
	 * Sets the size of the BulletinItem. The only size changed is the size of the BulletinItem's Image, thus changing
	 * the size of the whole thing.  
	 * @param x Size in pixels denoting how large the BulletinItem should be. The Image will always be a square. 
	 */
	
	
	
	public static void setSize(int x){
		LinearLayout.LayoutParams Params = (LinearLayout.LayoutParams) BulletinItemImageView.getLayoutParams();
		Params.width = x;
		Params.height = x;
		BulletinItemImageView.setLayoutParams(Params);
	}
	
	
	
	/**
	 * Sets the padding of the BulletinItem
	 * @param x Padding in pixels
	 */
	
	
	
	public void setPadding(int x){
		BulletinItemLinearLayout1.setPadding(x, x, x, x);
	}
	
	
	
	/**
	 * Sets the first line of text in the BulletinItem
	 * @param s The text to set
	 */
	
	
	
	public void setText1(String s){
		BulletinItemTextView1.setText(s);
	}
	
	
	
	/**
	 * Sets the second line of text in the BulletinItem
	 * @param s The text to set
	 */
	
	
	
	public void setText2(String s){
		BulletinItemTextView2.setText(s);
	}
	
	
	
	/**
	 * Sets the third line of text in the BulletinItem
	 * @param s The text to set
	 */

	
	
	public void setText3(String s){
		BulletinItemTextView3.setText(s);
	}
	
	
	
	/**
	 * Sets all aspects of the BulletinItem. The Image is set, as well as the three TextViews. The Information is
	 * obtained from the passed JSONObject, which contains all the information for any given BulletinItem.
	 * @param o JSONObject containing the information of the BulletinItem. 
	 */
	
	
	
	public static void setInformation(JSONObject o){
		try {
			BulletinItemTextView1.setText(o.getString("Title"));
			BulletinItemTextView2.setText(o.getString("ViewCount"));
			BulletinItemTextView3.setText(o.getString("DateStamp"));
			UrlImageViewHelper.setUrlDrawable(BulletinItemImageView, o.getString("Images").split(System.getProperty("line.separator"))[0]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Click listener for the options button of the BulletinItem. Opens the pop-up listview. 
	 */
	
	
	
	private static OnClickListener OptionsClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			P.showAsDropDown(v);
		}
	};
	
	
	
	/**
	 * Class defines and controls the popup listview that appears when the options button is selected in a
	 * BulletinItem. Constructor takes a context and a string array of for items in the listview. 
	 * @author Johnil
	 *
	 */
	
	
	
	private static class PopupOptions extends BaseAdapter {
		
		/**
		 * String array of Items to put into the PopUpWindow's ListView.
		 */
		
		
		private String[] Options;
		private Context c;
		
		
		/**
		 * Sets the context of the class, and sets the global string array variable to the passed string array
		 * @param c Context
		 * @param s Items to be placed in ListView
		 * @return returns the method {@link #PopupOptionsWindow(Context, ListAdapter)} which returns a PopupWindow.
		 */
		
		
		public PopupWindow PopUpOptionsCreate(Context c, String[] s){
			this.c = c;
			Options = s;
			return PopupOptionsWindow(c, this);
		}
		
		
		/**
		 * Actually creates the PopupWindow and sets its view as a ListView. The ListView is also created
		 * and its adapter is set. The adapter of the ListView is also this class, as the class extends
		 * BaseAdapter. The width of the Popup window is set to 250.
		 * @param c Context for the PopWindow
		 * @param a ListAdapter for the ListView
		 * @return PopWindow containing a ListView of the items given from {@link #Options}
		 */
		
		
		private static final PopupWindow PopupOptionsWindow(Context c, ListAdapter a){
			PopupWindow p = new PopupWindow(c);
			ListView v = new ListView(c);
			v.setAdapter(a);
			p.setFocusable(true);
			p.setWidth(250);
			p.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
			p.setContentView(v);
			return p;
		}

		@Override
		public int getCount() {
			return Options.length;
		}

		@Override
		public Object getItem(int position) {
			return Options[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView v = new TextView(c);
			v.setText(Options[position]);
			v.setTextColor(Color.WHITE);
			return v;
		}
		
	}
	

}
