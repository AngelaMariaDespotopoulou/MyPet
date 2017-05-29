//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 29th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.angie.mypet.R;

import java.util.HashMap;

public class EditPetActivity extends AppCompatActivity {

    HashMap<String, String> hashMap;
    HashMap<String, String> hashMapUpdateContents;


    //*****************************************************************************************************************************
    // onCreate method.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Receiving current pet data through an intent. A HashMap, which is Serializable is being used.
        Intent intent = getIntent();
        this.hashMap = (HashMap<String, String>)intent.getSerializableExtra("map");

        // Preparing screen.
        setTitle(R.string.pet_details);

        populateScreen();

        View addButton = findViewById(R.id.button_add);
        addButton.setVisibility(View.GONE);

        View saveButton = findViewById(R.id.button_save);
        saveButton.setVisibility(View.VISIBLE);

        // When the "save" button is clicked, all changes are saved and the activity commits suicide.
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                // Reading all fields after changes.
                hashMapUpdateContents = readScreenFieldContents();

                // Locating and updating current pet.
                String currentPetId = PetSpeciesListFragment.petsDatabase.returnCurrentPetId();
                PetSpeciesListFragment.petsDatabase.updatePet(currentPetId, hashMapUpdateContents);

                // Killing the activity. Its parent is smart enough to refresh itself.
                finish();
            }
        });
    }


    //*****************************************************************************************************************************
    // Initialises screen fields with original data from intent.
    //*****************************************************************************************************************************

    private void populateScreen()
    {
        TextView name = (TextView) findViewById(R.id.pet_info_name);
        name.setText(hashMap.get("Name").toString());

        TextView chip = (TextView) findViewById(R.id.pet_chip_id);
        chip.setText(hashMap.get("ChipId").toString());

        TextView anim = (TextView) findViewById(R.id.pet_info_animal);
        anim.setText(hashMap.get("Species").toString());

        TextView breed = (TextView) findViewById(R.id.pet_info_breed);
        breed.setText(hashMap.get("Breed").toString());

        TextView gender = (TextView) findViewById(R.id.pet_info_gender);
        gender.setText(hashMap.get("Gender").toString());

        TextView colour = (TextView) findViewById(R.id.pet_info_colour);
        colour.setText(hashMap.get("Colour").toString());

        TextView marks = (TextView) findViewById(R.id.pet_info_marks);
        marks.setText(hashMap.get("Marks").toString());

        TextView owner = (TextView) findViewById(R.id.pet_info_owner_name);
        owner.setText(hashMap.get("Owner").toString());

        TextView ownerAdd = (TextView) findViewById(R.id.pet_info_owner_address);
        ownerAdd.setText(hashMap.get("OwnerAdd").toString());

        TextView ownerPhone = (TextView) findViewById(R.id.pet_info_owner_phone);
        ownerPhone.setText(hashMap.get("OwnerPhone").toString());

        TextView vet = (TextView) findViewById(R.id.pet_info_vet_name);
        vet.setText(hashMap.get("Vet").toString());

        TextView vetAdd = (TextView) findViewById(R.id.pet_info_vet_address);
        vetAdd.setText(hashMap.get("VetAdd").toString());

        TextView vetPhone = (TextView) findViewById(R.id.pet_info_vet_phone);
        vetPhone.setText(hashMap.get("VetPhone").toString());

        TextView comm = (TextView) findViewById(R.id.pet_info_comments);
        comm.setText(hashMap.get("Comments").toString());
    }


    //*****************************************************************************************************************************
    // Reads screen fields after changes in order to prepare the update.
    //*****************************************************************************************************************************

    private HashMap<String, String> readScreenFieldContents()
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        TextView name = (TextView) findViewById(R.id.pet_info_name);
        String sname = name.getText().toString();
        hashMap.put("Name", sname);

        TextView chip = (TextView) findViewById(R.id.pet_chip_id);
        String schip = chip.getText().toString();
        hashMap.put("ChipId", schip);

        TextView anim = (TextView) findViewById(R.id.pet_info_animal);
        String sanim = anim.getText().toString();
        hashMap.put("Species", sanim);

        TextView breed = (TextView) findViewById(R.id.pet_info_breed);
        String sbreed = breed.getText().toString();
        hashMap.put("Breed", sbreed);

        TextView gender = (TextView) findViewById(R.id.pet_info_gender);
        String sgender = gender.getText().toString();
        hashMap.put("Gender", sgender);

        TextView colour = (TextView) findViewById(R.id.pet_info_colour);
        String scolour = colour.getText().toString();
        hashMap.put("Colour", scolour);

        TextView marks = (TextView) findViewById(R.id.pet_info_marks);
        String smarks = marks.getText().toString();
        hashMap.put("Marks", smarks);

        TextView owner = (TextView) findViewById(R.id.pet_info_owner_name);
        String sowner = owner.getText().toString();
        hashMap.put("Owner", sowner);

        TextView ownerAdd = (TextView) findViewById(R.id.pet_info_owner_address);
        String sownerAdd = ownerAdd.getText().toString();
        hashMap.put("OwnerAdd", sownerAdd);

        TextView ownerPhone = (TextView) findViewById(R.id.pet_info_owner_phone);
        String sownerPhone = ownerPhone.getText().toString();
        hashMap.put("OwnerPhone", sownerPhone);

        TextView vet = (TextView) findViewById(R.id.pet_info_vet_name);
        String svet = vet.getText().toString();
        hashMap.put("Vet", svet);

        TextView vetAdd = (TextView) findViewById(R.id.pet_info_vet_address);
        String svetAdd = vetAdd.getText().toString();
        hashMap.put("VetAdd", svetAdd);

        TextView vetPhone = (TextView) findViewById(R.id.pet_info_vet_phone);
        String svetPhone = vetPhone.getText().toString();
        hashMap.put("VetPhone", svetPhone);

        TextView comm = (TextView) findViewById(R.id.pet_info_comments);
        String scomm = comm.getText().toString();
        hashMap.put("Comments", scomm);

        return hashMap;
    }

}
