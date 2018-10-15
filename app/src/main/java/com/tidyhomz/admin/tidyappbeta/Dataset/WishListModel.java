package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 6/8/2017.
 */

public class WishListModel {

    private  int Product_id;
    private  String ProductName;
    private  String Image;
    private  String Price;
    private  String SpecialPrice;
    private  String Discount;


    public int getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(int product_id) {
        Product_id = product_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSpecialPrice() {
        return SpecialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        SpecialPrice = specialPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
