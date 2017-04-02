//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 2nd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Controller {

	// Certain pet description aspects take particular values only.
	BrowseActivity act;		    // A reference to the calling Browse Activity.
	String species;             // A memory to preserve the species in question.
	String currentPetId;        // A memory to preserve the id of the pet that currently appears on screen.
	Pet currentPet;             // A memory to preserve the pet that currently appears on screen.

	// Constructor Method.
	public Controller(BrowseActivity act)
	{
		this.act = act;
	}


	//*****************************************************************************************************************************
	// Initializes the Screen. Makes the pet indicated from PetPreviewActivity appear on screen.
	// Also enables/disables the buttons accordingly.
	//*****************************************************************************************************************************

	public void InitializeBrowsing(String species, String currentPetId)
	{
		// Legacy code: Hide the hidden message for non-existent animals.
		TextView hiddenText = (TextView)act.findViewById(R.id.hidden_message);
		hiddenText.setVisibility(View.INVISIBLE);

		// Initialize variables.
		this.species = species;                                 					// From intent.
		this.currentPetId = currentPetId;                       					// From intent.
		this.currentPet = MainActivity.petsDatabase.getPetById(currentPetId);		// Practically from intent.

		// Make the pet visible.
		this.makePetAppear(currentPet);

		// Handle "Previous" and "Next" buttons.
		Button previousButton = (Button) act.findViewById(R.id.button_previous);
		if (MainActivity.petsDatabase.hasPrevious(currentPetId, species))
			previousButton.setEnabled(true);
		else
			previousButton.setEnabled(false);

		Button nextButton = (Button) act.findViewById(R.id.button_next);
		if (MainActivity.petsDatabase.hasNext(currentPetId, species))
			nextButton.setEnabled(true);
		else
			nextButton.setEnabled(false);
	}


	//*****************************************************************************************************************************
	// Makes a pet appear on screen.
	//*****************************************************************************************************************************

	private void makePetAppear(Pet pet)
	{
		ImageView photo = (ImageView)act.findViewById(R.id.pet_photo);
		int image = pet.getPhoto();
		photo.setImageResource(image);

		TextView name = (TextView) act.findViewById(R.id.pet_info_name);
		name.setText(pet.getName());
		name.setTextColor(Color.rgb(8,41,138));

		TextView idnum = (TextView) act.findViewById(R.id.pet_info_id);
		idnum.setText(pet.getPetId());
		idnum.setTextColor(Color.rgb(8,41,138));

		TextView anim = (TextView) act.findViewById(R.id.pet_info_animal);
		anim.setText(pet.getTypeOfAnimal());
		anim.setTextColor(Color.rgb(8,41,138));

		TextView breed = (TextView) act.findViewById(R.id.pet_info_breed);
		breed.setText(pet.getBreed());
		breed.setTextColor(Color.rgb(8,41,138));

		TextView sex = (TextView) act.findViewById(R.id.pet_info_sex);
		sex.setText(pet.getSex());
		sex.setTextColor(Color.rgb(8,41,138));

		TextView bdate = (TextView) act.findViewById(R.id.pet_info_bdate);
		bdate.setText(pet.getDateOfBirthAsString());
		bdate.setTextColor(Color.rgb(8,41,138));

		TextView age = (TextView) act.findViewById(R.id.pet_info_age);
		age.setText(String.valueOf(pet.calculateAge()));
		age.setTextColor(Color.rgb(8,41,138));

		TextView hair = (TextView) act.findViewById(R.id.pet_info_hair);
		hair.setText(pet.getHairColour());
		hair.setTextColor(Color.rgb(8,41,138));

		TextView eye = (TextView) act.findViewById(R.id.pet_info_eyes);
		eye.setText(pet.getEyeColour());
		eye.setTextColor(Color.rgb(8,41,138));

		TextView hei = (TextView) act.findViewById(R.id.pet_info_height);
		hei.setText(String.valueOf(pet.getHeightInCm()) + " cm");
		hei.setTextColor(Color.rgb(8,41,138));

		TextView wei = (TextView) act.findViewById(R.id.pet_info_weight);
		wei.setText(String.valueOf(pet.getWeightInKilos())+ " kilos");
		wei.setTextColor(Color.rgb(8,41,138));

		TextView comm = (TextView) act.findViewById(R.id.pet_info_comments);
		comm.setText(String.valueOf(pet.getComments()));
		//comm.setTextColor(Color.rgb(8,41,138));
	}


	//*****************************************************************************************************************************
	// Fetches the next pet on screen. Handles the buttons accordingly.
	//*****************************************************************************************************************************

	public void fetchNextPet()
	{
		// Locate our buttons.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		Button previousButton = (Button) act.findViewById(R.id.button_previous);

		// Specify the first next pet of the species in question.
		Pet nextPet = MainActivity.petsDatabase.getNextPet(currentPetId, species);

		// Just for safety reasons. The code will never enter this block. The button is already inactive.
		if (nextPet == null)
		{
			nextButton.setEnabled(false);
			return;
		}

		// Make the first next pet appear on screen.
		this.makePetAppear(nextPet);
		currentPetId = nextPet.getPetId();
		currentPet = nextPet;

		// Handle "Previous" button. We are sure now that a previous pet exists.
		previousButton.setEnabled(true);

		// Handle "Next" button accordingly.
		if (MainActivity.petsDatabase.hasNext(currentPetId, species))
			nextButton.setEnabled(true);
		else
			nextButton.setEnabled(false);
	}


	//*****************************************************************************************************************************
	// Fetches the previous pet on screen. Handles the buttons accordingly.
	//*****************************************************************************************************************************

	public void fetchPreviousPet()
	{
		// Locate our buttons.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		Button previousButton = (Button) act.findViewById(R.id.button_previous);

		// Specify the first previous pet of the species in question.
		Pet previousPet = MainActivity.petsDatabase.getPreviousPet(currentPetId, species);

		// Just for safety reasons. The code will never enter this block. The button is already inactive.
		if (previousPet == null)
		{
			previousButton.setEnabled(false);
			return;
		}

		// Make the first previous pet appear on screen.
		this.makePetAppear(previousPet);
		currentPetId = previousPet.getPetId();
		currentPet = previousPet;

		// Handle "Next" button. We are sure now that a next pet exists.
		nextButton.setEnabled(true);

		// Handle "Previous" button accordingly.
		if (MainActivity.petsDatabase.hasPrevious(currentPetId, species))
			previousButton.setEnabled(true);
		else
			previousButton.setEnabled(false);
	}


	//*****************************************************************************************************************************
	// onSaveInstanceState method.
	// Committing elements to memory for safe recovery. Saves the Pet that has appeared last on screen.
	//*****************************************************************************************************************************

	public void saveState(Bundle savedInstanceState)
	{
		savedInstanceState.putString("currentPet", currentPetId);                // Commiting current Id pet to memory.
		savedInstanceState.putString("species", species);                        // Commiting current species to memory.
	}


	//*****************************************************************************************************************************
	// Restores the Pet that has appeared last on screen.
	//*****************************************************************************************************************************

	public void restorePreviousState(Bundle savedInstanceState)
	{
		species = savedInstanceState.getString("species");
		currentPetId = savedInstanceState.getString("currentPet");
		InitializeBrowsing(species, currentPetId);
	}
}
