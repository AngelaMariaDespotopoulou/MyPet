//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 10th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;


public abstract class Menu extends AppCompatActivity {

    private AppCompatActivity act;
    private LoginController cont;
    private String currentUser;


    //*****************************************************************************************************************************
    // Constructors.
    //*****************************************************************************************************************************

    public Menu()
    {
        // do nothing
    }


    //*****************************************************************************************************************************
    // Creates a new menu for an activity.
    //*****************************************************************************************************************************

    public boolean InflateMenu(android.view.Menu menu, AppCompatActivity act) {
        this.act = act;
        cont = new LoginController(act);

        MenuInflater inflater = act.getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);

        currentUser = cont.isUserAlreadyLoggedIn();
        if(currentUser != null)
        {
            MenuItem bedMenuItem = menu.findItem(R.id.navigation_login);
            bedMenuItem.setTitle("Not " + currentUser + "? Log Out");
        }

        return true;
    }


    //*****************************************************************************************************************************
    // Handles selection of menu items.
    //*****************************************************************************************************************************

    public boolean ItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_login:
                HandleLoginLogOut(item);
                return true;
            default:
                return false;
        }
    }


    //*****************************************************************************************************************************
    // Handles menu behaviour on log in / log out action.
    //*****************************************************************************************************************************

    public void  HandleLoginLogOut(MenuItem item)
    {
        android.view.Menu temp = (android.view.Menu)item.getMenuInfo();
        String menuLabel = item.getTitle().toString();

        if(menuLabel.equals("Log In")) {

            Intent loginIntent = new Intent(act, LoginActivity.class);
            startActivity(loginIntent);
        }
        else
        {
            cont.logoutUser();

            if(BrowseActivity.class.isAssignableFrom(act.getClass()))
            {
                act.finish();
                return;
            }

            currentUser = "";
            item.setTitle("Log In");
        }
    }


    /*//*****************************************************************************************************************************
    // Lets an activity know if a user has already performed log in. Note: activities to not have access to
    // LoginController objects.
    //*****************************************************************************************************************************

    public boolean isAnyUserAlreadyLoggedIn()
    {
        if(cont.isUserAlreadyLoggedIn() == null)
            return false;
        else
            return true;
    }*/


    //*****************************************************************************************************************************
    // Refreshes a menu, if needed.
    //*****************************************************************************************************************************

    public void  RefreshMenu(android.view.Menu menu)
    {
        currentUser = cont.isUserAlreadyLoggedIn();
        if(currentUser != null) {
            MenuItem bedMenuItem = menu.findItem(R.id.navigation_login);
            bedMenuItem.setTitle("Not " + currentUser + "? Log Out");
        }
        else
        {
            MenuItem bedMenuItem = menu.findItem(R.id.navigation_login);
            bedMenuItem.setTitle("Log In");
        }
    }


    //*****************************************************************************************************************************
    // Defines the label on top of every visible activity.
    //*****************************************************************************************************************************

    protected abstract int getTitleResource();
}
