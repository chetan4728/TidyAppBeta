package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 9/25/2017.
 */

public class UserTransactionModel {

    String amount;
    String description;
    String date_added;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
