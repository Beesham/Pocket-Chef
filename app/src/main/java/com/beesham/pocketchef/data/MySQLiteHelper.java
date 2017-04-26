package com.beesham.pocketchef.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;



public class MySQLiteHelper extends SQLiteOpenHelper {
	
	//Database version
	private static final int dbVer = 1;
	private static final String dbName = "pocketchef.db";
	private static final String dbPath = "/data/data/com.beesham.pocketchef/databases/";
	private SQLiteDatabase mydb;
    GetContext getContext;
    Context myContext;

    
	public MySQLiteHelper(Context context) {
		super(context, dbName, null, dbVer);
		myContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		getContext = new GetContext();
		//myContext = getContext.getmyContext();
	}
	
	public void createdb(){
		if(databaseExist()){ 
			//try {
				//CopyDB();
				Log.d("Copied Database","Copied Database");
			//} catch (IOException e) {e.printStackTrace();}
		}
		else this.getReadableDatabase();	
	}
	
	public boolean databaseExist()
	{
	    SQLiteDatabase checkDB = null;
	    String dbFullPath = dbPath+dbName;
	    try{
	    	checkDB = SQLiteDatabase.openDatabase(dbFullPath,null,SQLiteDatabase.OPEN_READONLY);
	    }catch(SQLiteException e){}
	    return checkDB != null ? true : false;
	}
	
	@Override 
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		db.execSQL("drop table if exists",null);
		onCreate(db);
	}
	
	public void openDataBaseRead() throws SQLException{
		 
    	//Open the database
        String myPath = dbPath + dbName;
    	mydb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
	
	public void openDataBaseWrite() throws SQLException{
		 
    	//Open the database
        String myPath = dbPath + dbName;
    	mydb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
	
	 public void CopyDB() throws IOException {
		 OutputStream outputStream = new FileOutputStream(dbPath);
		 
		// if(myContext.getAssets() != null) Log.d("no file","no file");		 
		 FileInputStream inputStream =  (FileInputStream) (myContext.getAssets()).open(dbName);
		 			byte[] buffer = new byte[1024];
			        int length;
			        while ((length = inputStream.read(buffer)) > 0) {
			        	outputStream.write(buffer, 0, length);
			        }
			        inputStream.close();
			        outputStream.close();
		}
 	
	public void closedb(){
		mydb.close();
	}
	
	
	//Database manipulations
	
 	public void insertInitialTables(){
 		try{mydb.execSQL("CREATE TABLE Recipe_Tbl(Recipe_ID integer primary key autoincrement,Recipe_Name varchar(30),Recipe_Content varchar(50),Recipe_Content_Path varchar(50), Recipe_Cat int,Favourite int);");}catch(SQLException e){}	
	}
	
 	//Inserts
 	
 	public void insertNewRecipe(String recipeName,String recipeContent,int recipe_Cat){
		try{
			
			ContentValues values = new ContentValues();
			
			values.put("recipe_content", recipeContent);
			values.put("recipe_name", recipeName);
			values.put("Recipe_Cat", recipe_Cat);
			mydb.insert("Recipe_Tbl", null, values);

		}catch(SQLException e){}
	}
 	

 	 public void insertNewRecipePath(String recipeName,String recipeContentPath){
 			try{
 				
 				ContentValues values = new ContentValues();
 				
 				//values.put("recipe_content", recipeContent);
 				values.put("recipe_content_path", recipeContentPath);
 				values.put("recipe_name", recipeName);
 				//values.put("Recipe_Cat", recipe_Cat);
 				mydb.update("Recipe_Tbl",values,"recipe_name like'"+recipeName+"'",null);

 			}catch(SQLException e){}
 		}
 	
 	public void setAsFavourite(String recipeName){
		try{
 		ContentValues values = new ContentValues();
		
		values.put("favourite", 1);
		mydb.update("Recipe_Tbl",values,"recipe_name like '"+recipeName+"'",null);
 		Log.d("inserting fav","setted "+recipeName+" as fav in dataB");
		}catch(SQLException e){e.printStackTrace();}
 	}
 	

 	//Gets
 	public ArrayList<String> getRecipeName(String categoryNum){
		ArrayList<String> recipeNamesAL= new ArrayList<String>();
		Cursor c=null;
		String temp;
		
		try{
			c = mydb.rawQuery("select recipe_name from recipe_tbl where Recipe_Cat like '"+categoryNum+"'",null);

			while (c.moveToNext()){	
					temp = c.getString(c.getColumnIndex("Recipe_Name"));
					if(!recipeNamesAL.contains(temp)){recipeNamesAL.add(c.getString(c.getColumnIndex("Recipe_Name")));}
					}	
			}catch(SQLException e){} 
			c.close();

		return recipeNamesAL;
	}
 	
 	public boolean checkIfExists(String recipeName){
 		boolean check=false;
		Cursor c=null;

 		try{
			c = mydb.rawQuery("select recipe_name from recipe_tbl where recipe_name like '"+recipeName+"'",null);

			while (c.moveToNext()){	
					if(c.getString(c.getColumnIndex("Recipe_Name")).equals(recipeName)) check=true;
					//if(!recipeNamesAL.contains(temp)){recipeNamesAL.add(c.getString(c.getColumnIndex("Recipe_Name")));}
					}	
			
			c.close();
			}catch(SQLException e){} 
 		
 		return check;
 	}
 	
 	public String getRecipeContent(String recipeName){
		String recipeContent = null;
		Cursor c=null;
		
		try{
			
			c = mydb.rawQuery("select recipe_content from recipe_tbl where recipe_name like '"+recipeName+"'",null);

			while (c.moveToNext()){	
				    recipeContent = c.getString(c.getColumnIndex("Recipe_Content"));
					}	
			}catch(SQLException e){} 
			c.close();

		return recipeContent;
	}
 	
 	public String getRecipeContentPath(String recipeName){
		String recipeContent = null;
		Cursor c=null;
		
		try{
			
			c = mydb.rawQuery("select recipe_content_path from recipe_tbl where recipe_name like '"+recipeName+"'",null);

			while (c.moveToNext()){	
				    recipeContent = c.getString(c.getColumnIndex("Recipe_Content_Path"));
					}	
			}catch(SQLException e){} 
			c.close();

		return recipeContent;
	}
 	
 	public ArrayList<String> getFavourites(String categoryNum){
 		ArrayList<String> recipeNamesAL= new ArrayList<String>();
		Cursor c=null;
		String temp;
		
		try{
			c = mydb.rawQuery("select recipe_name from recipe_tbl where Recipe_Cat like '"+categoryNum+"' and favourite is 1",null);

			while (c.moveToNext()){	
					temp = c.getString(c.getColumnIndex("Recipe_Name"));
					if(!recipeNamesAL.contains(temp)){recipeNamesAL.add(c.getString(c.getColumnIndex("Recipe_Name")));}
					}	
			
			c.close();
			}catch(SQLException e){} 

		return recipeNamesAL;
 	}
 	
 	public boolean checkFavourite(String recipeName){
 		boolean check=false;
		Cursor c=null;

 		try{
			c = mydb.rawQuery("select favourite from recipe_tbl where recipe_name like '"+recipeName+"'",null);

			while (c.moveToNext()){	
					if(c.getInt(c.getColumnIndex("Favourite"))==1) check=true;
					//if(!recipeNamesAL.contains(temp)){recipeNamesAL.add(c.getString(c.getColumnIndex("Recipe_Name")));}
					}	
			
			c.close();
			}catch(SQLException e){} 
 		
 		return check;
 	}
	
 	//Removes
 	
 	public void removeRecipe(String recipeName){

		try{		
			//mydb.execSQL("delete from recipe_tbl where recipe_name like '"+recipeName+"'",null);
			mydb.delete("recipe_tbl",  "recipe_name like '"+recipeName+"'", null);
		}catch(SQLException e){} 
			//c.close();

		//return recipeContent;
	}
 	
 	public void removeAsFavourite(String recipeName){
 		try{
 	 		ContentValues values = new ContentValues();
 			
 			values.put("favourite", 0);
 			mydb.update("Recipe_Tbl",values,"recipe_name like '"+recipeName+"'",null);
 	 		Log.d("inserting fav","removed "+recipeName+" as fav in dataB");
 			}catch(SQLException e){e.printStackTrace();}

 	}
 		
}//end of MySQLiteHelper class

class GetContext extends Activity{
	Context myContext;
	
	public Context getmyContext(){
		myContext = (Context) getApplicationContext();
		return myContext;
	}
	
}
