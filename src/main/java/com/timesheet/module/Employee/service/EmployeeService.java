package com.timesheet.module.Employee.service;

import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.registration.entity.Registration;

import java.util.List;

public interface EmployeeService {

    EmployeeDto2 addEmployeeByAdmin(Employee employee);

    EmployeeDto getEmployeeData(int id);

    List<EmployeeDto> getAllEmployeeByTeamId(int teamId);

    EmployeeDto updateEmployee(Employee employee);

    EmployeeDto updateEmployeeTeam(int employeeId, int teamId);

    void deleteUser(int id);

    void addEntryToEmployee(Registration registration);

}
