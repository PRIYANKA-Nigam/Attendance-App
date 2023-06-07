package com.example.attendenceapp;

import java.io.Serializable;

public class Company implements Serializable {
    public String company,years,name;

    public Company() {
    }

    public Company(String company, String years, String name) {
        this.company = company;
        this.years = years;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company(String company, String years) {
        this.company = company;
        this.years = years;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCompany() {
        return company;
    }

    public String getYears() {
        return years;
    }
}
