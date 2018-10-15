package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 6/16/2017.
 */

public class RelatedProductModel {

    private int Product_id;
    private  String ProductName;
    private  String ProductImage;
    private  String Header;

    public int getProduct_id() {
        return Product_id;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public void setProduct_id(int product_id) {
        Product_id = product_id;
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
}
