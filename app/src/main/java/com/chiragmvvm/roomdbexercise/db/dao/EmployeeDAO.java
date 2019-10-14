package com.chiragmvvm.roomdbexercise.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chiragmvvm.roomdbexercise.db.entity.Employee;

import java.util.List;

@Dao
public interface EmployeeDAO {

    //For Add record in database
    @Insert
    public long addEmployee(Employee emp);

    //For Update record in database
    @Update
    public void updateEmployee(Employee emp);

    //For Delete record in database
    @Delete
    public void deleteEmployee(Employee emp);

    //For get all record from database
    @Query("select * from employee")
    public List<Employee> getAllEmployee();

    //For get single record from database
    @Query("select * from employee where employee_id ==:employeeId")
    public Employee getEmployee(long employeeId);
}
