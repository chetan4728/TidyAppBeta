package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 8/3/2017.
 */

public class ProductoptionDropDownDataset {


    private  int product_option_value_id;
    private String name;
    private  int option_value_id;
    private String spinner_type;
    private String font_type;
    private String font_color;
    private String custome_required_flag;

    public String getCustome_required_flag() {
        return custome_required_flag;
    }

    public void setCustome_required_flag(String custome_required_flag) {
        this.custome_required_flag = custome_required_flag;
    }

    public String getSpinner_type() {
        return spinner_type;
    }

    public void setSpinner_type(String spinner_type) {
        this.spinner_type = spinner_type;
    }

    public String getFont_type() {
        return font_type;
    }

    public void setFont_type(String font_type) {
        this.font_type = font_type;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public int getProduct_option_value_id() {
        return product_option_value_id;
    }

    public void setProduct_option_value_id(int product_option_value_id) {
        this.product_option_value_id = product_option_value_id;
    }

    public int getOption_value_id() {
        return option_value_id;
    }

    public void setOption_value_id(int option_value_id) {
        this.option_value_id = option_value_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return  name;
    }
}
