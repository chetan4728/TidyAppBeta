package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 8/25/2017.
 */

public class addressSpinner {


    private int state_id;

    private String State_name;


    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }



    public String getState_name() {
        return State_name;
    }

    public void setState_name(String state_name) {
        State_name = state_name;
    }
    @Override
    public String toString() {
        return  State_name;
    }
}
