package com.example.safelife;

public class UserLabData {
    String name;
    String description;
    String address;
    String imageurl;
    String id;

    private UserLabData() {
    }

    public UserLabData(String name, String description, String address, String imageurl,String id) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.imageurl = imageurl;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

