//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 30th April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PetsDatabase {

    // Data structures.
    private PetDatabaseHelper dbHelper;             // The pet database helper.
    private ArrayList<String> species;              // A list to store all available species.
    private ArrayList<Pet> petsOfSpecies;           // A list to store pets (for various reasons).
    SQLiteDatabase db;                              // Reference will be used multiple times.

    // Available pet characteristics.
    Map colours;			                        // The pet's fur colour.
    Map genders;				                    // The pet's gender.

    // Android context.
    Context context;

    // A cursor.
    Cursor cursor;

    // Queries components.
    private static final String[] SPECIES_PROJECTION = {PetManagementContract.Pet.COLUMN_NAME_SPECIES};

    private static final String[] PETS_PROJECTION =
            {
                    PetManagementContract.Pet._ID,
                    PetManagementContract.Pet.COLUMN_NAME_NAME,
                    PetManagementContract.Pet.COLUMN_NAME_DATE_OF_BIRTH,
                    PetManagementContract.Pet.COLUMN_NAME_GENDER,
                    PetManagementContract.Pet.COLUMN_NAME_SPECIES,
                    PetManagementContract.Pet.COLUMN_NAME_BREED,
                    PetManagementContract.Pet.COLUMN_NAME_COLOUR,
                    PetManagementContract.Pet.COLUMN_NAME_DISTINGUISHING_MARKS,
                    PetManagementContract.Pet.COLUMN_NAME_CHIP_ID,
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_NAME,
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_ADDRESS,
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_PHONE,
                    PetManagementContract.Pet.COLUMN_NAME_VET_NAME,
                    PetManagementContract.Pet.COLUMN_NAME_VET_ADDRESS,
                    PetManagementContract.Pet.COLUMN_NAME_VET_PHONE,
                    PetManagementContract.Pet.COLUMN_NAME_COMMENTS,
                    PetManagementContract.Pet.COLUMN_NAME_IMAGE_URI
            };

    public static final String SQL_DELETE_PETS = "DELETE FROM " + PetManagementContract.Pet.TABLE_NAME;


    //*****************************************************************************************************************************
    // Constructor.
    //*****************************************************************************************************************************

    public PetsDatabase(Context context)
    {
        this.context = context;
        this.initializePetCharacteristics();
        this.initializePetsDatabase();
    }


    //*****************************************************************************************************************************
    // Initializes secondary pet characteristics.
    //*****************************************************************************************************************************

    private void initializePetCharacteristics()
    {
        colours = new HashMap();
        colours.put("BLA", "Black");
        colours.put("BLU", "Blue");
        colours.put("BRO", "Brown");
        colours.put("GOL", "Golden");
        colours.put("RED", "Red");
        colours.put("GRA", "Gray");
        colours.put("WHT", "White");
        colours.put("ORG", "Orange");
        colours.put("YEL", "Yellow");
        colours.put("GRN", "Green");
        colours.put("CYA", "Cyan");

        genders = new HashMap();
        genders.put("MAL", "Male");
        genders.put("FEM", "Female");
        genders.put("UNK", "Unknown");
    }


    //*****************************************************************************************************************************
    // Inserts a new pet in the database. Pet name is obligatory.
    //*****************************************************************************************************************************

    private void insertPet(SQLiteDatabase db, String name, String dateOfBirth, String gender, String species, String breed,
                           String colour, String distinguishingMarks, String chipId, String ownerName, String ownerAddress,
                           String ownerPhone, String vetName, String vetAddress, String vetPhone, String comments, int imageUri)
    {
        ContentValues values = new ContentValues();
        if (!name.equals("")) {
            values.put(PetManagementContract.Pet.COLUMN_NAME_NAME, name);
            values.put(PetManagementContract.Pet.COLUMN_NAME_DATE_OF_BIRTH, dateOfBirth);
            values.put(PetManagementContract.Pet.COLUMN_NAME_GENDER, gender);
            values.put(PetManagementContract.Pet.COLUMN_NAME_SPECIES, species);
            values.put(PetManagementContract.Pet.COLUMN_NAME_BREED, breed);
            values.put(PetManagementContract.Pet.COLUMN_NAME_COLOUR, colour);
            values.put(PetManagementContract.Pet.COLUMN_NAME_DISTINGUISHING_MARKS, distinguishingMarks);
            values.put(PetManagementContract.Pet.COLUMN_NAME_CHIP_ID, chipId);
            values.put(PetManagementContract.Pet.COLUMN_NAME_OWNER_NAME, ownerName);
            values.put(PetManagementContract.Pet.COLUMN_NAME_OWNER_ADDRESS, ownerAddress);
            values.put(PetManagementContract.Pet.COLUMN_NAME_OWNER_PHONE, ownerPhone);
            values.put(PetManagementContract.Pet.COLUMN_NAME_VET_NAME, vetName);
            values.put(PetManagementContract.Pet.COLUMN_NAME_VET_ADDRESS, vetAddress);
            values.put(PetManagementContract.Pet.COLUMN_NAME_VET_PHONE, vetPhone);
            values.put(PetManagementContract.Pet.COLUMN_NAME_COMMENTS, comments);
            values.put(PetManagementContract.Pet.COLUMN_NAME_IMAGE_URI, imageUri);
        }

        long newRowId = db.insert(PetManagementContract.Pet.TABLE_NAME, null, values);
    }

    //*****************************************************************************************************************************
    // Creates a data structure containing all the Pets.
    // Assumes hard-coded pets. These would normally be provided by an external database.
    //*****************************************************************************************************************************

    public void initializePetsDatabase()
    {
        // Initialize database.
        dbHelper = new PetDatabaseHelper(context);

        // Prepare to write / insert pets.
        db = dbHelper.getWritableDatabase();

        // Our database is hard-coded so we do not need duplicate values. In the future, with no hard-coded pets, deleting the database will be a big no-no.
        db.execSQL(SQL_DELETE_PETS);

        // Helper for formating dates of birth.
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String comments;

        // Create hard-coded Pet1.
        comments = "Info:\t\tNuvoletta is a quiet partner, is fully vaccinated and neutered. A bit overweight. Likes warmth and freedom.";
        insertPet(db, "Nuvoletta", dateFormat.format(new Date(2015-1900, 7, 23)), genders.get("MAL").toString(),
                "Cat", "Chartreux", colours.get("GRA").toString(), "white chin, golden eyes", "CACHA3025", "Filomena Papandreou",
                "10 Avydou str, 11142, Athens, Attica, Greece", "+30 6984122577", "Leon Spyridonos", "2A Ayisandrou str, 11632, Athens, Attica, Greece", "+30 6979965017",
                comments, R.drawable.cat_chartreux_nuvoletta);

        // Create hard-coded Pet2.
        comments = "Info:\t\tToby is a newborn kitty, not yet fully vaccinated. Still needs feeding. Has five siblings and is quite playful.";
        insertPet(db, "Toby", dateFormat.format(new Date(2017-1900, 1, 4)), genders.get("MAL").toString(),
                "Cat", "British Blue", colours.get("BLU").toString(), "spiky hair", "CABRI6630", "Maria Papadaki",
                "17 Odemissiou str, 16231, Vyronas, Attica, Greece", "+30 6944865213", "Cura di Animali", "36 Kyprou str, 16232, Vyronas, Attica, Greece", "+30 6975454820",
                comments, R.drawable.cat_britishblue_toby);

        // Create hard-coded Pet3.
        comments = "Info:\t\tIsis became a mother recently, gave birth to a litter of six kitties. Her health is optimal and is fully vaccinated.";
        insertPet(db, "Isis", dateFormat.format(new Date(2014-1900, 5, 25)), genders.get("FEM").toString(),
                "Cat", "Persian", colours.get("WHT").toString(), "grey end tail", "CAPER7415", "Petros Vrettos",
                "28 Knossou str, 18451, Nikaia, Attica, Greece", "+30 6949095064", "Pavlos Exarchos", "120 Laodikeias str, 18451, Nikaia, Attica, Greece", "+30 6984454858",
                comments, R.drawable.cat_persiancat_isis);

        // Create hard-coded Pet4.
        comments = "Info:\t\tHilda is adapted to live in very cold climates. She is a good hunter and able to survive on her own. Fully vaccinated.";
        insertPet(db, "Hilda", dateFormat.format(new Date(2016-1900, 8, 10)), genders.get("FEM").toString(),
                "Cat", "Siberian", colours.get("BRO").toString() + " and " + colours.get("WHT").toString(), "black stripped paws and tail", "CASIB6980", "Marios Andreakis",
                "25 Evrota str, 14564, Nea Kifissia, Attica, Greece", "+30 6980054148", "Theodoros Papaspyridakos", "9 Piyon str, 14564, Nea Kifissia, Attica, Greece", "+30 6934454511",
                comments, R.drawable.cat_siberiancat_hilda);

        // Create hard-coded Pet5.
        comments = "Info:\t\tRudy is a fast runner and quite playful. Sociable and very gentle with children. Will be sent to training school next month.";
        insertPet(db, "Rudy", dateFormat.format(new Date(2016-1900, 11, 11)), genders.get("MAL").toString(),
                "Dog", "Beagle", colours.get("BLA").toString() + ", " + colours.get("BRO").toString() + " and " + colours.get("WHT").toString(), "birthmark behind left ear",
                "DOBEA0012", "Filippos Valentinos", "47 Solonos str, 15232, Chalandri, Attica, Greece", "+30 6975414814", "Leandros Anastassiou",
                "10 Parashou str, 15233, Chalandri, Attica, Greece", "+30 6944462158", comments, R.drawable.dog_beagle_rudy);

        // Create hard-coded Pet6.
        comments = "Info:\t\tEva has recently won a beauty pageant. She is well trained and quiet, but not very used to living outdoors.";
        insertPet(db, "Eva", dateFormat.format(new Date(2013-1900, 9, 5)), genders.get("FEM").toString(),
                "Dog", "Bichon Frisé", colours.get("WHT").toString(), "spiky head top", "DOBIC2996", "Lefteris Chronis",
                "52 Samou str, 16673, Voula, Attica, Greece", "+30 6930488404", "Aspasia Giannoudaki", "18 Parou str, 16673, Voula, Attica, Greece", "+30 6941126447",
                comments, R.drawable.dog_bichonfrise_eva);

        // Create hard-coded Pet7.
        comments = "Info:\t\tMika is a newborn puppy that is still breastfeeding. Her mother is still overprotective of her, however her health is optimal.";
        insertPet(db, "Mika", dateFormat.format(new Date(2017-1900, 3, 3)), genders.get("FEM").toString(),
                "Dog", "Cotonde Tulear", colours.get("WHT").toString(), "very furry ears", "DOCOT8741", "Anna Mela",
                "74 Miaouli str, 12242, Aigaleo, Attica, Greece", "+30 6941154664", "Aggela Zoulaki", "66 Spercheiou str, 12243, Aigaleo, Attica, Greece", "+30 6971308826",
                comments, R.drawable.dog_cotondetulear_mika);

        // Create hard-coded Pet8.
        comments = "Info:\t\tFulstaf adores to live outdoors and is a trained Sheepdog. He is loyal, quick-of-foot and can put a good fight.";
        insertPet(db, "Fulstaf", dateFormat.format(new Date(2011-1900, 4, 4)), genders.get("MAL").toString(),
                "Dog", "Icelandic Sheepdog", colours.get("BRO").toString() + " and " +  colours.get("WHT").toString(), "minor scars on back", "DOICE3633", "Agapi-Maria Drossopoulou",
                "80 Mitropoleos str, 15124, Maroussi, Attica, Greece", "+30 6986121542", "Katerina Kypraiou", "20 Salaminas str, 15124, Maroussi, Attica, Greece", "+30 6935426825",
                comments, R.drawable.dog_icelandicsheepdog_fulstaf);

        // Create hard-coded Pet9.
        comments = "Info:\t\tRorry's favourite food is pollen and nectar. He loves to bathe and hang upside down. He is not friendly to other birds, though.";
        insertPet(db, "Rorry", dateFormat.format(new Date(2007-1900, 8, 1)), genders.get("MAL").toString(),
                "Parrot", "Chattering", colours.get("RED").toString() + " and " +  colours.get("GRN").toString(), "yellow marks on wings", "PACHA0258", "Loukas Galinakis",
                "90 Dimokritou str, 13674, Acharnes, Attica, Greece", "+30 6970567245", "Pet Clinic For All", "12 Pindarou str, 13674, Acharnes, Attica, Greece", "+30 6971154778",
                comments, R.drawable.parrot_chattering_rorry);

        // Create hard-coded Pet10.
        comments = "Info:\t\tFrieda is independent and does not like interaction with people. She can be territorial at times and likes to fly around in her cage.";
        insertPet(db, "Frieda", dateFormat.format(new Date(2015-1900, 11, 23)), genders.get("FEM").toString(),
                "Canary", "Gloster Fancy", colours.get("YEL").toString() + " and " +  colours.get("GRA").toString(), "black stripes on head", "CAGLO8781", "Dafni Kaplani",
                "36 Zefyrou str, 12136, Peristeri, Attica, Greece", "+30 6934158354", "Artemis Malama", "58 Alikis str, 12135, Peristeri, Attica, Greece", "+30 6988896457",
                comments, R.drawable.canary_gloster_frieda);

        // Create hard-coded Pet11.
        comments = "Info:\t\tLeonardo and is lively and active and needs to have ample space. He lives happily in a swallow water tank. He dreams of becoming a ninja.";
        insertPet(db, "Leonardo", dateFormat.format(new Date(2007-1900, 3, 10)), genders.get("MAL").toString(),
                "Turtle", "Reeve's", colours.get("GRN").toString() + " and " +  colours.get("BLA").toString(), "star shaped yellow belly", "TUREE9114", "Natalia Markantoni",
                "74 Ippokratous str, 18756, Keratsini, Attica, Greece", "+30 6945158214", "Vaggelis Sarivaxevanis", "10 Mikras Asias str, 18756, Keratsini, Attica, Greece", "+30 6941254250",
                comments, R.drawable.turtle_reeves_leonardo);

        // Create hard-coded Pet12.
        comments = "Info:\t\tLucy enjoys caresses and grooming. She likes playing with toys such as cardboard tubes and boxes. She likes company and attention.";
        insertPet(db, "Lucy", dateFormat.format(new Date(2017-1900, 3, 1)), genders.get("FEM").toString(),
                "Bunny", "Miniature Lion Lop", colours.get("WHT").toString(), "invisible eyes", "BUMIN8714", "Faidon Archontopoulos",
                "1 Akropoleos str, 16346, Ilioupoli, Attica, Greece", "+30 6946325821", "Patra Lysandrou", "19 Argonauton str, 16346, Iliouloli, Attica, Greece", "+30 6945428815",
                comments, R.drawable.bunny_minilop_lucy);

        // Create hard-coded Pet13.
        comments = "Info:\t\tEda is very playful, smart and curious. Her favourite food are black berries, green beans and hazelnuts.";
        insertPet(db, "Eda", dateFormat.format(new Date(2011-1900, 10, 1)), genders.get("FEM").toString(),
                "Parrot", "Parrotlet", colours.get("CYA").toString() + " and " +  colours.get("GRA").toString(), "violet head top", "PAPAR2252", "Efstratia Psarri",
                "66B Kekropos str, 19001, Keratea, Attica, Greece", "+30 6976651003", "Alexis Liaskos", "8 Troias str, 19001, Keratea, Attica, Greece", "+30 6974158023",
                comments, R.drawable.parrot_parrotlet_eda);

        // Create hard-coded Pet14.
        comments = "Info:\t\tHenry is quite industrious and able to build himself quite complex silk hides. He is also very good at jumping.";
        insertPet(db, "Henry", dateFormat.format(new Date(2012-1900, 04, 10)), genders.get("MAL").toString(),
                "Spider", "Pink Toe Tarantula", colours.get("BLA").toString() + " and " +  colours.get("RED").toString(), "cyan stripes on head", "SPPIN6336", "Evdokia Perperaki",
                "44 Metsovou str, 18120, Korydallos, Attica, Greece", "+30 6935623205", "Dimitris Vrachalis", "5 Arkadiou str, 18122, Korydallos, Attica, Greece", "+30 6934786632",
                comments, R.drawable.spider_pinktoe_henry);

        // Create hard-coded Pet15.
        comments = "Info:\t\tFurball, besides very hairy, is also energetic and alert. He has overall a great personality and enjoys the company of humans.";
        insertPet(db, "Furball", dateFormat.format(new Date(2016-1900, 07, 13)), genders.get("MAL").toString(),
                "Guinea Pig", "Peruvian Guinea Pig", colours.get("BRO").toString() + " and " +  colours.get("RED").toString(), "eterochromia on eyes", "GUPER8880", "Stelios Psycharis",
                "92 Elpidos str, 11146, Galatsi, Attica, Greece", "+30 6939988102", "Pit Zacharopoulos", "2 Heras str, 11147, Galatsi, Attica, Greece", "+30 6981452369",
                comments, R.drawable.guineapig_peruvian_furball);

        // Create hard-coded Pet16.
        comments = "Info:\t\tSatine has the hairstyle of a Hollywood celebrity. Her locks of hair are very soft and shiny, making her great for kids to pet and interact with.";
        insertPet(db, "Satine", dateFormat.format(new Date(2016-1900, 07, 13)), genders.get("FEM").toString(),
                "Guinea Pig", "Silkie Guinea Pig", colours.get("BLA").toString(), "vivid shiny hair", "GUSIL8144", "Kimon Nestoriadis",
                "31 Avydou str, 15771, Zografou, Attica, Greece", "+30 6981024545", "Ahmed Fouad", "50 Chlois, 15772, Zografou, Attica, Greece", "+30 6941257854",
                comments, R.drawable.guineapig_silkie_satine);

        // Create hard-coded Pet17.
        comments = "Info:\t\tRhea has a high-domed shell and is expected to live up to 80 years. She requires fresh, clean drinking water daily and enjoys a varied diet.";
        insertPet(db, "Rhea", dateFormat.format(new Date(1998-1900, 2, 6)), genders.get("FEM").toString(),
                "Turtle", "Box Turtle", colours.get("GRN").toString() + " and " +  colours.get("YEL").toString(), "shell cracked on the left", "TUBOX2009", "Vlassis Symeonoglou",
                "87 Skoufa str, 16452, Argyroupoli, Attica, Greece", "+30 6984664805", "Markella Lani", "37 Militou str, 16451, Argyroupoli, Attica, Greece", "+30 6975214521",
                comments, R.drawable.turtle_box_rhea);

        // Create hard-coded Pet18.
        comments = "Info:\t\tPauline boasts a bright red stripe behind each eye. An aquatic turtle, she requires an aquarium with water. She likes being outdoors on warm days so she can enjoy the sunshine.";
        insertPet(db, "Pauline", dateFormat.format(new Date(2004-1900, 2, 6)), genders.get("FEM").toString(),
                "Turtle", "Red-eared slider", colours.get("GRN").toString() + " and " +  colours.get("YEL").toString() + " and " +  colours.get("RED").toString(), "very slit eyes", "TURED8877", "Margarita Bofiliou",
                "4 Asklipiou str, 16675, Glyfada, Attica, Greece", "+30 6936612546", "Irini Axelou", "110 Mykinon str, 16674, Glyfada, Attica, Greece", "+30 6934117999",
                comments, R.drawable.turtle_redeared_pauline);

        // Create hard-coded Pet19.
        comments = "Info:\t\tTweety is as popular in the neighbourhood as is his famous namesake. He song is nerve-soothing and keeps excellent company. He is not afraid of pussy cats.";
        insertPet(db, "Tweety", dateFormat.format(new Date(2014-1900, 6, 15)), genders.get("MAL").toString(),
                "Canary", "Fife", colours.get("YEL").toString(), "white feathers on wings", "CAFIF0012", "Lamprini Kylidi",
                "61 Afroditis str, 17561, Palaio Faliro, Attica, Greece", "+30 6974461069", "Giannis Fokas", "7 Alkyonis str, 17561, Palaio Faliro, Attica, Greece", "+30 6984502487",
                comments, R.drawable.canary_fife_tweety);

        // Create hard-coded Pet20.
        comments = "Info:\t\tRock is the ideal pet for hugs. He belongs to an old breed of rabbit thought to have originated from the Flemish region as early as the 16th or 17th century.";
        insertPet(db, "Rock", dateFormat.format(new Date(2014-1900, 3, 1)), genders.get("MAL").toString(),
                "Bunny", "Flemish Giant", colours.get("WHT").toString() + " and " +  colours.get("GOL").toString(), "minor scar behind ear", "BUFLE8221", "Giorgos Kleanthous",
                "94 Menelaou str, 19600, Mandra, Attica, Greece", "+30 6987772545", "Kyriakos Papadopoulos", "3A Sokratous str, 19600, Mandra, Attica, Greece", "+30 6941254333",
                comments, R.drawable.bunny_flemish_rock);
    }


    //*****************************************************************************************************************************
    // Generates a list of all species contained in the database. The list contains unique Strings, listed alphabetically.
    //*****************************************************************************************************************************

    public ArrayList<String> generateSpeciesList() {

        // Initialise a data structure to keep the various species.
        species = new ArrayList();

        // Prepare to read pets.
        try {
            db = dbHelper.getReadableDatabase();

            // Read all species of animals contained in the database query.
            String sortOrder = PetManagementContract.Pet.COLUMN_NAME_SPECIES + " ASC";

            cursor = db.query(
                    true,                                                   // Distinct
                    PetManagementContract.Pet.TABLE_NAME,                   // The table to query
                    SPECIES_PROJECTION,                                     // The columns to return
                    null,                                                   // The columns for the WHERE clause
                    null,                                                   // The values for the WHERE clause
                    null,                                                   // Don't group the rows
                    null,                                                   // Don't filter by row groups
                    sortOrder,                                              // The sort order
                    null                                                    // Limit (Select TOP value)
            );

            // Iteration. Add results to an arraylist.
            int temp = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_SPECIES);
            String species_result;

            while (cursor.moveToNext()) {
                species_result = cursor.getString(temp);
                species.add(species_result);
            }
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable.", Toast.LENGTH_SHORT);
            toast.show();
        }
        cursor.close();

        // Keep the fairy tale alive.
        species.add("Unicorn");

        // Remove duplicates by using a little trick. Transfer everything to a Set that does not allow duplicates and then back.
        //Set<String> hs = new HashSet<>();
        //hs.addAll(species);
        //species.clear();
        //species.addAll(hs);

        // Put the species in alphabetical order for aesthetic and practical reasons. (Only used to put "Unicorn" in the right order.)
        Collections.sort(species, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        return species;
    }


    //*****************************************************************************************************************************
    // Transforms a pet database object to pet java object.
    // Note: the cursor parameter must contain all information needed and should be already moved to the correct place.
    //*****************************************************************************************************************************

    public Pet transformDatabasePetToObjectPet(Cursor cursor, int position)
    {
        try {
            db = dbHelper.getReadableDatabase();
            cursor.moveToPosition(position);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(context, "Unable to find pet in database.", Toast.LENGTH_SHORT);
            toast.show();
            //cursor.close();
            return null;
        }

        int nameColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_NAME);
        String name = cursor.getString(nameColumn);

        int speciesColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_SPECIES);
        String species = cursor.getString(speciesColumn);

        int breedColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_BREED);
        String breed = cursor.getString(breedColumn);

        int chipColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_CHIP_ID);
        String chip = cursor.getString(chipColumn);

        int birthColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_DATE_OF_BIRTH);
        String dateOfBirth = cursor.getString(birthColumn);
        Date bdate = new Date(); // Dummy value.
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            bdate = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int genderColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_GENDER);
        String gender = cursor.getString(genderColumn);

        int colourColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_COLOUR);
        String colour = cursor.getString(colourColumn);

        int marksColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_DISTINGUISHING_MARKS);
        String marks = cursor.getString(marksColumn);

        int ownerColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_OWNER_NAME);
        String owner = cursor.getString(ownerColumn);

        int ownerAddrColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_OWNER_ADDRESS);
        String ownerAddr = cursor.getString(ownerAddrColumn);

        int ownerPhoneColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_OWNER_PHONE);
        String ownerPhone = cursor.getString(ownerPhoneColumn);

        int vetColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_VET_NAME);
        String vet = cursor.getString(vetColumn);

        int vetAddrColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_VET_ADDRESS);
        String vetAddr = cursor.getString(vetAddrColumn);

        int vetPhoneColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_VET_PHONE);
        String vetPhone = cursor.getString(vetPhoneColumn);

        int commentsColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_COMMENTS);
        String comments = cursor.getString(commentsColumn);

        int imageColumn = cursor.getColumnIndexOrThrow(PetManagementContract.Pet.COLUMN_NAME_IMAGE_URI);
        int image = cursor.getInt(imageColumn);

        Pet newPet = new Pet(name, species, breed, chip, bdate, gender, colour, marks, owner, ownerAddr, ownerPhone, vet, vetAddr, vetPhone, comments, image);

        //cursor.close();
        return newPet;
    }


    //*****************************************************************************************************************************
    // Returns an ArrayList of all Pets belonging to a particular species.
    //*****************************************************************************************************************************

    public Cursor generateListOfPetsBelongingToASpecies(String species) {

        try {
            db = dbHelper.getReadableDatabase();

            // Read all species of animals contained in the database query.
            String sortOrder = PetManagementContract.Pet.COLUMN_NAME_NAME + " ASC";
            String whereClause = PetManagementContract.Pet.COLUMN_NAME_SPECIES + "= ?";

            cursor = db.query(
                    PetManagementContract.Pet.TABLE_NAME,                   // The table to query
                    PETS_PROJECTION,                                        // The columns to return
                    whereClause,                                            // The columns for the WHERE clause
                    new String[]{species},                                  // The values for the WHERE clause
                    null,                                                   // Don't group the rows
                    null,                                                   // Don't filter by row groups
                    sortOrder                                               // The sort order
            );

            // Note: PetPreviewActivity destroys this cursor.
            return cursor;
        }
        catch(SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return null;
    }


    //*****************************************************************************************************************************
    // Closes the database.
    //*****************************************************************************************************************************

    public void closeConnectionToDatabase()
    {
        db.close();
    }

    //*****************************************************************************************************************************
    // Returns a particular Pet when its id number is known.
    //*****************************************************************************************************************************

    /*public Pet getPetById(String petId)
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
    }*/
}