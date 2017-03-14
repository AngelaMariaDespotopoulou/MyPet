package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class defining an owner.
 */


public class Owner extends Person {


    private String idNumber;


    // Constructor that actually initialises a new Person.
    public Owner(String surname, String middleName, String name, String idCard, String phoneNumber1,
                 String phoneNumber2, String email, String sex, Address address, String idNumber) {
        super(surname, middleName, name, idCard, phoneNumber1, phoneNumber2, email, sex, address);
        this.idNumber = idNumber;
    }

    // Records a particular animal as a personal pet.
    public void setAsPet(Pet p)
    {
        // TODO This will be used to change pets' owners and vets. Remove from previous owner/vet duo.
        super.addAnimalToList(p);
        p.setOwner(this);
    }


    // Removes a particular animal from the personal pets list.
    public void RemoveAsPet(Pet p)
    {
        super.removeAnimalFromList(p);
        p.setOwner(null);
    }


    private String getIdNumber()
    {
        return idNumber;
    }

    private void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

}

