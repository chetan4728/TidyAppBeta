package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 6/3/2017.
 */

public class HomeSliderModel {

    private  String ImageUrl;
    private  String SliderType;
    private String sliderId;
    private String Link;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getSliderType() {
        return SliderType;
    }

    public void setSliderType(String sliderType) {
        SliderType = sliderType;
    }

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }


}
