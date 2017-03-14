package com.angie.mypet;

/* Created by:
 * A.M. Despotopoulou 12.03.2017
 * A class defining a veterinarian.
 */


public class Veterinarian extends Person {

    private String licenceNumber;

    // Constructor that actually initialises a new Person.
    public Veterinarian(String surname, String middleName, String name, String idCard, String phoneNumber1,
                        String phoneNumber2, String email, String sex, Address address, String licenceNumber) {

        super(surname, middleName, name, idCard, phoneNumber1, phoneNumber2, email, sex, address);
        this.licenceNumber = licenceNumber;
    }

    // Records a particular animal as a patient.
    public void setAsPatient(Pet p)
    {
        // TODO This will be used to change pets' owners and vets. Remove from previous owner/vet duo.
        super.addAnimalToList(p);
        p.setVet(this);
    }


    // Removes a particular animal from the patients list.
    public void RemoveAsPatient(Pet p)
    {
        super.removeAnimalFromList(p);
        p.setVet(null);
    }


    private String getLicenceNumber()
    {
        return licenceNumber;
    }

    private void setLicenceNumber(String licenceNumber)
    {
        this.licenceNumber = licenceNumber;
    }

}
