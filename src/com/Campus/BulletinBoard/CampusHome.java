package com.Campus.BulletinBoard;

import com.Campus.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CampusHome extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.digihome_fragment, container, false);
		MainActivity ParentActivity = (MainActivity) getActivity();
		MainActivity.ActionBarSorterSpinner.setVisibility(View.GONE);
        return v;
    }

}
