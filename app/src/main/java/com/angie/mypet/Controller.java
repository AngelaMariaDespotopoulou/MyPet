package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class to serve as an interface between the Main Activity and all other classes.
 * Created to implement a good design pattern.
 */

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class Controller {

    // Certain pet description aspects take particular values only.
    private enum Sexes {MAL, FEM, UKN};
    private enum HairColours {BLA, BRO, GOL, WHT, RED, GRA, BLU};
    private enum EyeColours {BLA, BRO, GOL, BLU, RED, GRA, GRN, ORG};

    MainActivity act;
    ListIterator<Pet> it;
    boolean prevCalled = false, nextCalled = false;

    public Controller(MainActivity act)
    {
        this.act = act;
    }

    // Initializes the App.
    public void InitializeApp()
    {
        this.CreateAndShowPetFirstCard();
    }


    //  Creates a hard-coded list of pets and makes the first of them to appear on screen.
    private void CreateAndShowPetFirstCard()
    {
        List<Pet> animalsList = new ArrayList<Pet>();
        // Note: For this version animals will stand alone. In the future they will be linked to owners and vets.

       Pet pet1 = new Pet("Nuvoletta", "Cat0001", R.drawable.cat_chartreux_nuvoletta, null, null, Sexes.MAL.toString(),
            new Date(2015-1900, 7, 23), HairColours.GRA.toString(), EyeColours.ORG.toString(), null, null, 25.4,
            4.5, true, true, true, "Cat", "Chartreux");
        pet1.setComments("Info:\t\tNuvoletta is a quiet partner, is fully vaccinated and neutered. A bit overweight. Likes warmth and freedom.");

        Pet pet2 = new Pet("Toby", "Cat0002", R.drawable.cat_britishblue_toby, null, null, Sexes.MAL.toString(),
                new Date(2017-1900, 1, 4), HairColours.BLU.toString(), EyeColours.BLU.toString(), null, null, 30.0,
                2.0, false, false, false, "Cat", "British Blue");
        pet2.setComments("Info:\t\tToby is a newborn kitty, not yet fully vaccinated. Still needs feeding. Has five siblings and is quite playful.");

        Pet pet3 = new Pet("Isis", "Cat0003", R.drawable.cat_persiancat_isis, null, null, Sexes.FEM.toString(),
                new Date(2014-1900, 5, 25), HairColours.WHT.toString(), EyeColours.GRN.toString(), null, null, 32.5,
                6.0, false, true, true, "Cat", "Persian");
        pet3.setComments("Info:\t\tIsis became a mother recently, gave birth to a litter of six kitties. Her health is optimal and is fully vaccinated.");

        Pet pet4 = new Pet("Hilda", "Cat0004", R.drawable.cat_siberiancat_hilda, null, null, Sexes.FEM.toString(),
                new Date(2016-1900, 8, 10), HairColours.BRO.toString() + " " +  HairColours.WHT.toString(),
                EyeColours.GRN.toString(), null, null, 28.2,
                3.5, false, true, true, "Cat", "Siberian");
        pet4.setComments("Info:\t\tHilda is adapted to live in very cold climates. She is a good hunter and able to survive on her own. Fully vaccinated.");

        Pet pet5 = new Pet("Rudy", "Dog0001", R.drawable.dog_beagle_rudy, null, null, Sexes.MAL.toString(),
                new Date(2016-1900, 11, 11), HairColours.BRO.toString() + " " +  HairColours.WHT.toString()
                + " " +  HairColours.BLA.toString(), EyeColours.BRO.toString(), null, null, 50.0,
                7.0, true, true, true, "Dog", "Beagle");
        pet5.setComments("Info:\t\tRudy is a fast runner and quite playful. Carries a birthmark behind the left ear. Will be sent to training school next month.");

        Pet pet6 = new Pet("Eva", "Dog0002", R.drawable.dog_bichonfrise_eva, null, null, Sexes.FEM.toString(),
                new Date(2013-1900, 9, 5), HairColours.WHT.toString(), EyeColours.BLA.toString(), null, null, 300.0,
                3.8, false, true, true, "Dog", "Bichon Frisé");
        pet6.setComments("Info:\t\tEva has recently won a beauty pageant. She is well trained and quiet, but not very used to living outdoors.");

        Pet pet7 = new Pet("Mika", "Dog0003", R.drawable.dog_cotondetulear_mika, null, null, Sexes.FEM.toString(),
                new Date(2017-1900, 3, 3), HairColours.WHT.toString(), EyeColours.BLA.toString(), null, null, 22.0,
                2.5, false, false, false, "Dog", "Cotonde Tulear");
        pet7.setComments("Info:\t\tMika is a newborn puppy that is still breastfeeding. Her mother is still overprotective of her, however her health is optimal.");

        Pet pet8 = new Pet("Fulstaf", "Dog0004", R.drawable.dog_icelandicsheepdog_fulstaf, null, null, Sexes.MAL.toString(),
                new Date(2011-1900, 4, 4), HairColours.BRO.toString() + " " +  HairColours.WHT.toString(),
                EyeColours.BLA.toString(), null, null, 70.0,
                6.0, true, false, true, "Dog", "Icelandic Sheepdog");
        pet8.setComments("Info:\t\tFulstaf adores to live outdoors and is a trained Sheepdog. He is loyal, quick-of-foot and can put a good fight. Bears minor scars.");

        animalsList.add(pet1);
        animalsList.add(pet2);
        animalsList.add(pet3);
        animalsList.add(pet4);
        animalsList.add(pet5);
        animalsList.add(pet6);
        animalsList.add(pet7);
        animalsList.add(pet8);

        it = animalsList.listIterator();
        fetchNextPet();

        Button previousButton = (Button) act.findViewById(R.id.button_previous);
        previousButton.setEnabled(false);

    }

    // Fetches the next pet from the List. Enables/disables buttons accordingly.
    public void fetchNextPet()
    {
        Pet pet = null;
        Button nextButton = (Button) act.findViewById(R.id.button_next);
        Button previousButton = (Button) act.findViewById(R.id.button_previous);

        if (it!= null)
        {
            if (it.hasNext())
            {
                pet = (Pet) it.next();
                if(prevCalled) pet = (Pet) it.next();  // This solves an Iterator perk.
                this.makePetAppear(pet);
                previousButton.setEnabled(true);
            }

            if (!(it.hasNext()))
            {
               nextButton.setEnabled(false);
            }
        }

        prevCalled = false;
        nextCalled = true;
    }

    // Fetches the previous pet from the List. Enables/disables buttons accordingly.
    public void fetchPreviousPet()
    {
        Pet pet = null;
        Button nextButton = (Button) act.findViewById(R.id.button_next);
        Button previousButton = (Button) act.findViewById(R.id.button_previous);

        if (it!= null)
        {
            if (it.hasPrevious())
            {
                pet = (Pet) it.previous();
                if(nextCalled) pet = (Pet) it.previous();    // This solves an Iterator perk.
                this.makePetAppear(pet);
                nextButton.setEnabled(true);
            }

            if (!(it.hasPrevious()))
            {
                previousButton.setEnabled(false);
            }
        }

        prevCalled = true;
        nextCalled = false;
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
        //wei.setTextColor(Color.rgb(8,41,138));

        /*if (it!= null && it.hasNext() == false)
        {
            Button nextButton = (Button) act.findViewById(R.id.button_next);
            nextButton.setEnabled(false);
        }

        if (it!= null && it.hasPrevious() == false)
        {
            Button previousButton = (Button) act.findViewById(R.id.button_previous);
            previousButton.setEnabled(false);
        }*/
    }
}
