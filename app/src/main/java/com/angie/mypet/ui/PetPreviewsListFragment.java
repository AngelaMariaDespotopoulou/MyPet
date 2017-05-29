//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.angie.mypet.R;
import com.angie.mypet.authentication.LoginController;


public class PetPreviewsListFragment extends Fragment {


    private static final String ARG_PET_SPECIES_SELECTED = "selected_pet_species";
    private String selectedPetSpecies;
    private ListView listView;                                                // A list view widget.
    Intent receivedIntent;                                                    // A message received from MainActivity.
    Intent browseIntent;                                                      // A new message directed to BrowseActivity.
    public static Cursor petsOfSpecies ;                                      // A set of pets belonging to a particular species.
    TextView hiddenText;                                                      // A hidden message for empty categories.
    com.angie.mypet.authentication.LoginController localLoginController = new com.angie.mypet.authentication.LoginController((AppCompatActivity) getActivity());


    //*****************************************************************************************************************************
    // Initialising the fragment.
    //*****************************************************************************************************************************

    public static PetPreviewsListFragment newInstance(String selectedPetSpecies) {
        PetPreviewsListFragment fragment = new PetPreviewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PET_SPECIES_SELECTED, selectedPetSpecies);
        fragment.setArguments(args);
        return fragment;
    }

    public PetPreviewsListFragment() {
        // Required empty public constructor
    }


    //*****************************************************************************************************************************
    // onCreate.
    // Receives information for the selected species to create pet previews. Creates a new loginController object.
    //*****************************************************************************************************************************

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            selectedPetSpecies = getArguments().getString(ARG_PET_SPECIES_SELECTED);
        }

        // A handler responsible only to check if user has already taken care of login. (Another is instantiated by the LoginActivity class.)
        localLoginController = new LoginController((AppCompatActivity) getActivity());

        if(savedInstanceState != null)
        {
            onActivityCreated(savedInstanceState);
        }
    }


    //*****************************************************************************************************************************
    // onCreateView.
    // Inflates the layout for this fragment.
    //*****************************************************************************************************************************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet_previews_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*if (savedInstanceState != null) {

            selectedPetSpecies = savedInstanceState.getString("species"); // Remembering the species on screen.
        }*/

        // When the user turns the device from landscape to portrait, the fragment is no longer needed.
        // The main activity with only one panel appears. The code below is necessary to prevent application failure.
        View fragmentContainer = getActivity().findViewById(R.id.fragment_container);

        if (fragmentContainer == null) {
            this.onDestroy();
            return;
        }

        // Hides the hidden message for non-existent animals.
        hiddenText = (TextView) getActivity().findViewById(R.id.hidden_message);
        hiddenText.setVisibility(View.INVISIBLE);

        // Generates a cursor for all pets belonging to a particular species.
        petsOfSpecies = PetSpeciesListFragment.petsDatabase.generateCursorOfPetsBelongingToASpecies(selectedPetSpecies);

        // Specifying the species view.
        listView = (ListView) getActivity().findViewById(R.id.preview_pets_list);

        // Assigning a custom adapter to the list view.
        CursorAdapter listAdapter = new PetPreviewAdapter(getActivity(), petsOfSpecies, 0);
        listView.setAdapter(listAdapter);

        // Assigning a Listener to the custom list view.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            public void onItemClick(AdapterView<?> list, View row, int index, long rowId) {

                                                // Let's animate things a bit...
                                                Animation animation1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide);
                                                row.startAnimation(animation1);

                                                // Ensure that only logged in users have access to the pets' details.
                                                if(localLoginController.isUserAlreadyLoggedIn() != "" && localLoginController.isUserAlreadyLoggedIn() != null) {
                                                    // Create and handle intent. The cursor is waiting. Passing the position (chosen pet) to the BrowseActivity.
                                                    browseIntent = new Intent(getActivity(), BrowseActivity.class);
                                                    Cursor cursor = (Cursor) list.getAdapter().getItem(index);
                                                    browseIntent.putExtra(BrowseActivity.EXTRA_PET_CURSOR_POSITION, cursor.getPosition());
                                                    startActivity(browseIntent);
                                                }
                                                else
                                                {
                                                    // Anonymous users receive a message.
                                                    String toastMessage = getResources().getString(R.string.please_login);
                                                    Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
        );

        // Handling the empty category. Control is transferred to BrowseActivity without making the listView visible.
        if(petsOfSpecies.getCount() < 1)
        {
            listView.setVisibility(View.INVISIBLE);

            String message = getString(R.string.fairy_tale_message);
            message = message.replace("@Species", selectedPetSpecies);
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
        savedInstanceState.putString("species", selectedPetSpecies);       // Committing current species to memory.
    }


    //*****************************************************************************************************************************
    // onDestroy method.
    // Closing the active cursor (if active).
    //*****************************************************************************************************************************

    public void onDestroy(){
        super.onDestroy();
        if (!(petsOfSpecies.isClosed())) petsOfSpecies.close();
    }
}
