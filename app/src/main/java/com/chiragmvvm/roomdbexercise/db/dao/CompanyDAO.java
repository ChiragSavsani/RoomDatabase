package com.chiragmvvm.roomdbexercise.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chiragmvvm.roomdbexercise.db.entity.Employee;

import java.util.List;

@Dao
public interface CompanyDAO {

    @Query("select * from company")
    public List<Employee> getAllEmployee();

    @Query("select * from company where company_id ==:companyId")
    public Employee getEmployee(long companyId);
}
