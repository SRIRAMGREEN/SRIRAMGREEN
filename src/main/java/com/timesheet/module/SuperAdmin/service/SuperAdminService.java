package com.timesheet.module.SuperAdmin.service;

import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;

import java.util.List;

public interface SuperAdminService {

    List<EmployeeDto2> getEmployeeDetails();
    List<ProjectManagerDto2> getProjectManagerDetails();
}
