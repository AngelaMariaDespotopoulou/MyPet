//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 23rd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetPreviewAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    // Default constructor
    public PetPreviewAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView = (ImageView) view.findViewById(R.id.pet_image);
        int image = cursor.getInt( cursor.getColumnIndex( PetManagementContract.Pet.COLUMN_NAME_IMAGE_URI ) );
        imageView.setImageResource(image);

        TextView nameTextView = (TextView) view.findViewById(R.id.pet_preview_name);
        String name = cursor.getString( cursor.getColumnIndex( PetManagementContract.Pet.COLUMN_NAME_NAME ) );
        nameTextView.setText(name);
        nameTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        nameTextView.setTextSize(25);

        TextView breedTextView = (TextView) view.findViewById(R.id.pet_preview_breed);
        String breed = cursor.getString( cursor.getColumnIndex( PetManagementContract.Pet.COLUMN_NAME_BREED ) );
        breedTextView.setText(breed);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.pet_card_preview, parent, false);
    }
}

/*public class PetPreviewAdapter extends BaseAdapter {

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
}*/

