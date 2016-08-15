package com.beesham.pocketchef;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RssFeed extends AsyncTask<String, Integer,ArrayList<Recipes>> {
	// Declaring a Recipe object
	ArrayList<Recipes> list = new ArrayList<Recipes>();
	Recipes recipes;
	String description,title,link,img;
	Context context;
	
	public RssFeed(Context context){
		this.context = context;
	}
	
	@Override
	protected ArrayList<Recipes> doInBackground(String... urlgot) {
		// TODO Auto-generated method stub
		
		try {
			// Assign url string to URL type
			URL url = new URL(urlgot[0]);
			String text = null;
			
			// Open URL Connection 
			HttpURLConnection connect = (HttpURLConnection) url.openConnection();
			connect.connect();
			
			//Reading the xml
			InputStream inStream = connect.getInputStream();
			
			//Declaring XML Pull parser
			XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
			XmlPullParser par = fac.newPullParser();
			
			par.setInput(inStream,null);
			
			//Reading Xml
			int EventType = par.getEventType();
				while(EventType != XmlPullParser.END_DOCUMENT){
					recipes = new Recipes(title, description, link,img);

					switch(EventType){
					
						case XmlPullParser.START_TAG:
							break;
						case XmlPullParser.TEXT:
							text = par.getText();
							break;
						case XmlPullParser.END_TAG:
								if(par.getName().equalsIgnoreCase("title")){
									title = text;
									recipes.setTitle(title);
								}
								else if(par.getName().equalsIgnoreCase("description")){
										
										int first = text.indexOf("<p>");
										int second = text.lastIndexOf("<p>");
										int imgTag = text.indexOf("<img");
										if(imgTag == -1){
											Log.d("No img","No image tag found");
										}else{
											String Temp = text.substring(imgTag);
											int imgETag = Temp.indexOf("/>");
											if(imgETag == -1){
												Log.d("No img","Return -1");
											}else{
												Log.d("Found img","An image was found");
												Temp = Temp.substring(0, imgETag);
												
												int src = Temp.indexOf('"');
												Temp = Temp.substring(src+1);
												Temp = Temp.substring(0, Temp.indexOf('"'));
												img = Temp;
												Log.d("Found img",img);
												recipes.setImg(img);
											}
										}
										
										if(first == -1){
											description = text;
											
											description = description.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
											description = description.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
											description = description.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
											description = description.replaceAll("&nbsp;"," ");
											description = description.replaceAll("&amp;"," ");
											description = description.replaceAll("&#8217;"," ");
											recipes.setDescription(description);
											
										}else{
											description = text.substring(first+3,second);
											
											description = description.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
											description = description.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
											description = description.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
											description = description.replaceAll("&nbsp;"," ");
											description = description.replaceAll("&amp;"," ");
											description = description.replaceAll("&#8217;"," ");
											
											recipes.setDescription(description);
										}
								}
								else if (par.getName().equalsIgnoreCase("link")){
									link = text;
									recipes.setLink(link);
								}
								else if(par.getName().equalsIgnoreCase("item")){
									list.add(recipes);
									Log.d("item", title + description + img);
								}
							break;
					}
					EventType = par.next();
				}
				Log.d("in recipe",recipes.getTitle());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}	

	protected void onProgressUpdate(Integer... progress){
		//setProgressPercent(progress[0]);
	}
	
	@Override
	protected void onPostExecute(ArrayList<Recipes> recipes) {

	}
	 
	 protected void onPreExecute(){
		 
	 }
	
}
