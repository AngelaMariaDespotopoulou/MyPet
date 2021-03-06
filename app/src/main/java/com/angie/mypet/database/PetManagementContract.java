//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.database;

import android.provider.BaseColumns;

public final class PetManagementContract {

    //*****************************************************************************************************************************
    // To prevent someone from accidentally instantiating the contract class, a private empty constructor has been created.
    //*****************************************************************************************************************************
    private PetManagementContract() {
    }


    //*****************************************************************************************************************************
    // Inner class that defines the "Pets" table contents.
    //*****************************************************************************************************************************
    public static abstract class Pet implements BaseColumns {
        public static final String TABLE_NAME = "pet";                                              //0
        public static final String COLUMN_NAME_NAME = "name";                                       //1
        public static final String COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth";                     //2
        public static final String COLUMN_NAME_GENDER = "gender";                                   //3
        public static final String COLUMN_NAME_SPECIES = "species";                                 //4
        public static final String COLUMN_NAME_BREED = "breed";                                     //5
        public static final String COLUMN_NAME_COLOUR = "colour";                                   //6
        public static final String COLUMN_NAME_DISTINGUISHING_MARKS = "distinguishing_marks";       //7
        public static final String COLUMN_NAME_CHIP_ID = "chipID";
        public static final String COLUMN_NAME_OWNER_NAME = "owner_name";
        public static final String COLUMN_NAME_OWNER_ADDRESS = "owner_address";
        public static final String COLUMN_NAME_OWNER_PHONE = "owner_phone";
        public static final String COLUMN_NAME_VET_NAME = "vet_name";
        public static final String COLUMN_NAME_VET_ADDRESS = "vet_address";
        public static final String COLUMN_NAME_VET_PHONE = "vet_phone";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_IMAGE_URI = "image_uri";
    }
}

