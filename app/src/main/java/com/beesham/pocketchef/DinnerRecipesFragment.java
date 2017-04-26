package com.beesham.pocketchef;
import java.util.ArrayList;

import com.beesham.pocketchef.data.MySQLiteHelper;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class DinnerRecipesFragment extends Fragment {

	private MySQLiteHelper db;
	ArrayAdapter<String> adapter=null;
	ArrayList<String> recipesAL;
	int catSelected;
	ListView recipeList;
	Context context;
	Bundle bundle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
		
		bundle = new Bundle();		

		db = new MySQLiteHelper(context);
		recipeList = (ListView) rootView.findViewById(R.id.mylist);
		
		try {
			db.openDataBaseRead();
			recipesAL = db.getRecipeName("3");
		}catch(SQLException sqle){
			sqle.printStackTrace();}
		finally{db.closedb();}
		
		final myArrayAdapterSwipeToDismiss adapter = new myArrayAdapterSwipeToDismiss(getActivity(), recipesAL);
		
		if(recipesAL.isEmpty()){
			rootView = inflater.inflate(R.layout.empty_list_item, container, false);
			recipeList.setEmptyView(rootView);
		}
		else{
			recipeList.setAdapter(adapter);
		    adapter.notifyDataSetChanged();
		}
		
		 final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
	                new SwipeToDismissTouchListener<ListViewAdapter>(
	                        new ListViewAdapter(recipeList),
	                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
	                            @Override
	                            public boolean canDismiss(int position) {
	                                return true;
	                            }

	                            @Override
	                            public void onDismiss(ListViewAdapter view, int position) {
	                            	removeRecipes(adapter.getItem(position));
	                                adapter.remove(position);                                
	                            }
	                        });
						 recipeList.setOnTouchListener(touchListener);
						 recipeList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
						 recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					    @Override
					    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					        if (touchListener.existPendingDismisses()) {
					            touchListener.undoPendingDismiss();
					        } else {
					           // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
					        	String selectedFromList =(String) (recipeList.getItemAtPosition(position));
					        	bundle.putString("selectedFromList", selectedFromList);
					        	
					        	Intent i = new Intent(getActivity(),ViewRecipeActivity.class);
					        	i.putExtras(bundle);
					    		startActivity(i);
					        }
					    }
	                });
		
		return rootView;
	}
	
	//removes recipe from database
	public void removeRecipes(String recipe){
		try{
			//Log.d("removing from db: ",(((EditActArrayAdapter) adapter).getSelectedChkBx()).get(i));
			db.openDataBaseWrite();
			db.removeRecipe(recipe);//recipesAL.get(i));
			db.closedb();
		}catch(SQLException e){}
	}
	


}

