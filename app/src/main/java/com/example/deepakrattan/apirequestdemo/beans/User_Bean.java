package com.example.deepakrattan.apirequestdemo.beans;

/**
 * Created by deepak.rattan on 1/11/2017.
 */

public class User_Bean {
    String name, address, phone, imageUrl;

    public User_Bean(String name, String address, String phone, String imageUrl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
