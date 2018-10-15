package com.tidyhomz.admin.tidyappbeta.Dataset;

/**
 * Created by Admin on 9/27/2017.
 */

public class NotificationModel {

    int notification_id;
    String notification_title;
    String notification_desc;
    String notification_image;
    String notification_status;
    String notification_type;
    String notification_type_id;
    String Notificatin_data;
    String Notificatin_type_name;
    int notification_flag;

    public String getNotificatin_type_name() {
        return Notificatin_type_name;
    }

    public void setNotificatin_type_name(String notificatin_type_name) {
        Notificatin_type_name = notificatin_type_name;
    }

    public int getNotification_flag() {
        return notification_flag;
    }

    public void setNotification_flag(int notification_flag) {
        this.notification_flag = notification_flag;
    }

    public String getNotificatin_data() {
        return Notificatin_data;
    }

    public void setNotificatin_data(String notificatin_data) {
        Notificatin_data = notificatin_data;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_desc() {
        return notification_desc;
    }

    public void setNotification_desc(String notification_desc) {
        this.notification_desc = notification_desc;
    }

    public String getNotification_image() {
        return notification_image;
    }

    public void setNotification_image(String notification_image) {
        this.notification_image = notification_image;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getNotification_type_id() {
        return notification_type_id;
    }

    public void setNotification_type_id(String notification_type_id) {
        this.notification_type_id = notification_type_id;
    }
}
