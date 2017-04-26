package com.beesham.pocketchef;

import java.util.ArrayList;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.beesham.pocketchef.model.Recipes;

public class BrowseNewRecipesActivity extends FragmentActivity {
	Bundle bundle;	
	ArrayList<Recipes> recipesL = null;
	String[] recipesSources = null;
	ListView list;
	Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_new_recipes);

		if(!isNetworkAvailable()){
			setContentView(R.layout.empty_list_item);
		}
		else{
			addBrowseNewRecipeFragment();
		}
		
	}

	public void addBrowseNewRecipeFragment(){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		BrowseNewRecipesFragment mBrowseNewRecipesFragment = new BrowseNewRecipesFragment();
		fragmentTransaction.replace(R.id.fragment_content, mBrowseNewRecipesFragment, "browse_recipe");
		fragmentTransaction.commit();
	}
	
	public boolean isNetworkAvailable()  // determines if the network is available or not
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	    		this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();  //return true if network is available| false if not available
	}
	
}
