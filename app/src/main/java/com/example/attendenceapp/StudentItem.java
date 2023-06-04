package com.example.attendenceapp;

public class StudentItem {
    private String name,status;
    private int roll;
    private long s_id;

    public StudentItem(String name, int roll, long s_id) {
        this.name = name;
        this.roll = roll;
        this.s_id = s_id;
        status="";
    }

    public StudentItem(String name, String status, int roll, long s_id) {
        this.name = name;
        this.status = status;
        this.roll = roll;
        this.s_id = s_id;
    }

    public long getS_id() {
        return s_id;
    }

    public void setS_id(long s_id) {
        this.s_id = s_id;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
