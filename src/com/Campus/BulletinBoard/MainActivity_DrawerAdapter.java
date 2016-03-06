package com.Campus.BulletinBoard;

import java.util.ArrayList;

import com.Campus.R;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Class that extends {@link BaseExpandableListAdapter}. Serves as the adapter for the DrawerLayout in 
 * {@link MainActivity}
 * @author Johnil
 *
 */

public class MainActivity_DrawerAdapter extends BaseExpandableListAdapter {
	
	private String[] DigiDrawerListItems;
	private Drawable[] DigiDrawerGroupListIcons;
	private LayoutInflater Inflater;
	private ArrayList<String[]> Children;
	
	public MainActivity_DrawerAdapter(String[] s, Drawable[] d, LayoutInflater I, ArrayList<String[]> a){
		DigiDrawerListItems = s;
		DigiDrawerGroupListIcons = d;
		Inflater = I;
		Children = a;
	}

	@Override
	public int getGroupCount() {
		return DigiDrawerListItems.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return Children.get(groupPosition).length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return DigiDrawerListItems[groupPosition];
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return Children.get(groupPosition)[childPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = Inflater.inflate(R.layout.digidraweritem, null);
		TextView v = (TextView) convertView.findViewById(R.id.DigiDrawerItemTV);
		ImageView I = (ImageView) convertView.findViewById(R.id.DigiDrawerIconIV);
		v.setText(DigiDrawerListItems[groupPosition]);
		I.setBackground(DigiDrawerGroupListIcons[groupPosition]);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (Children.get(groupPosition).length != 0){
		convertView = Inflater.inflate(R.layout.digidraweritem_sp, null);
		TextView v = (TextView) convertView.findViewById(R.id.DigiDrawerItemTVSP);
		v.setText(Children.get(groupPosition)[childPosition]);
		return convertView;
		}
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}