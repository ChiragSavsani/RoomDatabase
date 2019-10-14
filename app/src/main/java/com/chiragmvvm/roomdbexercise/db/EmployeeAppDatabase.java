package com.chiragmvvm.roomdbexercise.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chiragmvvm.roomdbexercise.db.dao.EmployeeDAO;
import com.chiragmvvm.roomdbexercise.db.entity.Company;
import com.chiragmvvm.roomdbexercise.db.entity.Employee;

@Database(entities = {Employee.class, Company.class}, version = 1)
public abstract class EmployeeAppDatabase extends RoomDatabase {

    public abstract EmployeeDAO getEmployeeDAO();

}
