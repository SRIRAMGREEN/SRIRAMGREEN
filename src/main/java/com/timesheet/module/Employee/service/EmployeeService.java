package com.timesheet.module.Employee.service;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.entity.dto.EmployeeDto;
import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.registration.entity.Registration;

import java.util.List;

public interface EmployeeService {

    EmployeeDto2 addEmployee(Employee employee);

    EmployeeDto getEmployeeData(int id);
    List<EmployeeDto> getAllEmployeeDetails();

    EmployeeDto updateEmployee(Employee employee);

    void deleteUser(int id);

    void addEntryToEmployee(Registration registration);

}
