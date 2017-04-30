//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 23rd April 2017.
//*****************************************************************************************************************************

package com.angie.mypet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Pet {

    private String name;
    private String species;
    private String breed;
    private String chipId;
    private Date dateOfBirth;
    private String gender;
    private String colour;
    private String distinguishingMarks;
    private String ownerName;
    private String ownerAddress;
    private String ownerPhone;
    private String vetName;
    private String vetAddress;
    private String vetPhone;
    private String comments;
    private int photo;


    //*****************************************************************************************************************************
    // Constructor.
    //*****************************************************************************************************************************

    public Pet(String name, String species, String breed, String chipId, Date dateOfBirth, String gender, String colour,
               String distinguishingMarks, String ownerName, String ownerAddress, String ownerPhone, String vetName,
               String vetAddress, String vetPhone, String comments, int photo) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.chipId = chipId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.colour = colour;
        this.distinguishingMarks = distinguishingMarks;
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerPhone = ownerPhone;
        this.vetName = vetName;
        this.vetAddress = vetAddress;
        this.vetPhone = vetPhone;
        this.comments = comments;
        this.photo = photo;
    }


    //*****************************************************************************************************************************
    // Calculates animal's age.
    //*****************************************************************************************************************************

    public int calculateAge()
    {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        return todayYear - birthDateYear;
    }


    //*****************************************************************************************************************************
    // Examines whether two pet objects are equal to one another.
    // The only thing we can actually check are chip ids.
    //*****************************************************************************************************************************

    public boolean equals(Pet pet)
    {
        if(this.chipId.equals(pet.chipId) && pet.chipId != null && this.chipId != null)
        {
            return true;
        }
        else return false;
    }


    //*****************************************************************************************************************************
    // Getters and setters.
    //*****************************************************************************************************************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthAsString() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy");
        String bdate = sdf.format(dateOfBirth);
        return bdate;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDistinguishingMarks() {
        return distinguishingMarks;
    }

    public void setDistinguishingMarks(String distinguishingMarks) {
        this.distinguishingMarks = distinguishingMarks;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getVetAddress() {
        return vetAddress;
    }

    public void setVetAddress(String vetAddress) {
        this.vetAddress = vetAddress;
    }

    public String getVetPhone() {
        return vetPhone;
    }

    public void setVetPhone(String vetPhone) {
        this.vetPhone = vetPhone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}




