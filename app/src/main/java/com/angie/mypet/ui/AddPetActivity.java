//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 29th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.angie.mypet.R;

public class AddPetActivity extends AppCompatActivity {

    //*****************************************************************************************************************************
    // onCreate method.
    //*****************************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        View addButton = findViewById(R.id.button_add);
        addButton.setVisibility(View.VISIBLE);

        View saveButton = findViewById(R.id.button_save);
        saveButton.setVisibility(View.GONE);

        // When the "save" button is clicked, all changes are saved and the activity commits suicide.
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            // Code missing here.

            // Killing the activity.
            finish();
            }
        });
    }


}
