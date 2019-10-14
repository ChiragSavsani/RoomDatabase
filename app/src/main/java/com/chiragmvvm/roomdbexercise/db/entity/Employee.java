package com.chiragmvvm.roomdbexercise.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee {

    //ColumnInfo is used when you want to give different name than variable eg. name and e_name

    @ColumnInfo(name = "e_name")
    private String name;

    @ColumnInfo(name = "e_email")
    private String email;

    @ColumnInfo(name = "e_phone_no")
    private String phoneNo;

    @ColumnInfo(name = "e_country")
    private String country;

    @ColumnInfo(name = "employee_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Ignore
    public Employee() {

    }

    public Employee(long id, String name, String email, String phoneNo, String country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
