package com.Campus.Utility;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SorterAdapter extends BaseAdapter {
	
	private String[] Items;
	private Context mContext;
	
	public SorterAdapter(String[] i, Context c){
		Items = i;
		mContext = c;
	}

	@Override
	public int getCount() {
		return Items.length;
	}

	@Override
	public Object getItem(int position) {
		return Items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(com.Campus.R.layout.sorter_adapter, null);
		TextView v = (TextView) convertView.findViewById(com.Campus.R.id.DigiSorterTV);
		v.setText(Items[position]);
		return convertView;
	}

}
