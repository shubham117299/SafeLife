package com.example.safelife;

public class UserLabRequest {
    String name;
    String description;
    String address;
    //String status;

    public UserLabRequest() {
    }

    public UserLabRequest(String name, String description, String address, String status) {
        this.name = name;
        this.description = description;
        this.address = address;
        //this.status = status;
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

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
}
