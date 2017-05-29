//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 29th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.angie.mypet.R;
import com.angie.mypet.authentication.LoginActivity;
import com.angie.mypet.authentication.LoginController;
import com.angie.mypet.authentication.RegisterActivity;


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

        // The message is different if the user is already authenticated.
        currentUser = cont.isUserAlreadyLoggedIn();
        if(currentUser != null)
        {
            MenuItem bedMenuItem = menu.findItem(R.id.navigation_login);
            bedMenuItem.setTitle("Not " + currentUser + "? Log Out");
        }

        // The group of buttons for Pet addition, deletion and update should be visible only in the details activity (BrowseActivity).
        String activityName = act.getLocalClassName();
        if(activityName.equals("ui.MainActivity") || activityName.equals("ui.PetPreviewActivity"))
        {
            menu.setGroupVisible(R.id.pet_detail_buttons_group, false);
        }
        else
        {
            menu.setGroupVisible(R.id.pet_detail_buttons_group, true);
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
            case R.id.delete_pet:
                HandleDelete();
                return true;
            case R.id.add_pet:
                HandleAdd();
                return true;
            case R.id.edit_pet:
                HandleEdit();
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


    //*****************************************************************************************************************************
    // Handles menu behaviour when deletion of Pet is requested.
    //*****************************************************************************************************************************

    public void  HandleDelete()
    {
        // Just a precaution.
        String activityName = act.getLocalClassName();
        if(!(activityName.equals("ui.BrowseActivity")))
        {
            return;
        }

        // This is an abstract method to be implemented in the BrowseActivity class only.
        this.deleteCurrentPet();
        return;
    }


    //*****************************************************************************************************************************
    // Handles menu behaviour when addition of a new Pet is requested.
    //*****************************************************************************************************************************

    public void HandleAdd()
    {
        Intent addPetIntent = new Intent(act, AddPetActivity.class);
        startActivity(addPetIntent);
    }


    //*****************************************************************************************************************************
    // Handles menu behaviour when update of a Pet is requested.
    //*****************************************************************************************************************************

    public void HandleEdit()
    {
        this.editCurrentPet();
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


    //*****************************************************************************************************************************
    // Deletes from the database the Pet displayed. To be implemented in the BrowseActivity class only.
    //*****************************************************************************************************************************

    protected abstract void deleteCurrentPet();


    //*****************************************************************************************************************************
    // Edits on the database the Pet displayed. To be implemented in the BrowseActivity class only.
    //*****************************************************************************************************************************

    protected abstract void editCurrentPet();
}
