//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 10th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

//import android.util.Log;


public class MainActivity extends com.angie.mypet.Menu {

    private List<String> species;                   // A list to store all available species.
    private ListView listView;                      // A list view widget.
    private BaseAdapter speciesListAdapter;         // An adapter for the listView widget.
    private Intent speciesIntent;                   // A message towards the PetPreviewActivity.
    static PetsDatabase petsDatabase;               // A database of pets.
    com.angie.mypet.Menu appMenu;                   // A handler for the menu actions.

    //*****************************************************************************************************************************
    // onCreate method.
    // When a list item (species) is clicked, an intent is launched towards another activity.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Standard procedure.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            //c.restorePreviousState(savedInstanceState);
        }

        // Creating the database containing all the Pets. (Hard-coded for the moment.)
        petsDatabase = new PetsDatabase(this.getBaseContext());
        species = petsDatabase.generateSpeciesList();

        // Assigning a listener to the species view.
        listView = (ListView) findViewById(R.id.species_list_view);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> list, View row, int index, long rowId) {

                        // Let's animate things a bit...
                        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
                        row.startAnimation(animation1);

                        // Create and handle intent. Pass the selected species to the PetPreviewActivity.
                        speciesIntent = new Intent(MainActivity.this, PetPreviewActivity.class);
                        String choice = list.getAdapter().getItem(index).toString();
                        speciesIntent.putExtra(PetPreviewActivity.EXTRA_SPECIES, choice);
                        startActivity(speciesIntent);
                    }
                }
        );

        // Aesthetic changes.
        listView.setCacheColorHint(0);
        listView.setBackgroundResource(R.drawable.vector);

        // Assigning a simple adapter (provided by Android) to the list view.
        speciesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, species) {
            @Override
            // Handling aesthetic aspects.
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.rgb(145, 42, 42));
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                textView.setShadowLayer(1.5f, -2, 2, Color.GRAY);
                textView.setTextSize(20);
                return textView;
            }
        };
        listView.setAdapter(this.speciesListAdapter);
        listView.setVisibility(View.VISIBLE);

        /*try {
            test();
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    //*****************************************************************************************************************************
    // onDestroy method.
    // Closing the active cursor.
    //*****************************************************************************************************************************
    public void onDestroy(){
        super.onDestroy();
        petsDatabase.closeConnectionToDatabase();
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



