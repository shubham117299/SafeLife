package com.example.safelife.model;

public class UserData {
    String labName;
    String description;
    String address;
    Integer labImage;

    public UserData() {
    }

    public UserData(String labName, String description, String address, Integer image) {
        this.labName = labName;
        this.description = description;
        this.address = address;
        this.labImage = image;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
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

    public Integer getLabImage() {
        return labImage;
    }

    public void setLabImage(Integer labImage) {
        this.labImage = labImage;
    }
}
