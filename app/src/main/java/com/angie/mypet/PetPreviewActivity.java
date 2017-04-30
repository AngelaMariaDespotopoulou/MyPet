//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 23rd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PetPreviewActivity extends Activity {

    public static final String EXTRA_SPECIES = "species_selected";        // Used for receiving messages from MainActivity.
    String speciesFromIntent;                                             // The same practically.
    private ListView listView;                                            // A list view widget.
    Intent receivedIntent;                                                // A message received from MainActivity.
    Intent browseIntent;                                                  // A new message directed to BrowseActivity.
    public static Cursor petsOfSpecies ;                                  // A set of pets belonging to a particular species.
    TextView hiddenText;                                                  // A hidden message for empty categories.


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

                                                // Create and handle intent. The cursor is waiting. Passing the position (chosen pet) to the BrowseActivity.
                                                browseIntent = new Intent(PetPreviewActivity.this, BrowseActivity.class);
                                                Cursor cursor = (Cursor) list.getAdapter().getItem(index);
                                                browseIntent.putExtra(BrowseActivity.EXTRA_PET_CURSOR_POSITION, cursor.getPosition());
                                                startActivity(browseIntent);
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

}
