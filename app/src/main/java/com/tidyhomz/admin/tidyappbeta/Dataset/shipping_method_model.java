package com.tidyhomz.admin.tidyappbeta.Dataset;

import org.json.JSONObject;

/**
 * Created by Admin on 9/15/2017.
 */

public class shipping_method_model {

JSONObject mmethod;
String name;
 String Code;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getMmethod() {
        return mmethod;
    }

    public void setMmethod(JSONObject mmethod) {
        this.mmethod = mmethod;
    }
}
