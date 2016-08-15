package com.beesham.pocketchef;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageDownload extends AsyncTask <Void, Void, Bitmap> {
	
	private String url;
	private ImageView imageView;
	private ProgressBar progressBar;
	
	public ImageDownload(String url, View v){
		this.url = url;
		this.imageView = (ImageView) v.findViewById(R.id.imageView1);
		//this.progressBar = (ProgressBar) v.findViewById(R.id.imgProgress);
	}
	
	@Override
	protected Bitmap doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try{
			URL urlcon = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlcon.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			Bitmap bit = BitmapFactory.decodeStream(in);
			return bit;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		//imageView.setVisibility(android.view.View.INVISIBLE);
		//progressBar.setVisibility(android.view.View.GONE);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		imageView.setImageBitmap(result);
		//progressBar.setVisibility(android.view.View.GONE);
		//imageView.setVisibility(android.view.View.VISIBLE);
	}
	

}
