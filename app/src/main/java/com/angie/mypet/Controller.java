package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class to serve as an interface between the Main Activity and all other classes.
 * Created to implement a good design pattern.
 */

import android.graphics.Color;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import android.util.Log;


public class Controller {

	// Certain pet description aspects take particular values only.
	Map hairColour;			    // The pet's fur colour.
	Map eyeColour;			    // The pet's eye colour.
	Map sexes;				    // The pet's sex.
	BrowseActivity act;		    // A reference to the calling Browse Activity.
	Map pets;                   // A data structure to maintain a pets database.
	Map otherpets;              // A helping data structure used in the "Other" pets case.
	String species;             // A memory to preserve the species in question.
	String currentPetId;        // A memory to preserve the pet that currently appears on screen.
	String latestIndexFetched;  // A memory to preserve the map id of pet latest returned from a getNext, getPrevious method. Not always equal to "currentPetId".

	public Controller(BrowseActivity act)
	{
		// Initializing variables.
		eyeColour = new HashMap();
		eyeColour.put("BLA", "Black");
		eyeColour.put("BLU", "Blue");
		eyeColour.put("BRO", "Brown");
		eyeColour.put("GOL", "Golden");
		eyeColour.put("RED", "Red");
		eyeColour.put("GRA", "Gray");
		eyeColour.put("GRN", "Green");
		eyeColour.put("ORG", "Orange");

		hairColour = new HashMap();
		hairColour.put("BLA", "Black");
		hairColour.put("BLU", "Blue");
		hairColour.put("BRO", "Brown");
		hairColour.put("GOL", "Golden");
		hairColour.put("RED", "Red");
		hairColour.put("GRA", "Gray");
		hairColour.put("WHT", "White");
		hairColour.put("ORG", "Orange");
		hairColour.put("YEL", "Yellow");
		hairColour.put("GRN", "Green");

		sexes = new HashMap();
		sexes.put("MAL", "Male");
		sexes.put("FEM", "Female");
		sexes.put("UNK", "Unknown");

		this.act = act;
	}


	// Initializes the App. Creates a hard-coded data structure of pets.
	public void InitializeBrowsing(String species)
	{
		// Note: For this version animals will stand alone. In the future they will be linked to owners and vets.

		// Hide the hidden message for non-existent animals.
		TextView hiddenText = (TextView)act.findViewById(R.id.hidden_message);
		hiddenText.setVisibility(View.INVISIBLE);

		// Create the database.
		pets = new HashMap();

		// Save the species to memory.
		this.species = species;

		// No pets on memory yet.
		currentPetId = "";

		// Create hard-coded pets. These would normally be provided by an external database.
		Pet pet1 = new Pet("Nuvoletta", "Cat0001", R.drawable.cat_chartreux_nuvoletta, null, null, sexes.get("MAL").toString(),
				new Date(2015-1900, 7, 23), hairColour.get("GRA").toString(), eyeColour.get("ORG").toString(), null, null, 25.4,
				4.5, true, true, true, "Cat", "Chartreux");
		pet1.setComments("Info:\t\tNuvoletta is a quiet partner, is fully vaccinated and neutered. A bit overweight. Likes warmth and freedom.");

		Pet pet2 = new Pet("Toby", "Cat0002", R.drawable.cat_britishblue_toby, null, null,  sexes.get("MAL").toString(),
				new Date(2017-1900, 1, 4), hairColour.get("BLU").toString(), eyeColour.get("BLU").toString(), null, null, 30.0,
				2.0, false, false, false, "Cat", "British Blue");
		pet2.setComments("Info:\t\tToby is a newborn kitty, not yet fully vaccinated. Still needs feeding. Has five siblings and is quite playful.");

		Pet pet3 = new Pet("Isis", "Cat0003", R.drawable.cat_persiancat_isis, null, null,  sexes.get("FEM").toString(),
				new Date(2014-1900, 5, 25), hairColour.get("WHT").toString(), eyeColour.get("GRN").toString(), null, null, 32.5,
				6.0, false, true, true, "Cat", "Persian");
		pet3.setComments("Info:\t\tIsis became a mother recently, gave birth to a litter of six kitties. Her health is optimal and is fully vaccinated.");

		Pet pet4 = new Pet("Hilda", "Cat0004", R.drawable.cat_siberiancat_hilda, null, null,  sexes.get("FEM").toString(),
				new Date(2016-1900, 8, 10), hairColour.get("BRO").toString() + " and " +  hairColour.get("WHT").toString(),
				eyeColour.get("GRN").toString(), null, null, 28.2,
				3.5, false, true, true, "Cat", "Siberian");
		pet4.setComments("Info:\t\tHilda is adapted to live in very cold climates. She is a good hunter and able to survive on her own. Fully vaccinated.");

		Pet pet5 = new Pet("Rudy", "Dog0001", R.drawable.dog_beagle_rudy, null, null,  sexes.get("MAL").toString(),
				new Date(2016-1900, 11, 11), hairColour.get("BRO").toString() + " and " +  hairColour.get("WHT").toString()
				+ " and " +  hairColour.get("BLA").toString(), eyeColour.get("BRO").toString(), null, null, 50.0,
				7.0, true, true, true, "Dog", "Beagle");
		pet5.setComments("Info:\t\tRudy is a fast runner and quite playful. Carries a birthmark behind the left ear. Will be sent to training school next month.");

		Pet pet6 = new Pet("Eva", "Dog0002", R.drawable.dog_bichonfrise_eva, null, null,  sexes.get("FEM").toString(),
				new Date(2013-1900, 9, 5), hairColour.get("WHT").toString(), eyeColour.get("BLA").toString(), null, null, 300.0,
				3.8, false, true, true, "Dog", "Bichon Frisé");
		pet6.setComments("Info:\t\tEva has recently won a beauty pageant. She is well trained and quiet, but not very used to living outdoors.");

		Pet pet7 = new Pet("Mika", "Dog0003", R.drawable.dog_cotondetulear_mika, null, null,  sexes.get("FEM").toString(),
				new Date(2017-1900, 3, 3), hairColour.get("WHT").toString(), eyeColour.get("BLA").toString(), null, null, 22.0,
				2.5, false, false, false, "Dog", "Cotonde Tulear");
		pet7.setComments("Info:\t\tMika is a newborn puppy that is still breastfeeding. Her mother is still overprotective of her, however her health is optimal.");

		Pet pet8 = new Pet("Fulstaf", "Dog0004", R.drawable.dog_icelandicsheepdog_fulstaf, null, null, sexes.get("MAL").toString(),
				new Date(2011-1900, 4, 4), hairColour.get("BRO").toString() + " and " +  hairColour.get("WHT").toString(),
				eyeColour.get("BLA").toString(), null, null, 70.0,
				6.0, true, false, true, "Dog", "Icelandic Sheepdog");
		pet8.setComments("Info:\t\tFulstaf adores to live outdoors and is a trained Sheepdog. He is loyal, quick-of-foot and can put a good fight. Bears minor scars.");

		Pet pet9 = new Pet("Rorry", "Parrot0001", R.drawable.parrot_chattering_rorry, null, null, sexes.get("MAL").toString(),
				new Date(2007-1900, 8, 1), hairColour.get("RED").toString() + " and " +  hairColour.get("GRN").toString(),
				eyeColour.get("GOL").toString(), null, null, 28.0,
				0.5, false, false, false, "Parrot", "Chattering");
		pet9.setComments("Info:\t\tRorry's favourite food is pollen and nectar. He loves to bathe and hang upside down. He is not friendly to other birds, though.");

		Pet pet10 = new Pet("Frieda", "Canary0001", R.drawable.canary_gloster_frieda, null, null, sexes.get("FEM").toString(),
				new Date(2015-1900, 11, 23), hairColour.get("YEL").toString() + " and " +  hairColour.get("GRA").toString(),
				eyeColour.get("BLA").toString(), null, null, 11.0,
				0.28, false, false, false, "Canary", "Gloster Fancy");
		pet10.setComments("Info:\t\tFrieda is independent and does not like interaction with people. She can be territorial at times and likes to fly around in her cage.");

		Pet pet11 = new Pet("Leonardo", "Turtle0001", R.drawable.turtle_reeves_leonardo, null, null, sexes.get("MAL").toString(),
				new Date(2007-1900, 3, 10), hairColour.get("GRN").toString() + " and " +  hairColour.get("BLA").toString(),
				eyeColour.get("BLA").toString(), null, null, 11.0,
				0.28, false, false, false, "Turtle", "Reeve's");
		pet11.setComments("Info:\t\tLeonardo and is lively and active and needs to have ample space. He lives happily in a swallow water tank. He dreams of becoming a ninja.");

		Pet pet12 = new Pet("Lucy", "Bunny0001", R.drawable.bunny_minilop_lucy, null, null, sexes.get("FEM").toString(),
				new Date(2017-1900, 3, 1), hairColour.get("WHT").toString(), eyeColour.get("RED").toString(), null, null, 20.0,
				0.5, false, false, false, "Bunny", "Miniature Lion Lop");
		pet12.setComments("Info:\t\tLucy enjoys caresses and grooming. She likes playing with toys such as cardboard tubes and boxes. She likes company and attention.");


		// Add the pets to the database.
		pets.put(pet1.getPetId(), pet1);
		pets.put(pet2.getPetId(), pet2);
		pets.put(pet3.getPetId(), pet3);
		pets.put(pet4.getPetId(), pet4);
		pets.put(pet5.getPetId(), pet5);
		pets.put(pet6.getPetId(), pet6);
		pets.put(pet7.getPetId(), pet7);
		pets.put(pet8.getPetId(), pet8);
		pets.put(pet9.getPetId(), pet9);
		pets.put(pet10.getPetId(), pet10);
		pets.put(pet11.getPetId(), pet11);
		pets.put(pet12.getPetId(), pet12);

		// If the species is "Other" the Map undergoes some changes. With an external database the method would be to create an-easy-to-handle table copy or key. Here
		// we use a less sophisticated method.
		if(species.equals("Other")) {

			otherpets = new HashMap();

			int currentIndex = 1;
			String nextIndexAsString = "0000";

			Iterator<Integer> itr = pets.keySet().iterator();
			while (itr.hasNext()) {
				Pet current = (Pet) pets.get(itr.next());
				if ( (current.getTypeOfAnimal().equals("Dog") == false) &&
						(current.getTypeOfAnimal().equals("Cat") == false) &&
						(current.getTypeOfAnimal().equals("Unicorn") == false)) {


					nextIndexAsString = "0000";
					nextIndexAsString = nextIndexAsString + String.valueOf(currentIndex);
					nextIndexAsString = nextIndexAsString.substring(nextIndexAsString.length()-4);
					nextIndexAsString = species.concat(nextIndexAsString);
					otherpets.put(nextIndexAsString, current);
					currentIndex ++;
				}
			}

			pets = otherpets;
		}

		// Specify the first pet of the species in question. Make it appear on screen.
		Pet firstPetOfSpecies = getNextPet();

		if (firstPetOfSpecies == null)
		{
			View layout = act.findViewById(R.id.photo_area);
			View layout2 = act.findViewById(R.id.buttons);
			View layout3 = act.findViewById(R.id.scrollArea);
			layout.setVisibility(View.INVISIBLE);
			layout2.setVisibility(View.INVISIBLE);
			layout3.setVisibility(View.INVISIBLE);

			String message = act.getString(R.string.fairy_tale_message);
			message = message.replace("@Species", species);
			hiddenText.setText(message);
			hiddenText.setVisibility(View.VISIBLE);
			currentPetId = null;
			return;
		}

		this.makePetAppear(firstPetOfSpecies);
		currentPetId = firstPetOfSpecies.getPetId();

		// Handle "Previous" button.
		Button previousButton = (Button) act.findViewById(R.id.button_previous);
		previousButton.setEnabled(false);

		// Confirm existence of a second pet of the species in question.
		Pet secondPetOfSpecies = getNextPet();

		// Handle "Next" button.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		if (secondPetOfSpecies == null)
		{
			nextButton.setEnabled(false);
		}
		else
		{
			nextButton.setEnabled(true);
		}
	}


	// Searches the next pet at the database. Returns null if nothing is found.
	public Pet getNextPet()
	{
		// For the first Pet only
		if(currentPetId.equals(""))
		{
			return (Pet)pets.get(species + "0001");
		}
		else {
			String currentIndexAsString = currentPetId.substring(currentPetId.length() - 4);
			Integer currentIndex = new Integer(currentIndexAsString);
			currentIndex++;
			String nextIndexAsString = "0000";
			nextIndexAsString = nextIndexAsString + String.valueOf(currentIndex);
			nextIndexAsString = nextIndexAsString.substring(nextIndexAsString.length() - 4);
			nextIndexAsString = species.concat(nextIndexAsString);
			latestIndexFetched = nextIndexAsString;
			return (Pet) pets.get(nextIndexAsString);
		}
	}

	// Fetches to screen the next pet from the database. Assumes that one is already on screen. Enables/disables buttons accordingly.
	public void fetchNextPet(String species)
	{
		// Locate our buttons.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		Button previousButton = (Button) act.findViewById(R.id.button_previous);

		// Specify the first next pet of the species in question. 
		Pet firstPetOfSpecies = getNextPet();

		// Just for safety reasons. The code will never enter this block. The button is already inactive.
		if (firstPetOfSpecies == null)
		{
			nextButton.setEnabled(false);
			return;
		}

		// Make the first next pet appear on screen.
		this.makePetAppear(firstPetOfSpecies);
		currentPetId = latestIndexFetched;

		// Handle "Previous" button. We are sure now that a previous pet exists.
		previousButton.setEnabled(true);

		// Confirm existence of a second pet of the species in question.
		Pet secondPetOfSpecies = getNextPet();

		// Handle "Next" button accordingly.
		if (secondPetOfSpecies == null)
		{
			nextButton.setEnabled(false);
		}
		else
		{
			nextButton.setEnabled(true);
		}
	}

	// Searches the previous pet at the database. Returns null if nothing is found.
	public Pet getPreviousPet()
	{
		String currentIndexAsString = currentPetId.substring(currentPetId.length()-4);
		Integer currentIndex = new Integer(currentIndexAsString);
		currentIndex --;
		if(currentIndex < 1) return null;
		String nextIndexAsString = "0000";
		nextIndexAsString = nextIndexAsString + String.valueOf(currentIndex);
		nextIndexAsString = nextIndexAsString.substring(nextIndexAsString.length()-4);
		nextIndexAsString = species.concat(nextIndexAsString);
		latestIndexFetched = nextIndexAsString;
		return (Pet)pets.get(nextIndexAsString);
	}

	// Fetches to screen the previous pet from the database. Assumes that one is already on screen. Enables/disables buttons accordingly.
	public void fetchPreviousPet(String species)
	{
		// Locate our buttons.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		Button previousButton = (Button) act.findViewById(R.id.button_previous);

		// Specify the first previous pet of the species in question. 
		Pet firstPetOfSpecies = getPreviousPet();

		// Just for safety reasons. The code will never enter this block. The button is already inactive.
		if (firstPetOfSpecies == null)
		{
			nextButton.setEnabled(false);
			return;
		}

		// Make the first next pet appear on screen.
		this.makePetAppear(firstPetOfSpecies);
		currentPetId = latestIndexFetched;

		// Handle "Next" button. We are sure now that a next pet exists.
		nextButton.setEnabled(true);

		// Confirm existence of a second pet of the species in question.
		Pet secondPetOfSpecies = getPreviousPet();

		// Handle "Previous" button accordingly.
		if (secondPetOfSpecies == null)
		{
			previousButton.setEnabled(false);
		}
		else
		{
			previousButton.setEnabled(true);
		}
	}

	// Makes a pet to appear on screen.
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


	// Saves the Pet that has appeared last on screen.
	public void saveState(Bundle savedInstanceState)
	{
		savedInstanceState.putString("currentPet", currentPetId);                // Commiting current pet to memory.
		savedInstanceState.putString("latestIndexedPet", latestIndexFetched);    // Commiting latest read pet to memory.
		savedInstanceState.putString("species", species);                        // Commiting current species to memory.
	}


	// Restores the Pet that has appeared last on screen.
	public void restorePreviousState(Bundle savedInstanceState)
	{
		species = savedInstanceState.getString("species");               // Remembering the species on screen.
		InitializeBrowsing(species);

		if (currentPetId == null)
		{
			View layout = act.findViewById(R.id.photo_area);
			View layout2 = act.findViewById(R.id.buttons);
			View layout3 = act.findViewById(R.id.scrollArea);
			layout.setVisibility(View.INVISIBLE);
			layout2.setVisibility(View.INVISIBLE);
			layout3.setVisibility(View.INVISIBLE);

			TextView hiddenText = (TextView)act.findViewById(R.id.hidden_message);
			String message = act.getString(R.string.fairy_tale_message);
			message = message.replace("@Species", species);
			hiddenText.setText(message);
			hiddenText.setVisibility(View.VISIBLE);
			return;
		}

		currentPetId = savedInstanceState.getString("currentPet");
		if(species.equals("Other")) {  // Correction for problem that appears under some circumstances.
			currentPetId = "Other" + currentPetId.substring(currentPetId.length()-4);
		}
		this.makePetAppear((Pet) pets.get(currentPetId));
		latestIndexFetched = currentPetId;

		// Confirm existence of a next pet of the species in question.
		Pet nextPetOfSpecies = getNextPet();

		// Handle "Next" button accordingly.
		Button nextButton = (Button) act.findViewById(R.id.button_next);
		if (nextPetOfSpecies == null)
		{
			nextButton.setEnabled(false);
		}
		else
		{
			nextButton.setEnabled(true);
		}

		// Confirm existence of a previous pet of the species in question.
		Button previousButton = (Button) act.findViewById(R.id.button_previous);
		Pet previousPetOfSpecies = getPreviousPet();

		// Handle "Previous" button accordingly.
		if (previousPetOfSpecies == null)
		{
			previousButton.setEnabled(false);
		}
		else
		{
			previousButton.setEnabled(true);
		}
	}
}
