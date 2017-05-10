//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 10th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PetPreviewActivity extends com.angie.mypet.Menu {

    public static final String EXTRA_SPECIES = "species_selected";            // Used for receiving messages from MainActivity.
    String speciesFromIntent;                                                 // The same practically.
    private ListView listView;                                                // A list view widget.
    Intent receivedIntent;                                                    // A message received from MainActivity.
    Intent browseIntent;                                                      // A new message directed to BrowseActivity.
    public static Cursor petsOfSpecies ;                                      // A set of pets belonging to a particular species.
    TextView hiddenText;                                                      // A hidden message for empty categories.
    final LoginController localLoginController = new LoginController(this);   // A handler responsible only to check if user has already taken care of login. (Another is instantiated by the LoginActivity class.)

    //*****************************************************************************************************************************
    // onCreate method.
    // When a list item (species) is clicked, an intent is launched towards another activity.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Standard procedure.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_preview);

        if (savedInstanceState != null) {
            speciesFromIntent = savedInstanceState.getString("species");               // Remembering the species on screen if necessary.
        }
        else
        {
            receivedIntent = getIntent();
            speciesFromIntent = receivedIntent.getStringExtra(EXTRA_SPECIES);
        }

        // Hide the hidden message for non-existent animals.
        hiddenText = (TextView)findViewById(R.id.hidden_message);
        hiddenText.setVisibility(View.INVISIBLE);

        // Reading a message from Main Activity and do some housekeeping.
        petsOfSpecies = MainActivity.petsDatabase.generateListOfPetsBelongingToASpecies(speciesFromIntent);

        // Specifying the species view.
        listView = (ListView) findViewById(R.id.preview_pets_list);

        // Assigning a custom adapter to the list view.

        CursorAdapter listAdapter = new PetPreviewAdapter(this, petsOfSpecies, 0);
        listView.setAdapter(listAdapter);

        // Assigning a Listener to the custom list view.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            public void onItemClick(AdapterView<?> list, View row, int index, long rowId) {

                                                // Let's animate things a bit...
                                                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
                                                row.startAnimation(animation1);

                                                // Ensure that only logged in users have access to the pets' details.
                                                if(localLoginController.isUserAlreadyLoggedIn() != "" && localLoginController.isUserAlreadyLoggedIn() != null) {
                                                    // Create and handle intent. The cursor is waiting. Passing the position (chosen pet) to the BrowseActivity.
                                                    browseIntent = new Intent(PetPreviewActivity.this, BrowseActivity.class);
                                                    Cursor cursor = (Cursor) list.getAdapter().getItem(index);
                                                    browseIntent.putExtra(BrowseActivity.EXTRA_PET_CURSOR_POSITION, cursor.getPosition());
                                                    startActivity(browseIntent);
                                                }
                                                else
                                                {
                                                    // Anonymous users receive a message.
                                                    String toastMessage = getResources().getString(R.string.please_login);
                                                    Toast.makeText(PetPreviewActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
        );

        // Handling the empty category. Control is transferred to BrowseActivity without making the listView visible.
        if(petsOfSpecies.getCount() < 1)
        {
            listView.setVisibility(View.INVISIBLE);

            String message = getString(R.string.fairy_tale_message);
            message = message.replace("@Species", speciesFromIntent);
            hiddenText.setText(message);
            hiddenText.setVisibility(View.VISIBLE);
            return;
        }
        else
            listView.setVisibility(View.VISIBLE);
    }


    //*****************************************************************************************************************************
    // onSaveInstanceState method.
    // Committing elements to memory for safe recovery.
    //*****************************************************************************************************************************

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString("species", speciesFromIntent);       // Committing current species to memory.
    }


    //*****************************************************************************************************************************
    // onDestroy method.
    // Closing the active cursor.
    //*****************************************************************************************************************************
    public void onDestroy(){
        super.onDestroy();
        petsOfSpecies.close();
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

}
