package com.beesham.pocketchef;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;


public class HTMLextractor extends Activity {
/*********************************************************
 * This class extract the source code of a given html site
 * and saves it to the file system on the android device
 ***************************************************************/
	
	String fileContents = "Recipes";


	public void extract_save_html(final String url,final String recipe_name)  // extracts the html source code of a given webpage and saves it in a named file
	{
    	new Thread(new Runnable() {
    		  String recipe_html = "";
    		  URL url = null;
			public void run() {
				
				try {
					url = new URL(url.toString());			
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
					
					//HttpClient client = new DefaultHttpClient();  //creates an HTTP client
					//HttpGet request = new HttpGet(url); // random website
					//HttpResponse response = client.execute(request);
              
					
					//InputStream in = response.getEntity().getContent();
				    InputStream in = new BufferedInputStream(con.getInputStream());

					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder str = new StringBuilder();
					String line = null;
					while((line = reader.readLine()) != null)
					{
					    str.append(line);
					}
					in.close();
					recipe_html = str.toString();
			
					runOnUiThread(new Runnable() // executes an action on the UI thread
				    {
				          public void run()
				          {
				        	  mk_file(recipe_name,recipe_html);
				          }
				     });
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
    	}).start();
    	
	}
	
	 public void mk_file(String file_name, String file_content)  // creates a html file and write the source code of a given file
	 {
		    
	    	File file_system = Environment.getExternalStorageDirectory();
	    	File dir = new File (file_system.getAbsolutePath() + "/Recipes");
	    	Log.d("dir",file_system.getAbsolutePath()+ "/Recipes");
	    	dir.mkdirs();
	    	File file = new File(dir, file_name+".html");
	    	FileWriter fw;
			try {
					fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(file_content);
					bw.close();
			    }
			    catch (IOException e)
			    {
				  e.printStackTrace();
			    }
	 }	
	
}
