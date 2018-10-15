package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 6/27/2017.
 */

public class RecentlyViewdModel {

    private String ProductName;
    private int ProductId;
    private String ProductImage;
    private String ProductPriice;
    private String SpecialProductPriice;
    private String Discount;

    public String getSpecialProductPriice() {
        return SpecialProductPriice;
    }

    public void setSpecialProductPriice(String specialProductPriice) {
        SpecialProductPriice = specialProductPriice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductPriice() {
        return ProductPriice;
    }

    public void setProductPriice(String productPriice) {
        ProductPriice = productPriice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
