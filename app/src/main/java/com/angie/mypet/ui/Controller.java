//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.angie.mypet.R;
import com.angie.mypet.database.Pet;


public class Controller {


	BrowseActivity act;		              // A reference to the calling Browse Activity.
	int petCursorPosition;                // A memory to preserve current pet on screen.
	int cursorSize;                       // How many pets belong to a species.


	//*****************************************************************************************************************************
	// Constructor Method.
	//*****************************************************************************************************************************

	public Controller(BrowseActivity act)
	{
		this.act = act;
	}


	//*****************************************************************************************************************************
	// Initializes the Screen. Makes the pet indicated from PetPreviewsListFragment appear on screen.
	// Also enables/disables the buttons accordingly.
	//*****************************************************************************************************************************

	public void InitializeBrowsing(int petCursorPosition)
	{
		// Legacy code: Hide the hidden message for non-existent animals.
		TextView hiddenText = (TextView)act.findViewById(R.id.hidden_message);
		hiddenText.setVisibility(View.INVISIBLE);

		// Initialize variables.
		this.petCursorPosition = petCursorPosition;           // From intent.
		this.cursorSize = PetPreviewsListFragment.petsOfSpecies.getCount();

		// Make the pet visible.
		this.makePetAppear(petCursorPosition);

		// Handle "Previous" and "Next" buttons.
		Button previousButton = (Button) act.findViewById(R.id.button_previous);

		if (petCursorPosition == 0) {
			previousButton.setEnabled(false);
		}
		else {
			previousButton.setEnabled(true);
		}

		Button nextButton = (Button) act.findViewById(R.id.button_next);
		if (petCursorPosition == cursorSize-1) {
			nextButton.setEnabled(false);
		}
		else {
			nextButton.setEnabled(true);
		}
	}


	//*****************************************************************************************************************************
	// Makes a pet appear on screen.
	//*****************************************************************************************************************************

	private void makePetAppear(int petCursorPosition)
	{
		Pet pet = PetSpeciesListFragment.petsDatabase.transformDatabasePetToObjectPet(PetPreviewsListFragment.petsOfSpecies, petCursorPosition);

		ImageView photo = (ImageView)act.findViewById(R.id.pet_photo);
		int image = pet.getPhoto();
		photo.setImageResource(image);

		TextView name = (TextView) act.findViewById(R.id.pet_info_name);
		name.setText(pet.getName());
		name.setTextColor(Color.rgb(8,41,138));

		TextView chip = (TextView) act.findViewById(R.id.pet_chip_id);
		chip.setText(pet.getChipId());
		chip.setTextColor(Color.rgb(8,41,138));

		TextView anim = (TextView) act.findViewById(R.id.pet_info_animal);
		anim.setText(pet.getSpecies());
		anim.setTextColor(Color.rgb(8,41,138));

		TextView breed = (TextView) act.findViewById(R.id.pet_info_breed);
		breed.setText(pet.getBreed());
		breed.setTextColor(Color.rgb(8,41,138));

		TextView gender = (TextView) act.findViewById(R.id.pet_info_gender);
		gender.setText(pet.getGender());
		gender.setTextColor(Color.rgb(8,41,138));

		TextView bdate = (TextView) act.findViewById(R.id.pet_info_bdate);
		bdate.setText(pet.getDateOfBirthAsString());
		bdate.setTextColor(Color.rgb(8,41,138));

		TextView age = (TextView) act.findViewById(R.id.pet_info_age);
		age.setText(String.valueOf(pet.calculateAge()));
		age.setTextColor(Color.rgb(8,41,138));

		TextView colour = (TextView) act.findViewById(R.id.pet_info_colour);
		colour.setText(pet.getColour());
		colour.setTextColor(Color.rgb(8,41,138));

		TextView marks = (TextView) act.findViewById(R.id.pet_info_marks);
		marks.setText(pet.getDistinguishingMarks());
		marks.setTextColor(Color.rgb(8,41,138));

		TextView owner = (TextView) act.findViewById(R.id.pet_info_owner_name);
		owner.setText(pet.getOwnerName());
		owner.setTextColor(Color.rgb(8,41,138));

		TextView ownerAdd = (TextView) act.findViewById(R.id.pet_info_owner_address);
		if(act.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) // Portrait seems neater with wrapping.
		{
			String helper = pet.getOwnerAddress();
			int secondCommaPosition = helper.indexOf(",", helper.indexOf(",") + 1);
			if(secondCommaPosition != -1) helper = helper.substring(0, secondCommaPosition+1) + "\n" + helper.substring(secondCommaPosition+1, helper.length());
			ownerAdd.setText(helper);
		}
		else
		{
			ownerAdd.setText(pet.getOwnerAddress());
		}
		ownerAdd.setTextColor(Color.rgb(8,41,138));

		TextView ownerPhone = (TextView) act.findViewById(R.id.pet_info_owner_phone);
		ownerPhone.setText(pet.getOwnerPhone());
		ownerPhone.setTextColor(Color.rgb(8,41,138));

		TextView vet = (TextView) act.findViewById(R.id.pet_info_vet_name);
		vet.setText(pet.getVetName());
		vet.setTextColor(Color.rgb(8,41,138));

		TextView vetAdd = (TextView) act.findViewById(R.id.pet_info_vet_address);
		if(act.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) // Portrait seems neater with wrapping.
		{
			String helper = pet.getVetAddress();
			int secondCommaPosition = helper.indexOf(",", helper.indexOf(",") + 1);
			if(secondCommaPosition != -1) helper = helper.substring(0, secondCommaPosition+1) + "\n" + helper.substring(secondCommaPosition+1, helper.length());
			vetAdd.setText(helper);
		}
		else
		{
			vetAdd.setText(pet.getVetAddress());
		}
		vetAdd.setTextColor(Color.rgb(8,41,138));

		TextView vetPhone = (TextView) act.findViewById(R.id.pet_info_vet_phone);
		vetPhone.setText(pet.getVetPhone());
		vetPhone.setTextColor(Color.rgb(8,41,138));

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

		if (petCursorPosition != cursorSize-1)
		{
			PetPreviewsListFragment.petsOfSpecies.moveToNext();
		}
		else
		{
			// Just for safety reasons. The code will never enter this block. The button is already inactive.
			nextButton.setEnabled(false);
			return;
		}

		// Make the first next pet appear on screen.
		this.petCursorPosition = PetPreviewsListFragment.petsOfSpecies.getPosition();
		this.makePetAppear(PetPreviewsListFragment.petsOfSpecies.getPosition());

		// Handle "Previous" button. We are sure now that a previous pet exists.
		previousButton.setEnabled(true);

		// Handle "Next" button accordingly.
		if (petCursorPosition != cursorSize-1)
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

		if (petCursorPosition != 0)
		{
			PetPreviewsListFragment.petsOfSpecies.moveToPrevious();
		}
		else
		{
			// Just for safety reasons. The code will never enter this block. The button is already inactive.
			previousButton.setEnabled(false);
			return;
		}

		// Make the first previous pet appear on screen.
		this.petCursorPosition = PetPreviewsListFragment.petsOfSpecies.getPosition();
		this.makePetAppear(PetPreviewsListFragment.petsOfSpecies.getPosition());

		// Handle "Next" button. We are sure now that a next pet exists.
		nextButton.setEnabled(true);

		// Handle "Previous" button accordingly.
		if (petCursorPosition != 0)
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
		savedInstanceState.putInt("currentPet", petCursorPosition);                // Commiting current pet to memory.
	}


	//*****************************************************************************************************************************
	// Restores the Pet that has appeared last on screen.
	//*****************************************************************************************************************************

	public void restorePreviousState(Bundle savedInstanceState)
	{
		this.petCursorPosition = savedInstanceState.getInt("currentPet");
		InitializeBrowsing(petCursorPosition);
	}
}
