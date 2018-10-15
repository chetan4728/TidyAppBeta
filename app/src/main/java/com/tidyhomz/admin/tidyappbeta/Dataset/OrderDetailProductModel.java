package com.tidyhomz.admin.tidyappbeta.Dataset;

import org.json.JSONArray;

/**
 * Created by Admin on 9/6/2017.
 */

public class OrderDetailProductModel {


    private int Product_id;
    private  String name;
    private  String model;
    private  String Order_id;
    private JSONArray option;
    private  String quantity;
    private  String price;
    private  String total;
    private  String order_product_id;

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(String order_product_id) {
        this.order_product_id = order_product_id;
    }

    public int getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(int product_id) {
        Product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public JSONArray getOption() {
        return option;
    }

    public void setOption(JSONArray option) {
        this.option = option;
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
