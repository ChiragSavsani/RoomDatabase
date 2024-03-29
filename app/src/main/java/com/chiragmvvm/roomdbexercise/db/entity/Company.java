package com.chiragmvvm.roomdbexercise.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "company")
public class Company {

    @ColumnInfo(name = "company_name")
    private String name;

    @ColumnInfo(name = "company_email")
    private String email;

    @ColumnInfo(name = "company_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Ignore
    public Company() {
    }

    public Company(long id, String name, String email) {

        this.name = name;
        this.email = email;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
