package com.beesham.pocketchef;
import java.util.ArrayList;

import com.beesham.pocketchef.image.loader.ImageLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.pocket.chef.image.loader;

public class ArrayAdapt extends ArrayAdapter<Recipes> {

	private ArrayList<Recipes> Robj;
	Context context;
	
	Recipes Rec;
	public ArrayAdapt(Context context, int textViewResourceId,
			ArrayList<Recipes> objects) {
		super(context,textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.Robj = objects;
		this.context = context;
		
	}

	static class ViewHolder{
		TextView textTitle;
		//TextView textDes;
		ImageView img;
		int position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder viewHolder; 
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.customlist, null);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView) convertView.findViewById(R.id.imageView1);		
			viewHolder.textTitle = (TextView) convertView.findViewById(R.id.title);
			//viewHolder.textDes = (TextView) convertView.findViewById(R.id.des);
			viewHolder.position = position;
			convertView.setTag(viewHolder);
		}else {
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
		
		Rec = Robj.get(position);
		
		if(Rec != null){
			//ImageView img = (ImageView) convertView.findViewById(R.id.imageView1);
			
			viewHolder.textTitle.setText(Rec.getTitle());
			//viewHolder.textDes.setText(Rec.getDescription());
			viewHolder.textTitle.setText(Rec.getTitle());
			//viewHolder.textDes.setText(Rec.getDescription());
			if(Rec.getImg()==null){
				viewHolder.img.setImageDrawable((Drawable)context.getApplicationContext().getResources().getDrawable(R.drawable.ic_launcher));
			}
			//else new ImageDownload(Rec.getImg(), convertView).execute();
			else new ImageLoader(context).DisplayImage(Rec.getImg(), viewHolder.img);
		}
		return convertView;
	}
	
}
