//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 2nd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PetsDatabase {

    // Data structures.
    private Map pets;                               // The main data structure to store pets.
    private ArrayList<String> species;              // A list to store all available species.
    private ArrayList<Pet> petsOfSpecies;           // A list to store pets (for various reasons).

    // Available pet characteristics.
    Map hairColour;			                        // The pet's fur colour.
    Map eyeColour;			                        // The pet's eye colour.
    Map sexes;				                        // The pet's sex.

    // Constructor.
    public PetsDatabase()
    {
        this.initializePetCharacteristics();
        this.initializePetsDatabase();
    }


    //*****************************************************************************************************************************
    // Initializes secondary pet characteristics.
    //*****************************************************************************************************************************

    private void initializePetCharacteristics()
    {
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
        hairColour.put("CYA", "Cyan");

        sexes = new HashMap();
        sexes.put("MAL", "Male");
        sexes.put("FEM", "Female");
        sexes.put("UNK", "Unknown");
    }


    //*****************************************************************************************************************************
    // Creates a data structure containing all the Pets.
    // Assumes hard-coded pets. These would normally be provided by an external database.
    //*****************************************************************************************************************************

    public void initializePetsDatabase()
    {
        // Initialize database.
        pets = new LinkedHashMap();

        // Create hard-coded pets.
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

        Pet pet13 = new Pet("Eda", "Parrot0002", R.drawable.parrot_parrotlet_eda, null, null, sexes.get("FEM").toString(),
                new Date(2011-1900, 10, 1), hairColour.get("CYA").toString() + " and " +  hairColour.get("GRA").toString(),
                eyeColour.get("GRA").toString(), null, null, 25.0,
                0.4, false, false, false, "Parrot", "Parrotlet");
        pet13.setComments("Info:\t\tEda is very playful, smart and curious. Her favourite food are black berries, green beans and hazelnuts.");

        Pet pet14 = new Pet("Henry", "Spider0001", R.drawable.spider_pinktoe_henry, null, null, sexes.get("MAL").toString(),
                new Date(2012-1900, 04, 10), hairColour.get("BLA").toString() + " and " +  hairColour.get("RED").toString(),
                eyeColour.get("BLA").toString(), null, null, 15.0, 0.1, false, false, false, "Spider", "Pink Toe Tarantula");
        pet14.setComments("Info:\t\tHenry is quite industrious and able to build himself quite complex silk hides. He is also very good at jumping.");

        Pet pet15 = new Pet("Furball", "Guinea Pig0001", R.drawable.guineapig_peruvian_furball, null, null, sexes.get("MAL").toString(),
                new Date(2016-1900, 07, 13), hairColour.get("BRO").toString() + " and " +  hairColour.get("RED").toString(),
                eyeColour.get("BLA").toString(), null, null, 30.0, 0.5, false, false, false, "Guinea Pig", "Peruvian Guinea Pig");
        pet15.setComments("Info:\t\tFurball, besides very hairy, is also energetic and alert. He has overall a great personality and enjoys the company of humans.");

        Pet pet16 = new Pet("Satine", "Guinea Pig0002", R.drawable.guineapig_silkie_satine, null, null, sexes.get("FEM").toString(),
                new Date(2016-1900, 07, 13), hairColour.get("BLA").toString(), eyeColour.get("BLA").toString(), null, null, 25.0, 0.4, false, false, false,
                "Guinea Pig", "Silkie Guinea Pig");
        pet16.setComments("Info:\t\tSatine has the hairstyle of a Hollywood celebrity. Her locks of hair are very soft and shiny, making her great for kids to pet and interact with.");

        Pet pet17 = new Pet("Rhea", "Turtle0002", R.drawable.turtle_box_rhea, null, null, sexes.get("FEM").toString(),
                new Date(1998-1900, 2, 6), hairColour.get("GRN").toString() + " and " +  hairColour.get("YEL").toString(),
                eyeColour.get("GRN").toString(), null, null, 25.0, 0.50, false, false, false, "Turtle", "Box Turtle");
        pet17.setComments("Info:\t\tRhea has a high-domed shell and is expected to live up to 80 years. She requires fresh, clean drinking water daily and enjoys a varied diet.");

        Pet pet18 = new Pet("Pauline", "Turtle0003", R.drawable.turtle_redeared_pauline, null, null, sexes.get("FEM").toString(),
                new Date(2004-1900, 2, 6), hairColour.get("GRN").toString() + ", " +  hairColour.get("YEL").toString() + " and " +  hairColour.get("RED").toString(),
                eyeColour.get("GRN").toString(), null, null, 30.0, 1.50, false, false, false, "Turtle", "Red-eared slider");
        pet18.setComments("Info:\t\tPauline boasts a bright red stripe behind each eye. An aquatic turtle, she requires an aquarium with water. She likes being outdoors on warm days so she can enjoy the sunshine.");

        Pet pet19 = new Pet("Tweety", "Canary0002", R.drawable.canary_fife_tweety, null, null, sexes.get("MAL").toString(),
                new Date(2014-1900, 6, 15), hairColour.get("YEL").toString(), eyeColour.get("BLA").toString(), null, null, 14.0,
                0.32, false, false, false, "Canary", "Fife");
        pet19.setComments("Info:\t\tTweety is as popular in the neighbourhood as is his famous namesake. He song is nerve-soothing and keeps excellent company.");

        Pet pet20 = new Pet("Rock", "Bunny0002", R.drawable.bunny_flemish_rock, null, null, sexes.get("MAL").toString(),
                new Date(2014-1900, 3, 1), hairColour.get("WHT").toString()+ " and " +  hairColour.get("GOL").toString(), eyeColour.get("BLU").toString(), null, null, 80.0,
                7.0, false, false, false, "Bunny", "Flemish Giant");
        pet20.setComments("Info:\t\tRock is the ideal pet for hugs. He belongs to an old breed of rabbit thought to have originated from the Flemish region as early as the 16th or 17th century.");

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
        pets.put(pet13.getPetId(), pet13);
        pets.put(pet14.getPetId(), pet14);
        pets.put(pet15.getPetId(), pet15);
        pets.put(pet16.getPetId(), pet16);
        pets.put(pet17.getPetId(), pet17);
        pets.put(pet18.getPetId(), pet18);
        pets.put(pet19.getPetId(), pet19);
        pets.put(pet20.getPetId(), pet20);
    }


    //*****************************************************************************************************************************
    // Generates a list of all species contained in the database. The list contains unique Strings, listed alphabetically.
    //*****************************************************************************************************************************

    public ArrayList<String> generateSpeciesList() {

        // Initialise a data structure to keep the various species.
        species = new ArrayList();

        // Read all species of animals contained in the database. Add all to an arraylist.
        Iterator<Pet> it = pets.values().iterator();
        while (it.hasNext())
        {
            Pet currentPet = (Pet) it.next();
            species.add(currentPet.getTypeOfAnimal());
        }

        // Keep the fairy tale alive.
        species.add("Unicorn");

        // Remove duplicates by using a little trick. Transfer everything to a Set that does not allow duplicates and then back.
        Set<String> hs = new HashSet<>();
        hs.addAll(species);
        species.clear();
        species.addAll(hs);

        // Put the species in alphabetical order for aesthetic and practical reasons.
        Collections.sort(species, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        return species;
    }


    //*****************************************************************************************************************************
    // Returns all database contents stored in a Map.
    //*****************************************************************************************************************************

    public Map getPetsAsMap()
    {
        return this.pets;
    }


    //*****************************************************************************************************************************
    // Returns an ArrayList of all Pets belonging to a particular species.
    //*****************************************************************************************************************************

    public ArrayList<Pet> generateListOfPetsBelongingToASpecies(String species) {

        Pet currentPet;
        String speciesOfCurrentPet;

        // Initialise a data structure to keep the various pets belonging to a species.
        petsOfSpecies = new ArrayList();

        // Transfer from the main data structure only the pets belonging to a particular species.
        Iterator<Pet> it = pets.values().iterator();
        while (it.hasNext())
        {
            currentPet = (Pet) it.next();
            speciesOfCurrentPet = currentPet.getTypeOfAnimal();
            if(speciesOfCurrentPet.equals(species)) {
                petsOfSpecies.add(currentPet);
            }
        }

        return petsOfSpecies;
    }


    //*****************************************************************************************************************************
    // Returns a particular Pet when its id number is known.
    //*****************************************************************************************************************************

    public Pet getPetById(String petId)
    {
        return (Pet)pets.get(petId);
    }


    //*****************************************************************************************************************************
    // Confirms the existence of a next pet.
    //*****************************************************************************************************************************

    public boolean hasNext(String petId, String species)
    {
        String currentIndexAsString = petId.substring(petId.length() - 4);
        Integer currentIndex = new Integer(currentIndexAsString);
        currentIndex++;
        String nextIndexAsString = "0000";
        nextIndexAsString = nextIndexAsString + String.valueOf(currentIndex);
        nextIndexAsString = nextIndexAsString.substring(nextIndexAsString.length() - 4);
        nextIndexAsString = species.concat(nextIndexAsString);
        return pets.containsKey(nextIndexAsString);
    }


    //*****************************************************************************************************************************
    // Confirms the existence of a previous pet.
    //*****************************************************************************************************************************

    public boolean hasPrevious(String petId, String species)
    {
        String currentIndexAsString = petId.substring(petId.length() - 4);
        Integer currentIndex = new Integer(currentIndexAsString);
        currentIndex--;
        if(currentIndex < 1) return false;
        String previousIndexAsString = "0000";
        previousIndexAsString = previousIndexAsString + String.valueOf(currentIndex);
        previousIndexAsString = previousIndexAsString.substring(previousIndexAsString.length() - 4);
        previousIndexAsString = species.concat(previousIndexAsString);
        return pets.containsKey(previousIndexAsString);
    }


    //*****************************************************************************************************************************
    // Returns the next pet.
    //*****************************************************************************************************************************

    public Pet getNextPet(String petId, String species)
    {
        String currentIndexAsString = petId.substring(petId.length() - 4);
        Integer currentIndex = new Integer(currentIndexAsString);
        currentIndex++;
        String nextIndexAsString = "0000";
        nextIndexAsString = nextIndexAsString + String.valueOf(currentIndex);
        nextIndexAsString = nextIndexAsString.substring(nextIndexAsString.length() - 4);
        nextIndexAsString = species.concat(nextIndexAsString);
        if(pets.containsKey(nextIndexAsString)) return (Pet) pets.get(nextIndexAsString);
        return null;
    }


    //*****************************************************************************************************************************
    // Returns the previous pet.
    //*****************************************************************************************************************************

    public Pet getPreviousPet(String petId, String species)
    {
        String currentIndexAsString = petId.substring(petId.length() - 4);
        Integer currentIndex = new Integer(currentIndexAsString);
        currentIndex--;
        if(currentIndex < 1) return null;
        String previousIndexAsString = "0000";
        previousIndexAsString = previousIndexAsString + String.valueOf(currentIndex);
        previousIndexAsString = previousIndexAsString.substring(previousIndexAsString.length() - 4);
        previousIndexAsString = species.concat(previousIndexAsString);
        if(pets.containsKey(previousIndexAsString)) return (Pet) pets.get(previousIndexAsString);
        return null;
    }
}