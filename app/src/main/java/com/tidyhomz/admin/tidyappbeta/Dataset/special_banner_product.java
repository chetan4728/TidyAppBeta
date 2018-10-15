package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 10/5/2017.
 */

public class special_banner_product {

    int product_id;
    String Image;
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
