package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 6/3/2017.
 */

public class ProductGridViewModel {


    private  String  ProductName;
    private  String  ProductImage;
    private  int     ProductId;
    private  int     Quntity;
    private  String  ProductPrice;

    public int getQuntity() {
        return Quntity;
    }

    public void setQuntity(int quntity) {
        Quntity = quntity;
    }

    private  String  ProductStock;
    private  String  ProductSpecial;
    private  String  ProductRating;
    private  String  ProductDiscount;

    public String getProductStock() {
        return ProductStock;
    }

    public void setProductStock(String productStock) {
        ProductStock = productStock;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductSpecial() {
        return ProductSpecial;
    }

    public void setProductSpecial(String productSpecial) {
        ProductSpecial = productSpecial;
    }

    public String getProductRating() {
        return ProductRating;
    }

    public void setProductRating(String productRating) {
        ProductRating = productRating;
    }

    public String getProductDiscount() {
        return ProductDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        ProductDiscount = productDiscount;
    }


}
