//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 23rd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class BrowseActivity extends Activity
{
	Controller c;                                                           // A helping class in order to avoid having too much code here.
	public static final String EXTRA_PET_CURSOR_POSITION = "pet.to.show.on.screen";
	int petCursorPositionFromIntent = 0;

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
		petCursorPositionFromIntent = intent.getIntExtra(EXTRA_PET_CURSOR_POSITION, 0);

		c.InitializeBrowsing(petCursorPositionFromIntent);

		if (savedInstanceState != null) {
			c.restorePreviousState(savedInstanceState);
		}
	}


	//*****************************************************************************************************************************
	// onSaveInstanceState method.
	// Committing elements to memory for safe recovery.
	//*****************************************************************************************************************************

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		c.saveState(savedInstanceState);
	}


	//*****************************************************************************************************************************
	// Take action when one of the buttons is clicked. Make the next or previous pet appear on screen respectively.
	//*****************************************************************************************************************************

	// "Next"  Button Clicked.
	public void onClickFindNextPet(View view) {
		if(c != null)
		{
			c.fetchNextPet();
		}
	}

	// "Previous" Button Clicked.
	public void onClickFindPreviousPet(View view) {
		if(c != null)
		{
			c.fetchPreviousPet();
		}
	}
}