package com.beesham.pocketchef;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class ViewRecipeActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_recipe);

		Bundle b = getIntent().getExtras();
		
		FragmentManager mFragmentManager = getFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		ViewRecipeFragment mViewRecipeFragment = new ViewRecipeFragment();
		mViewRecipeFragment.setArguments(b);
		mFragmentTransaction.add(R.id.fragment_content,mViewRecipeFragment);
		mFragmentTransaction.commit();
	}

	
	
}
