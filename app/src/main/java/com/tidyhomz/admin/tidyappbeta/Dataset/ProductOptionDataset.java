package com.tidyhomz.admin.tidyappbeta.Dataset;

import org.json.JSONArray;

/**
 * Created by Admin on 8/3/2017.
 */

public class ProductOptionDataset {

    private int product_option_id;
    private String name;
    private String type;
    private int required;
    private JSONArray product_option_value;
    private String customerequired;
    private String custome_required_flag;
    private String parent_node_flag;
    private String parenttitle;

    public String getParenttitle() {
        return parenttitle;
    }

    public void setParenttitle(String parenttitle) {
        this.parenttitle = parenttitle;
    }

    public String getParent_node_flag() {
        return parent_node_flag;
    }

    public void setParent_node_flag(String parent_node_flag) {
        this.parent_node_flag = parent_node_flag;
    }

    public String getCustome_required_flag() {
        return custome_required_flag;
    }

    public void setCustome_required_flag(String custome_required_flag) {
        this.custome_required_flag = custome_required_flag;
    }

    public int getProduct_option_id() {
        return product_option_id;
    }

    public void setProduct_option_id(int product_option_id) {
        this.product_option_id = product_option_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public JSONArray getProduct_option_value() {
        return product_option_value;
    }

    public void setProduct_option_value(JSONArray product_option_value) {
        this.product_option_value = product_option_value;
    }

    public String getCustomerequired() {
        return customerequired;
    }

    public void setCustomerequired(String customerequired) {
        this.customerequired = customerequired;
    }
}
