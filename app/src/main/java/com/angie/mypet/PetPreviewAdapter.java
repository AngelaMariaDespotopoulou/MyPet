//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 2nd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PetPreviewAdapter extends BaseAdapter {

    private Context context;
    private List<Pet> pets;

    public PetPreviewAdapter(Context context, List<Pet> pets) {
        this.context = context;
        this.pets = pets;
    }

    @Override
    public int getCount() {
        return this.pets.size();
    }

    @Override
    public Object getItem(int position) {
        return this.pets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.pet_card_preview, parent, false);
        }

        Pet pet = (Pet) getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.pet_image);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.pet_preview_name);
        TextView breedTextView = (TextView) convertView.findViewById(R.id.pet_preview_breed);

        imageView.setImageResource(pet.getPhoto());

        nameTextView.setText(pet.getName());
        nameTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        nameTextView.setTextSize(25);

        breedTextView.setText(pet.getBreed());

        return convertView;
    }
}

