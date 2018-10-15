package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 9/26/2017.
 */

public class BannerDatasetModel {

    String banner_id;
    String banner_type_id;
    String banner_type;
    String banner_position;
    String 	banner_image;
    String banner_status;
    String BannerTitle;

    public String getBannerTitle() {
        return BannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        BannerTitle = bannerTitle;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_type_id() {
        return banner_type_id;
    }

    public void setBanner_type_id(String banner_type_id) {
        this.banner_type_id = banner_type_id;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getBanner_position() {
        return banner_position;
    }

    public void setBanner_position(String banner_position) {
        this.banner_position = banner_position;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_status() {
        return banner_status;
    }

    public void setBanner_status(String banner_status) {
        this.banner_status = banner_status;
    }
}
