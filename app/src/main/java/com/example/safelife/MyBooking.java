package com.example.safelife;

public class MyBooking {
    String bookedtest;
    String dateandtime;
    String labid;
    String labname;
    String pdfurl;
    String time;
    String useremail;
    String userid;

    public MyBooking() {
    }

    public MyBooking(String bookedtest, String dateandtime, String labid, String labname, String pdfurl, String time, String useremail, String userid) {
        this.bookedtest = bookedtest;
        this.dateandtime = dateandtime;
        this.labid = labid;
        this.labname = labname;
        this.pdfurl = pdfurl;
        this.time = time;
        this.useremail = useremail;
        this.userid = userid;
    }

    public String getBookedtest() {
        return bookedtest;
    }

    public void setBookedtest(String bookedtest) {
        this.bookedtest = bookedtest;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
