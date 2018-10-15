package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 7/18/2017.
 */

public class UserReviewModel {

    private String Userrating;
    private String Review;
    private  String reviewerName;
    private String dateon;

    public String getUserrating() {
        return Userrating;
    }

    public void setUserrating(String userrating) {
        Userrating = userrating;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getDateon() {
        return dateon;
    }

    public void setDateon(String dateon) {
        this.dateon = dateon;
    }
}
