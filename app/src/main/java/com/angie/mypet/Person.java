package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class defining a person (Owner or Veterinarian).
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Person {

    private String surname;
    private String middleName;
    private String name;
    private String idCard;
    protected String phoneNumber1;
    protected String phoneNumber2;
    private String email;
    private String sex;					// {MAL, FEM, UKN}
    private Address address;
    protected List<Pet> animalsList;


    // Constructor that initialises a new list of animals.
    public Person(String surname, String middleName, String name, String idCard, String phoneNumber1,
                  String phoneNumber2, String email, String sex, Address address)
    {
        this.surname = surname;
        this.middleName = middleName;
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.email = email;
        this.sex = sex;
        this.address = address;
        this.animalsList = new ArrayList<Pet>();
    }


    // Prints person's data as a String.
    public String printPersonDataAsString() {
        // TODO
        return null;
    }


    // Adds an animal to the list.
    public boolean addAnimalToList(Pet pet)
    {
        Pet temp;
        boolean found = false;

        Iterator<Pet> it = animalsList.iterator();

        while (it.hasNext())
        {
            temp = (Pet)it.next();
            if(temp.equals(pet))
            {
                found = true;
                break;
            }
        }

        if (found == false)
        {
            animalsList.add(pet);
        }

        return found;
    }


    // Removes an animal from the list.
    public void removeAnimalFromList(Pet pet)
    {
        Pet temp;

        Iterator<Pet> it = animalsList.iterator();

        while (it.hasNext())
        {
            temp = (Pet)it.next();
            if(temp.equals(pet))
            {
                animalsList.remove(pet);
                break;
            }
        }


    }



    public String getPhoneNumber1()
    {
        return this.phoneNumber1;
    }

    public void setPhoneNumber1(String phone)
    {
        this.phoneNumber1 = phone;
    }



    public String getPhoneNumber2()
    {
        return this.phoneNumber2;
    }

    public void setPhoneNumber2(String phone)
    {
        this.phoneNumber2 = phone;
    }



    private String getSurname() {
        return surname;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }



    private String getMiddleName() {
        return middleName;
    }

    private void setMiddleName(String middleName) {
        this.middleName = middleName;
    }



    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }



    private String getIdCard() {
        return idCard;
    }

    private void setIdCard(String idCard) {
        this.idCard = idCard;
    }



    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }



    private String getSex() {
        return sex;
    }

    private void setSex(String sex) {
        this.sex = sex;
    }



    private Address getAddress() {
        return address;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

}
