package com.beesham.pocketchef.util;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork extends Activity {
	public boolean isNetworkAvailable()  // determines if the network is available or not
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	    		getSystemService(Context.CONNECTIVITY_SERVICE);
	    
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();  //return true if network is available| false if not available
	}
}
