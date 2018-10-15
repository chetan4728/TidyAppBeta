package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 8/24/2017.
 */

public class AddressDatasetModel {

    private int Address_id;
    private int Customer_id;
    private String firstname;
    private String lastname;
    private String telephone;
    private String company;
    private String address_1;
    private String address_2;
    private String city;
    private String postcode;
    private String country_id;
    private String zone_id;
    private String custom_field;
    private String Country;
    private String State;

    public int getDefault_yes() {
        return Default_yes;
    }

    public void setDefault_yes(int default_yes) {
        Default_yes = default_yes;
    }

    private int Default_yes;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getAddress_id() {
        return Address_id;
    }

    public void setAddress_id(int address_id) {
        Address_id = address_id;
    }

    public int getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(int customer_id) {
        Customer_id = customer_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public String getCustom_field() {
        return custom_field;
    }

    public void setCustom_field(String custom_field) {
        this.custom_field = custom_field;
    }
}
