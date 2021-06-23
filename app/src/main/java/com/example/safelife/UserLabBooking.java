package com.example.safelife;

public class UserLabBooking {

    private String labTest;
    private String dateAndTime;
    private String userEmail;
    private String labId;
    private String userId;
    private String labName;
    private String timing;

    public UserLabBooking() {
    }

    public UserLabBooking(String labTest, String dateAndTime, String userEmail, String labId, String userId, String labName, String timing) {
        this.labTest = labTest;
        this.dateAndTime = dateAndTime;
        this.userEmail = userEmail;
        this.labId = labId;
        this.userId = userId;
        this.labName = labName;
        this.timing = timing;
    }

    public String getLabTest() {
        return labTest;
    }

    public void setLabTest(String labTest) {
        this.labTest = labTest;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }
}
