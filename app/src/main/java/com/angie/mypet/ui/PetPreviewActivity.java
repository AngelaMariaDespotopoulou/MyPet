//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 29th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.angie.mypet.R;

public class PetPreviewActivity extends com.angie.mypet.ui.Menu {

    private static final String EXTRA_PET_SPECIES_SELECTED = "selected_pet_species";
    String speciesFromIntent;                                                 // The same practically.
    Intent receivedIntent;                                                    // A message received from MainActivity.


    //*****************************************************************************************************************************
    // A method handling intents. As we use fragments, the activity handles its own intents as opposed to the
    // standard procedure.
    //*****************************************************************************************************************************

    public static Intent getStartIntent(Context context, String speciesFromIntent) {
        Intent intent = new Intent(context, PetPreviewActivity.class);
        intent.putExtra(EXTRA_PET_SPECIES_SELECTED, speciesFromIntent);

        return intent;
    }

    //*****************************************************************************************************************************
    // onCreate method.
    // When a list item (species) is clicked, an intent is launched towards another activity.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Standard procedure.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_preview);

        // Reading the selected pet species from an Intent.
        receivedIntent = getIntent();
        speciesFromIntent = receivedIntent.getStringExtra(EXTRA_PET_SPECIES_SELECTED);

        // If the user stands at the PetPreviewActivity while on portrait and then turns to landscape,
        // the activity must finish and assign control to dual-pan-main-activity.
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            MainActivity.mergeMemory = speciesFromIntent;
            finish();
        }

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            PetPreviewsListFragment firstFragment = PetPreviewsListFragment.newInstance(speciesFromIntent);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    //*****************************************************************************************************************************
    // Handles menu creation.
    //*****************************************************************************************************************************

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        boolean result = super.InflateMenu(menu, this);
        return result;
    }


    //*****************************************************************************************************************************
    // Handles selection of menu items.
    //*****************************************************************************************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = super.ItemSelected(item);
        return result;
    }


    //*****************************************************************************************************************************
    // Refreshes menu every time it re-appears.
    // This function is needed when the user logs-out while on the BrowseActivity (Pet Details Screen). The Activity
    // dies and returns to PetPreviewActivity. However, the PetPreviewActivity menu must be refreshed to "Log In".
    // Also, when the Back Button is pressed between activities.
    //*****************************************************************************************************************************

    public boolean onPrepareOptionsMenu(android.view.Menu menu)
    {
        super.RefreshMenu(menu);
        return true;
    }


    //*****************************************************************************************************************************
    // Defines the label on top of every visible activity.
    //*****************************************************************************************************************************

    @Override
    protected int getTitleResource() {
        return R.string.pet_preview_activity_title;
    }


    //*****************************************************************************************************************************
    // Deletes from the database the Pet displayed. Not actually needed here.
    //*****************************************************************************************************************************

    protected void deleteCurrentPet()
    {
        return;
    }


    //*****************************************************************************************************************************
    // Edits on the database the Pet displayed. Not actually needed here.
    //*****************************************************************************************************************************

    protected void editCurrentPet()
    {
        return;
    }


    //*****************************************************************************************************************************
    // onResume is used to refresh the screen after e.g. pet deletions.
    //*****************************************************************************************************************************

    @Override
    public void onRestart(){
        super.onRestart();
        this.recreate();
    }



    //*****************************************************************************************************************************
    // onSaveInstanceState method.
    // Committing elements to memory for safe recovery.
    //*****************************************************************************************************************************

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString("species", speciesFromIntent);       // Committing current species to memory.
    }*/

}
