package com.beesham.pocketchef;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class EditActivity extends Activity implements ActionBar.OnNavigationListener {

	ListView recipeList;
	EditActArrayAdapter adapter=null;
	ArrayList<String> recipesAL;
	
	private MySQLiteHelper db;
	private ActionBar actionBar;
	
	Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		//gettheme();
		
		db = new MySQLiteHelper(this);
		b=getIntent().getExtras();

		getRecipes();//gets recipes from the database based on category selected
		
		recipeList = (ListView)findViewById(R.id.editlist);//getListView();		
		adapter = new EditActArrayAdapter (this,recipesAL);
		recipeList.setAdapter(adapter);
	    adapter.notifyDataSetChanged();
	    
	    actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_act_actions, menu);
		return true;
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
		
		//actionbar actions
		switch (item.getItemId()) {
		/*case R.id.action_back:
			back();
			return true;*/
		case R.id.action_remove:
			removeRecipes();
			return true;
		case android.R.id.home:
			back();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	//removes recipe from database
	public void removeRecipes(){
		for(int i=0;i<(((EditActArrayAdapter) adapter).getSelectedChkBx()).size();i++){//for(int i=0;i<recipesAL.size();i++){
			try{
				Log.d("removing from db: ",(((EditActArrayAdapter) adapter).getSelectedChkBx()).get(i));
				db.openDataBaseWrite();
				db.removeRecipe((((EditActArrayAdapter) adapter).getSelectedChkBx()).get(i));//recipesAL.get(i));
				db.closedb();
			}catch(SQLException e){}
		}
		
		recreate();	//refreshes the screen to display updated data
	}
	
	//gets recipe from database 
	public void getRecipes(){
		try {
			db.openDataBaseRead();
			recipesAL = db.getRecipeName(b.getString("catSelected"));
		    db.closedb();
		}catch(SQLException sqle){throw sqle;}
	}
	
	//returns to previous activity
	public void back(){
		Intent i = new Intent(this,TabsActivity.class);
		i.putExtras(b);
		System.exit(RESULT_OK);
		startActivity(i);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        back();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
