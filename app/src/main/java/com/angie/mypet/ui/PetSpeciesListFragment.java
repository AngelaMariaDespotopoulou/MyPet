//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.angie.mypet.R;
import com.angie.mypet.database.PetsDatabase;

import java.util.List;

public class PetSpeciesListFragment extends Fragment {

    private List<String> species;                   // A list to store all available species.
    private ListView listView;                      // A list view widget.
    private BaseAdapter speciesListAdapter;         // An adapter for the listView widget.
    private Intent speciesIntent;                   // A message towards the PetPreviewActivity.
    static PetsDatabase petsDatabase;               // A database of pets.
    com.angie.mypet.ui.Menu appMenu;                   // A handler for the menu actions.

    public interface OnFragmentInteractionListener {
        void onPetSpeciesSelected(String selectedSpecies);
    }

    public static PetSpeciesListFragment newInstance() {
        return new PetSpeciesListFragment();
    }

    private OnFragmentInteractionListener mListener;

    public PetSpeciesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_species_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Creating the database containing all the Pets. (Hard-coded for the moment.)
        petsDatabase = new PetsDatabase(getActivity().getBaseContext());
        species = petsDatabase.generateSpeciesList();

        // Assigning a listener to the species view.
        listView = (ListView) getActivity().findViewById(R.id.species_list_view);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> list, View row, int index, long rowId) {

                        // Let's animate things a bit...
                        Animation animation1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide);
                        row.startAnimation(animation1);

                        // Create and handle intent. Pass the selected species to the PetPreviewFragment.
                        String choice = list.getAdapter().getItem(index).toString();
                        mListener.onPetSpeciesSelected(choice);
                    }
                }
        );

        // Aesthetic changes.
        listView.setCacheColorHint(0);
        listView.setBackgroundResource(R.drawable.vector);

        // Assigning a simple adapter (provided by Android) to the list view.
        speciesListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, species) {
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
    }


    //*****************************************************************************************************************************
    // onDestroy method.
    // Closing the active cursor.
    //*****************************************************************************************************************************
    public void onDestroy(){
        super.onDestroy();
        petsDatabase.closeConnectionToDatabase();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
