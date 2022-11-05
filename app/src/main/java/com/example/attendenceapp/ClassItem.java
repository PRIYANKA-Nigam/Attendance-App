package com.example.attendenceapp;

public class ClassItem {
    private long c_Id;
   private String fname,lname;

    public ClassItem(long c_Id, String fname, String lname) {
        this.c_Id = c_Id;
        this.fname = fname;
        this.lname = lname;
    }

    public ClassItem(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;

    }

    public long getC_Id() {
        return c_Id;
    }

    public void setC_Id(long c_Id) {
        this.c_Id = c_Id;
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
