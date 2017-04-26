package com.beesham.pocketchef.util;
import android.content.Context;
import android.content.res.Configuration;

public class IsTablet {
	public static boolean getSize(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
}
