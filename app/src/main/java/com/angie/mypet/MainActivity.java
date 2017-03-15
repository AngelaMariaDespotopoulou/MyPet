package com.angie.mypet;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;


public class MainActivity extends Activity
{
    Controller c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = new Controller(this);
        c.InitializeApp();

        if (savedInstanceState != null) {
            c.restorePreviousState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        c.saveState(savedInstanceState);
    }

    // "Next"  Button Clicked.
    public void onClickFindNextPet(View view) {
        if(c != null)
        {
            c.fetchNextPet();
        }
    }

    // "Previous" Button Clicked.
    public void onClickFindPreviousPet(View view) {
        if(c != null)
        {
            c.fetchPreviousPet();
        }
    }
}