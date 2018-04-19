package com.mad.android.parkingtracker.Model;

import java.sql.Struct;

public class User {
    private String car_number;
    private String city;
    private String email;
    private String lastLoginDate;
    private String lastLoginTime;
    private String name;
    private String number;

    public User(String car_number, String city, String email, String lastLoginDate, String lastLoginTime, String name, String number) {
        this.car_number = car_number;
        this.city = city;
        this.email = email;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
        this.name = name;
        this.number = number;
    }

    public User(){

    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
