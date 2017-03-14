package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class defining a pet.
 */

import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class Pet {

    private String name;
    private String petId;
    private int photo;
    private Owner owner;
    private Veterinarian vet;

    private String sex;			// {MAL, FEM, UKN}
    private Date dateOfBirth;

    private String hairColour;  // {BLA, BRO, GOL, WHT, RED, GRA};
    private String eyeColour;   // {BLA, BRO, GOL, BLU, RED, GRA, GRN};

    private Pet mother;
    private Pet father;

    private double heightInCm;
    private double weightInKilos;

    private String comments;

    private boolean isNeutered;
    private boolean isOrganDonor;
    private boolean isFullyVaccinated;

    // TODO --> Transform These to Classes if needed.
    private String typeOfAnimal;
    private String breed;




    public Pet(String name, String petId, int photo, Owner owner, Veterinarian vet, String sex,
               Date dateOfBirth, String hairColour, String eyeColour, Pet mother, Pet father, double heightInCm,
               double weightInKilos, boolean isNeutered, boolean isOrganDonor, boolean isFullyVaccinated,
               String typeOfAnimal, String breed) {
        super();
        this.name = name;
        this.petId = petId;
        this.photo = photo;
        this.owner = owner;
        this.vet = vet;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.hairColour = hairColour;
        this.eyeColour = eyeColour;
        this.mother = mother;
        this.father = father;
        this.heightInCm = heightInCm;
        this.weightInKilos = weightInKilos;
        this.isNeutered = isNeutered;
        this.isOrganDonor = isOrganDonor;
        this.isFullyVaccinated = isFullyVaccinated;
        this.typeOfAnimal = typeOfAnimal;
        this.breed = breed;


        if(vet != null)vet.setAsPatient(this); 	// Bidirectional mutable association between Pet and Veterinarian.
        if(owner != null)owner.setAsPet(this); 		// Bidirectional mutable association between Pet and Owner.
    }


    // Calculates animal's age.
    public int calculateAge()
    {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        return todayYear - birthDateYear;
    }


    // Examines whether two pet objects are equal to one another.
    public boolean equals(Pet pet)
    {
        if(this.petId.equals(pet.petId))
        {
            return true;
        }
        else return false;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPetId() {
        return petId;
    }


    public void setPetId(String petId) {
        this.petId = petId;
    }


    public int getPhoto() {
        return photo;
    }


    public void setPhoto(int photo) {
        this.photo = photo;
    }


    public Owner getOwner() {
        return owner;
    }


    public void setOwner(Owner owner) {
        this.owner = owner;
        owner.setAsPet(this);		// This line disconnects the pet to other owners, too.
    }


    public Veterinarian getVet() {
        return vet;
    }


    public void setVet(Veterinarian vet) {
        this.vet = vet;
        vet.setAsPatient(this);      // This line disconnects the pet to other vets, too.
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    public String getDateOfBirthAsString() {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy");
        String bdate = sdf.format(dateOfBirth);
        return bdate;


       /* int year = dateOfBirth.getYear();
        year -= 1900;
        dateOfBirth.setYear(year);
        String age = android.text.format.DateFormat.format("EEEE d MMMM yyyy", dateOfBirth).toString();
        return age;*/
    }


    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public String getHairColour() {
        return hairColour;
    }


    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }


    public String getEyeColour() {
        return eyeColour;
    }


    public void setEyeColour(String eyeColour) {
        this.eyeColour = eyeColour;
    }


    public Pet getMother() {
        return mother;
    }


    public void setMother(Pet mother) {
        this.mother = mother;
    }


    public Pet getFather() {
        return father;
    }


    public void setFather(Pet father) {
        this.father = father;
    }


    public double getHeightInCm() {
        return heightInCm;
    }


    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }


    public double getWeightInKilos() {
        return weightInKilos;
    }


    public void setWeightInKilos(double weightInKilos) {
        this.weightInKilos = weightInKilos;
    }


    public boolean isNeutered() {
        return isNeutered;
    }


    public void setNeutered(boolean isNeutered) {
        this.isNeutered = isNeutered;
    }


    public boolean isOrganDonor() {
        return isOrganDonor;
    }


    public void setOrganDonor(boolean isOrganDonor) {
        this.isOrganDonor = isOrganDonor;
    }


    public boolean isFullyVaccinated() {
        return isFullyVaccinated;
    }


    public void setFullyVaccinated(boolean isFullyVaccinated) {
        this.isFullyVaccinated = isFullyVaccinated;
    }


    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }


    public void setTypeOfAnimal(String typeOfAnimal) {
        this.typeOfAnimal = typeOfAnimal;
    }


    public String getBreed() {
        return breed;
    }


    public void setBreed(String breed) {
        this.breed = breed;
    }


    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }

}




