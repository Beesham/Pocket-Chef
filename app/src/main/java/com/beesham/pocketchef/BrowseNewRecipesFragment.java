package com.beesham.pocketchef;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.beesham.pocketchef.model.Recipes;

public class BrowseNewRecipesFragment extends Fragment {

	Bundle bundle;	
	ArrayList<Recipes> recipesL = null;
	String[] recipesSources = null;
	ListView list;
	ProgressBar mProgressBar;
	Context context;
	private ArrayAdapt adapt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_browse_new_recipes,container,false);
		
		list = (ListView) rootView.findViewById(R.id.searchlist);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		
		recipesL = new ArrayList<Recipes>();

		final EditText eText = (EditText) rootView.findViewById(R.id.editText1);

		list = (ListView) rootView.findViewById(R.id.searchlist);

		try {

			getRecipes();

			adapt = new ArrayAdapt(getActivity(),R.layout.customlist, recipesL);

			list.setAdapter(adapt);
			adapt.notifyDataSetChanged();

			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					bundle = new Bundle();
					Recipes tempR = (Recipes) list.getItemAtPosition(position);

					String selectedFromList =(String) (tempR.getLink());
					bundle.putString("selectedContentFromSearch", selectedFromList);
					bundle.putString("selectedFromList", tempR.getTitle());

					showSelectedRecipe();			        		
				}
			});

			eText.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					ArrayList<Recipes> temp = new ArrayList<Recipes>();
					ArrayAdapter adapter = new ArrayAdapt(getActivity(),R.layout.customlist, temp);
					int textlength = eText.getText().length();
					temp.clear();
					for(int i = 0; i < recipesL.size(); i++){
						if(textlength <= recipesL.get(i).title.length())
						{
							if(eText.getText().toString().equalsIgnoreCase((String)recipesL.get(i).title.subSequence(0, textlength))){
								temp.add(recipesL.get(i));
							}								
						}
					}
					list.setAdapter(adapter);
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}

			});

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootView;		
	}

	public void showSelectedRecipe(){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		ViewRecipeFragment mViewRecipeFragment = new ViewRecipeFragment();
		mViewRecipeFragment.setArguments(bundle);
		fragmentTransaction.replace(R.id.fragment_content, mViewRecipeFragment, "selected_recipe");
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	public void getRecipes() throws InterruptedException, ExecutionException{
		recipesSources = getResources().getStringArray(R.array.breakfastsources);
		for(int i=0;i<(recipesSources.length);i++){
			new RssFeed().execute(recipesSources[i]);
		}

		recipesSources = getResources().getStringArray(R.array.lunchsources);
		for(int i=0;i<(recipesSources.length);i++){
			new RssFeed().execute(recipesSources[i]);
		}
		

		recipesSources = getResources().getStringArray(R.array.dinnersources);
		for(int i=0;i<(recipesSources.length);i++){
			new RssFeed().execute(recipesSources[i]);
		}
	}

	private class RssFeed extends AsyncTask<String, Integer,ArrayList<Recipes>> {
		// Declaring a Recipe object
		ArrayList<Recipes> list = new ArrayList<Recipes>();
		Recipes recipes;
		String description,title,link,img;
		Context context;
		
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
			mProgressBar.setVisibility(View.GONE);
			recipesL.addAll(recipes);
			adapt.notifyDataSetChanged();
		}
		 
		protected void onPreExecute(){
			mProgressBar.setVisibility(View.VISIBLE);
		}
		
	}
	
}
