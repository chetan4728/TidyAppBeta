package com.tidyhomz.admin.tidyappbeta.Dataset;

import org.json.JSONArray;

/**
 * Created by Admin on 7/22/2017.
 */

public class ViewCartModel {

    private  String ImageThumb;
    private  String name;
    private  String quantity;
    private  String Image;
    private  String price;
    private   int cart_id;
    private  String HSN;
    private String model;
    private  String total;
    private  int prduct_id;


    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    private JSONArray Option;

    public JSONArray getOption() {
        return Option;
    }

    public void setOption(JSONArray option) {
        Option = option;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getPrduct_id() {
        return prduct_id;
    }

    public void setPrduct_id(int prduct_id) {
        this.prduct_id = prduct_id;
    }

    public String getImageThumb() {
        return ImageThumb;
    }

    public void setImageThumb(String imageThumb) {
        ImageThumb = imageThumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
