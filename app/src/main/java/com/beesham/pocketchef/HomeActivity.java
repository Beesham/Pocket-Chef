package com.beesham.pocketchef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beesham.pocketchef.data.MySQLiteHelper;


public class HomeActivity extends Activity {

	private MySQLiteHelper mydb;
	private int SPLASH_TIME_OUT = 3000;
	SharedPreferences prefs = null;
	
	
	Bundle bundle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home);
        
        prefs = getSharedPreferences(getString(R.string.package_name), MODE_PRIVATE);
        mydb = new MySQLiteHelper(this);
               
        //create and inserts initial tables with values on first run
        if (prefs.getBoolean("firstrun", true)) {
        	Log.d("running first time","running first time");
        	
	    	//CopyFiles cf = new CopyFiles();
	    	String[] initRecipes = {getResources().getString(R.string.old_fashioned_pancakes),getResources().getString(R.string.salisbury_steak_with_mushroom_gravy),getResources().getString(R.string.caramelized_chicken_wings)};
	    	Log.d("in string[]",initRecipes[0]);

        	File file_system = Environment.getExternalStorageDirectory();
	    	File dir = new File (file_system.getAbsolutePath() + "/Recipes");
	    	Log.d("dir",file_system.getAbsolutePath()+ "/Recipes");
	    	dir.mkdirs();
	    	
        	copyAssets(initRecipes);
        	
	        try {
	        	mydb.createdb();
	        	mydb.openDataBaseWrite();
	        	mydb.insertInitialTables();
	            mydb.insertNewRecipe(this.getString(R.string.old_fashioned_pancakes),this.getString(R.string.old_fashioned_pancakes_c),1);
	            mydb.insertNewRecipe(this.getString(R.string.caramelized_chicken_wings),this.getString(R.string.caramelized_chicken_wings_c),2);
	            mydb.insertNewRecipe(this.getString(R.string.salisbury_steak_with_mushroom_gravy),this.getString(R.string.salisbury_steak_with_mushroom_gravy_c),3);
	            mydb.closedb();
	    	}catch(SQLException sqle){throw sqle;}   
	        prefs.edit().putBoolean("firstrun", false).commit();
        }
        
        //Splash Screen Timer
        new Handler().postDelayed(new Runnable() {
        	 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                 Intent i = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(i);
 
                finish();
            }
        }, SPLASH_TIME_OUT);
        
        
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
   
    public void copyAssets(String[] fileList) {
	    AssetManager assetManager = getAssets();
	    String[] files = fileList;
	    try {
	        files = assetManager.list("Recipes");
	    } catch (IOException e) {
	        Log.e("tag", "Failed to get asset file list.", e);
	    }
	    
	    for(String filename : files) {
	    	Log.d("file in assets",filename);
	    }
	    
	    for(String filename : files) {
	    	Log.d("file in assets",filename);

	        InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = assetManager.open("Recipes/"+filename);
	          File outFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recipes/" + filename);
	          out = new FileOutputStream(outFile);
	          copyFile(in, out);
	        } catch(IOException e) {
	            Log.e("tag", "Failed to copy asset file: " + filename, e);
	        }     
	        finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    // NOOP
	                }
	            }
	            if (out != null) {
	                try {
	                    out.close();
	                } catch (IOException e) {
	                    // NOOP
	                }
	            }
	        }  
	    }
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
    
	
}
