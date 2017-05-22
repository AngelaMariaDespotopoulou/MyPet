//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.angie.mypet.R;

//import android.util.Log;


public class MainActivity extends com.angie.mypet.ui.Menu implements PetSpeciesListFragment.OnFragmentInteractionListener {

    static public String mergeMemory = "";

    //*****************************************************************************************************************************
    // onCreate method.
    // When a list item (species) is clicked, an intent is launched towards another activity.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Standard procedure.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //*****************************************************************************************************************************
    // Handles menu creation.
    //*****************************************************************************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    // This function is needed the Back Button is pressed between activities.
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
        return R.string.main_activity_title;
    }


    @Override
    public void onPetSpeciesSelected(String selectedSpecies) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        boolean isDualPane = fragmentContainer != null &&
                fragmentContainer.getVisibility() == View.VISIBLE;

        if (isDualPane) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, PetPreviewsListFragment.newInstance(selectedSpecies));
            fragmentTransaction.commit();
        } else {
            startActivity(PetPreviewActivity.getStartIntent(this, selectedSpecies));
        }
    }

}



//Safekeeping the code below for historical reasons.

   /*
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent speciesIntent = new Intent(this, BrowseActivity.class);

         final CardView cardViewDogs = (CardView) findViewById(R.id.card_view1);
        cardViewDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Dog");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewCats = (CardView) findViewById(R.id.card_view2);
        cardViewCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Cat");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewUnicorns = (CardView) findViewById(R.id.card_view3);
        cardViewUnicorns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Unicorn");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewOthers = (CardView) findViewById(R.id.card_view4);
        cardViewOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Other");
                startActivity(browseIntent);
            }
        });
        */



