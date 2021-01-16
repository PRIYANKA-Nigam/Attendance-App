package com.example.attendenceapp;

public class ClassItem {
   private String fname,lname;

    public ClassItem(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;

    }
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
