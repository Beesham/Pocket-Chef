package com.beesham.pocketchef.ui;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.beesham.pocketchef.R;

public class TitleNavigationAdapter extends BaseAdapter {

	private ImageView imgIcon;
	private Context context;

	public TitleNavigationAdapter(Context context) {
		this.context = context;
	}

	

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
        if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_title_navigation, null);
        }
        
        imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);        
        imgIcon.setVisibility(View.GONE);
        return convertView;
	}
	

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
        	LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_title_navigation, null);
        }
        
        imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        return convertView;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
