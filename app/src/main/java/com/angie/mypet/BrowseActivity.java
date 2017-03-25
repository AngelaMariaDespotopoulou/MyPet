package com.angie.mypet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
//import android.util.Log;
import android.view.View;


public class BrowseActivity extends Activity
{
	Controller c;
	public static final String EXTRA_SPECIES = "species.to.request";
	String speciesFromIntent = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_browse);

		// Determine Landscape or Portrait orientation and choose layout accordingly.
		/*if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			setContentView(R.layout.activity_browse_landscape);
		}
		else
		{
			setContentView(R.layout.activity_browse_portrait);
		}*/

		c = new Controller(this);

		Intent intent = getIntent();
		speciesFromIntent = intent.getStringExtra(EXTRA_SPECIES);
		
		if(speciesFromIntent != "" && speciesFromIntent != null)
		{
			c.InitializeBrowsing(speciesFromIntent);
		}
		
		if (savedInstanceState != null) {
			c.restorePreviousState(savedInstanceState);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		c.saveState(savedInstanceState);
	}

	// "Next"  Button Clicked.
	public void onClickFindNextPet(View view) {
		if(c != null)
		{
			c.fetchNextPet(speciesFromIntent);
		}
	}

	// "Previous" Button Clicked.
	public void onClickFindPreviousPet(View view) {
		if(c != null)
		{
			c.fetchPreviousPet(speciesFromIntent);
		}
	}
}