package com.angie.mypet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
//import android.util.Log;
import android.support.v7.widget.CardView;
import android.view.View;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent browseIntent = new Intent(this, BrowseActivity.class);

        final CardView cardViewDogs = (CardView) findViewById(R.id.card_view1);
        cardViewDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Dog");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewCats = (CardView) findViewById(R.id.card_view2);
        cardViewCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Cat");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewUnicorns = (CardView) findViewById(R.id.card_view3);
        cardViewUnicorns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Unicorn");
                startActivity(browseIntent);
            }
        });

        final CardView cardViewOthers = (CardView) findViewById(R.id.card_view4);
        cardViewOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseIntent.putExtra(BrowseActivity.EXTRA_SPECIES, "Other");
                startActivity(browseIntent);
            }
        });

    }


}