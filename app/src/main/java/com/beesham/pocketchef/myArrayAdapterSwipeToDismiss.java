package com.beesham.pocketchef;
import java.util.List;

import com.beesham.pocketchef.R;
import com.beesham.pocketchef.R.id;
import com.beesham.pocketchef.R.layout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
//import com.workout.logger.free.R;

public class myArrayAdapterSwipeToDismiss extends ArrayAdapter<String> {
	
	  private final List<String> list;
	  private final Activity context;

	  public myArrayAdapterSwipeToDismiss(Activity context, List<String> list) {
	    super(context, R.layout.rowlayout_swipe_to_dismiss, list);
	    this.context = context;
	    this.list = list;
	  }

	  static class ViewHolder {
		    protected TextView text;
		  }
		
	 @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    
	    if (convertView == null) {
		      LayoutInflater inflator = context.getLayoutInflater();
		      convertView = inflator.inflate(R.layout.rowlayout_swipe_to_dismiss, null);
		      final ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) convertView.findViewById(R.id.label);      
		      convertView.setTag(viewHolder);   
		    } 
		   
		    ViewHolder holder = (ViewHolder) convertView.getTag();
		    holder.text.setText(list.get(position));
		    return convertView;
	  }
	 
	 public void remove(int position) {
		 list.remove(position);
         notifyDataSetChanged();
     }

} 

