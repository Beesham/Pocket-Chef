package com.beesham.pocketchef.ui;
import java.util.ArrayList;

import com.beesham.pocketchef.R;
import com.beesham.pocketchef.data.MySQLiteHelper;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FavouritesFragment extends Fragment {
	
	private MySQLiteHelper db;
	ArrayAdapter<String> adapter=null;
	ArrayList<String> favRecipesAL;
	int catSelected;
	ListView favRecipeList;
	Context context;
	Bundle b;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        
		b = getActivity().getIntent().getExtras();
		
		Log.d("cat in frag", b.getString("catSelected"));
		
		View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
	
		db = new MySQLiteHelper(context);
		favRecipeList = (ListView) rootView.findViewById(R.id.favlist);//getListView();
		
		getFavourites();//gets the favourited recipes based on category from database
		
		for(int i=0;i<favRecipesAL.size();i++){
			Log.d("fav recipe: ",favRecipesAL.get(i));
		}
		
		//adapter = new ArrayAdapter<String> (this.getActivity(),android.R.layout.simple_list_item_1,favRecipesAL);
		final myArrayAdapterSwipeToDismiss adapter = new myArrayAdapterSwipeToDismiss(getActivity(), favRecipesAL);

		
		if(favRecipesAL.isEmpty()){
			rootView = inflater.inflate(R.layout.empty_list_item, container, false);
			//this.addContentView(empty, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			favRecipeList.setEmptyView(rootView);
		}
		else{
			//try if favourite list is null
			try{
				favRecipeList.setAdapter(adapter);
				adapter.notifyDataSetChanged(); 
				favRecipeList.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			           
			        	Bundle tempb = new Bundle();
			        	
			        	String selectedFromList =(String) (favRecipeList.getItemAtPosition(position));
			        	tempb.putString("selectedFromList", selectedFromList);
			        	
			        	Intent i = new Intent(getActivity(),ViewRecipeFragment.class);
			        	i.putExtras(tempb);
			        	i.putExtras(b);
			    		startActivity(i);
		        		
		        }
		    });
			}catch(Exception e){}
		}
		
		 final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
	                new SwipeToDismissTouchListener<ListViewAdapter>(
	                        new ListViewAdapter(favRecipeList),
	                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
	                            @Override
	                            public boolean canDismiss(int position) {
	                                return true;
	                            }

	                            @Override
	                            public void onDismiss(ListViewAdapter view, int position) {
	                                adapter.remove(position);
	                            }
	                        });
						 favRecipeList.setOnTouchListener(touchListener);
						 favRecipeList.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
						 favRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					    @Override
					    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					        if (touchListener.existPendingDismisses()) {
					            touchListener.undoPendingDismiss();
					        } else {
					           // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
					        }
					    }
	                });
		
		return rootView;
	}
	
	//gets favourited recipes from database
	public void getFavourites(){
		try {
			db.openDataBaseRead();
			favRecipesAL = db.getFavourites(b.getString("catSelected"));
		    db.closedb();
		}catch(SQLException sqle){throw sqle;}
	}
	
}//end of class
