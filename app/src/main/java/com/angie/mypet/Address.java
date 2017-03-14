package com.angie.mypet;

/*
 * Created by:
 * A.M. Despotopoulou - 12.03.2017 - Athens, Greece
 * A class defining addresses for persons.
 */

public class Address
{

    private String roadName;
    private String number;
    private String area;
    private String municipality;
    private String adminDivision1;
    private String adminDivision2;
    private String country;


    public Address(String roadName, String number, String area, String municipality, String adminDivision1,
                   String adminDivision2, String country)
    {
        this.roadName = roadName;
        this.number = number;
        this.area = area;
        this.municipality = municipality;
        this.adminDivision1 = adminDivision1;
        this.adminDivision2 = adminDivision2;
        this.country = country;
    }



    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }



    public String getAdminDivision1() {
        return adminDivision1;
    }

    public void setAdminDivision1(String adminDivision1) {
        this.adminDivision1 = adminDivision1;
    }



    public String getAdminDivision2() {
        return adminDivision2;
    }

    public void setAdminDivision2(String adminDivision2) {
        this.adminDivision2 = adminDivision2;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String printAddressAsString() {
        // TODO
        return null;
    }
}
