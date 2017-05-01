package com.beesham.pocketchef.ui;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beesham.pocketchef.R;
import com.beesham.pocketchef.data.MySQLiteHelper;

public class ViewRecipeFragment extends Fragment implements ActionBar.OnNavigationListener  {

	Bundle bundle;
	String recipeContent,selectedContentFromSearch,selectedNameFromSearch;
	String recipeName;
	private ActionBar actionBar;
	private MySQLiteHelper db;
	private WebView recipeWV;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_view_recipe,container,false);
		
		db = new MySQLiteHelper(getActivity());
		bundle = getArguments();
		
		recipeWV = (WebView)rootView.findViewById(R.id.recipeWebView);
		
		if(bundle.containsKey("selectedContentFromSearch")){
			selectedContentFromSearch=bundle.getString("selectedContentFromSearch");
		}
		
		if(bundle.containsKey("selectedFromList")){
			selectedNameFromSearch=bundle.getString("selectedFromList");
		}
		
		if(bundle.containsKey("selectedFromList")){
			recipeName=bundle.getString("selectedFromList");
		}
		
		try {
			db.openDataBaseRead();
			recipeContent = db.getRecipeContent(recipeName);
		}catch(SQLException sqle){throw sqle;}
		finally{db.closedb();}
		
		if(recipeContent == null){
			recipeContent = selectedContentFromSearch;
			recipeName = selectedNameFromSearch;
		}

		if(isNetworkAvailable()){		
			viewFromWeb();
		}
		else viewFromLocal();
		
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.view_act_tabs_action, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		//changes icon state
		switch (item.getItemId()) {
		//case R.id.action_favourite:
//			try{
//				db.openDataBaseRead();
//				if(!db.checkFavourite(recipeName)){
//					if(db.checkIfExists(recipeName)){
//						setAsFavourite();
//						item.setIcon(R.drawable.ic_menu_star_fav);
//					}
//					else{
//						new AlertDialog.Builder(getActivity())
//					    .setTitle(R.string.unable)
//					    .setMessage(R.string.please_save)
//					    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//					        public void onClick(DialogInterface dialog, int which) {
//
//					        }
//					     })
//
//					    .setIcon(android.R.drawable.ic_dialog_alert)
//					     .show();
//					}
//				}
//				else if (db.checkFavourite(recipeName)){
//					removeAsFavourite();
//					item.setIcon(R.drawable.ic_menu_star);
//				}
//			}catch(SQLException e){e.printStackTrace();}
//			finally{db.closedb();}

			//return true;
		case R.id.action_save:
//			try{
//				db.openDataBaseRead();
//				if(!db.checkIfExists(recipeName)){
//					db.closedb();
//					save();
//					item.setIcon(R.drawable.ic_menu_saved);
//				}
//			}catch(SQLException e){e.printStackTrace();}
//			finally{db.closedb();}

			return true;
		case android.R.id.home:
			//back();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void viewFromWeb(){
		WebSettings ws = recipeWV.getSettings();
		ws.setSupportZoom(true);
		ws.setBuiltInZoomControls(true);
			
		recipeWV.setWebViewClient(new WebViewClient());
		recipeWV.clearCache(true);
		recipeWV.loadUrl(recipeContent);
	}
	
	public void viewFromLocal(){
		WebSettings ws = recipeWV.getSettings();
		ws.setSupportZoom(true);
		ws.setBuiltInZoomControls(true);
				 
		recipeWV.setWebViewClient(new WebViewClient());
		recipeWV.clearCache(true);
		recipeWV.loadUrl("file:"+Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Recipes/"+recipeName+".html");
	}
	
//	public void setAsFavourite(){
//
//		try{
//			db.openDataBaseWrite();
//			db.setAsFavourite(recipeName);
//		}catch(SQLException e){e.printStackTrace();}
//		finally{db.closedb();}
//
//	}
//
//	public void removeAsFavourite(){
//		try{
//			db.openDataBaseWrite();
//			Log.d("removing favourite","setting "+recipeName+" as favourite");
//			db.removeAsFavourite(recipeName);
//		}catch(SQLException e){e.printStackTrace();}
//		finally{db.closedb();}
//
//	}
	
	public boolean isNetworkAvailable()  // determines if the network is available or not
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	    		getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();  //return true if network is available| false if not available
	}
	
	/*public void save(){
		
		HTMLextractor htmlExtractor = new HTMLextractor();
		htmlExtractor.extract_save_html(recipeContent,recipeName);
		
		try{
			db.openDataBaseWrite();
			db.insertNewRecipe(recipeName, recipeContent,Integer.parseInt(recipeCat));//removeAsFavourite(recipeName);
		}catch(SQLException e){e.printStackTrace();}
		finally{db.closedb();}

	}*/
	
//	public void unSave(){
//		try{
//			db.openDataBaseWrite();
//			db.removeRecipe(recipeName);
//		}catch(SQLException e){e.printStackTrace();}
//		finally{db.closedb();}
//	}
	
	/*public void back(){
		Intent i = new Intent(this,TabsActivity.class);
		i.putExtras(b);
		System.exit(RESULT_OK);
		startActivity(i);
	}*/

	
}
