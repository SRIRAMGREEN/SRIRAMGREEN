package com.timesheet.module.SuperAdmin.service.impl;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.repository.EmployeeRepo;
import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;
import com.timesheet.module.SuperAdmin.service.SuperAdminService;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.repository.ProjectManagerRepo;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service

public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    ProjectManagerRepo projectManagerRepo;
    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(SuperAdminServiceImpl.class);

    @Override
    public List<EmployeeDto2> getEmployeeDetails() {
        try {
            Optional<List<Employee>> employeeList = employeeRepo.findByEmployeeAddedByAdminTrue();
            List<EmployeeDto2> employeeDtoList = new ArrayList<>();
            if (!employeeList.get().isEmpty()) {
                for (Employee employee : employeeList.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    EmployeeDto2 employeeDto = modelMapper.map(employee, EmployeeDto2.class);
                    employeeDtoList.add(employeeDto);
                }
                return employeeDtoList;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "No data added by Admin");
        } catch (Exception e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), "data not retrieved");
        }
    }

    @Override
    public List<ProjectManagerDto2> getProjectManagerDetails() {
        try {
            Optional<List<ProjectManager>> projectManagerList = projectManagerRepo.findByProjectManagerAddedByAdminTrue();
            List<ProjectManagerDto2> projectManagerDto2 = new ArrayList<>();
            if (!projectManagerList.get().isEmpty()) {
                for (ProjectManager projectManager : projectManagerList.get()) {
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    ProjectManagerDto2 ProjectManagers = modelMapper.map(projectManager, ProjectManagerDto2.class);
                    projectManagerDto2.add(ProjectManagers);
                }
                return projectManagerDto2;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "No data added by Admin");
        } catch (Exception e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), "data not retrieved");
        }
    }
}
