package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 8/25/2017.
 */

public class addressCountrySpinner {

    private int country_id;

    private String Country_name;


    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }



    public String getCountry_name() {
        return Country_name;
    }

    public void setCountry_name(String country_name) {
        Country_name = country_name;
    }
    @Override
    public String toString() {
        return  Country_name;
    }

}
