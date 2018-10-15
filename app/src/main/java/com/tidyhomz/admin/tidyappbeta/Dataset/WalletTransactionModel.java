package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 10/23/2017.
 */

public class WalletTransactionModel {

    int refferal_id;
    int after_refral_amount;
    String Desc_detail;
    int status;
    String title;
    String date;
    String RefeByName;

    public String getRefeByName() {
        return RefeByName;
    }

    public void setRefeByName(String refeByName) {
        RefeByName = refeByName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    int customer_id;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRefferal_id() {
        return refferal_id;
    }

    public void setRefferal_id(int refferal_id) {
        this.refferal_id = refferal_id;
    }

    public int getAfter_refral_amount() {
        return after_refral_amount;
    }

    public void setAfter_refral_amount(int after_refral_amount) {
        this.after_refral_amount = after_refral_amount;
    }

    public String getDesc_detail() {
        return Desc_detail;
    }

    public void setDesc_detail(String desc_detail) {
        Desc_detail = desc_detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
