//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 19th April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDatabaseHelper extends SQLiteOpenHelper {


    //*****************************************************************************************************************************
    // Variables.
    //*****************************************************************************************************************************

    // The name and version of the database.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PetManagement.db";

    // The data types available for the table columns.
    private static final String TEXT_TYPE = " TEXT";            // Any character type
    private static final String INT_TYPE = " INTEGER";          // Any integer type
    private static final String REAL_TYPE = " REAL";            // Any floating-point number
    private static final String NUMERIC_TYPE = " NUMERIC";      // Booleans, dates, and date-times
    private static final String BLOB_TYPE = " BLOB";            // Binary Large Object

    // Queries to create and delete a table (in our case the database where pets reside).
    public static final String SQL_CREATE_PETS =
            "CREATE TABLE " + PetManagementContract.Pet.TABLE_NAME + " (" +
                    PetManagementContract.Pet._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    PetManagementContract.Pet.COLUMN_NAME_NAME + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_DATE_OF_BIRTH + NUMERIC_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_SPECIES + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_GENDER + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_BREED + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_COLOUR + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_DISTINGUISHING_MARKS + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_CHIP_ID + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_NAME + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_ADDRESS + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_OWNER_PHONE + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_VET_NAME + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_VET_ADDRESS + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_VET_PHONE + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_COMMENTS + TEXT_TYPE + ", " +
                    PetManagementContract.Pet.COLUMN_NAME_IMAGE_URI + INT_TYPE +
                    " )";

    public static final String SQL_DELETE_PETS =
            "DROP TABLE IF EXISTS " + PetManagementContract.Pet.TABLE_NAME;


    //*****************************************************************************************************************************
    // Constructor that calls the constructor of the SQLiteOpenHelper superclass, and passing it the database name and version.
    //*****************************************************************************************************************************

    public PetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //*****************************************************************************************************************************
    // The method that creates the database (in our case a table where "Pets" reside).
    //*****************************************************************************************************************************

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PETS);
    }

    //*****************************************************************************************************************************
    // The upgrade policy for the database is to simply discard the data and start over.
    //*****************************************************************************************************************************

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PETS);
        onCreate(db);
    }

    //*****************************************************************************************************************************
    // The downgrade policy for the database is to simply discard the data and start over.
    //*****************************************************************************************************************************

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
