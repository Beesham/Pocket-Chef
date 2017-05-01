package com.beesham.pocketchef.ui;


import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.beesham.pocketchef.R;

public class CategoriesActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

	int catSelected; //category selected
	
	private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

	private ActionBar actionBar ;
	
	//private DrawerLayout mDrawerLayout;
	private String[] drawerOptions;
	private ListView drawerOptionsLV;
	private ActionBarDrawerToggle mActionBarToggle;
	  
	Bundle i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
				
		setupActionBar();
		setupViewPager();
		//setupDrawer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
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

		switch (item.getItemId()) {			
		case android.R.id.home:
			return true;
			case(R.id.action_search_new_recipes):
				Intent i = new Intent(this,BrowseNewRecipesActivity.class);
				startActivity(i);
				return true;
			default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //mActionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mActionBarToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
    
	@SuppressWarnings("deprecation")
	private void setupViewPager(){
		// Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);     
        mPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding tab.
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
		
		// Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
	    
	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	    	
	    	// Create a tab listener that is called when the user changes tabs.
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	        	// When the tab is selected, switch to the
	            // corresponding page in the ViewPager.
	        	mPager.setCurrentItem(tab.getPosition());
	        	
	        }

			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
	    };

	    
	    // Add tabs, specifying the tab's text and TabListener    
	    actionBar.addTab(
	    		actionBar.newTab()
	    		.setText("Breakfast")
	    		.setTabListener((android.app.ActionBar.TabListener) tabListener));
	    
	    actionBar.addTab(
	    		actionBar.newTab()
	    		.setText("Lunch")
	    		.setTabListener((android.app.ActionBar.TabListener) tabListener));
	    
	    actionBar.addTab(
	    		actionBar.newTab()
	    		.setText("Dinner")
	    		.setTabListener((android.app.ActionBar.TabListener) tabListener));
	}
		
//	private void setupDrawer(){
//
//		drawerOptions = getResources().getStringArray(R.array.options);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.categories_layout);
//		drawerOptionsLV = (ListView) findViewById(R.id.left_drawer);
//
//		// ActionBarDrawerToggle ties together the proper interactions
//        // between the sliding drawer and the action bar app icon
//		mActionBarToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//                ) {
//            public void onDrawerClosed(View view) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.setDrawerListener(mActionBarToggle);
//        drawerOptionsLV.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,drawerOptions));
//		drawerOptionsLV.setOnItemClickListener(new DrawerItemClickListener());
//	}
	
	private void setupActionBar(){
		actionBar = getActionBar();
		actionBar.show();
		actionBar.setDisplayShowTitleEnabled(true);
	}
	    

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return new BreakfastRecipesFragment();
				
			case 1:
				return new LunchRecipesFragment();
			
			case 2:
				return new DinnerRecipesFragment();
			}
			
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
	
//	/* The click listener for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    private void selectItem(int position) {
//    	Intent i;
//    	switch(position){
//    	case 0:
//    			i = new Intent(this,BrowseNewRecipesActivity.class);
//    			startActivity(i);
//    		break;
//    	case 1:
//    		break;
//    	}
//
//        // update selected item and title, then close the drawer
//        drawerOptionsLV.setItemChecked(position, true);
//        setTitle(drawerOptions[position]);
//        mDrawerLayout.closeDrawer(drawerOptionsLV);
//    }

}//end of class
