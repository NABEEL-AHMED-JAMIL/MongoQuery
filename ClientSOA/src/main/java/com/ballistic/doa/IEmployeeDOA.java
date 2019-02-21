package com.ballistic.doa;

import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Employee;

import java.util.List;

public interface IEmployeeDOA extends CrudDOA<Employee>{

    public static final String EMPLOYEE_ID = "employeeId";
    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";
    public static final String BIRTH_DATE = "birthDate";
    public static final String PHOTO = "photo";
    public static final String NOTES = "notes";

    public List<Employee> findAllEmployees();
    public List<Employee> fetchAllByQuery(LocalQuery query);
    public List<String> fetchAllId();
    public List<Employee> fetchAllNull() throws Exception;

}
