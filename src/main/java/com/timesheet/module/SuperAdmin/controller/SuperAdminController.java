package com.timesheet.module.SuperAdmin.controller;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.service.EmployeeService;
import com.timesheet.module.SuperAdmin.dto.EmployeeDto2;
import com.timesheet.module.SuperAdmin.dto.ProjectManagerDto2;
import com.timesheet.module.SuperAdmin.service.SuperAdminService;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import com.timesheet.module.projectmanager.service.ProjectManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/superAdmin")
public class SuperAdminController {
    @Autowired
    SuperAdminService superAdminService;

    @Autowired
    EmployeeService employeeServices;

    @Autowired
    ProjectManagerService projectManagerServices;

    Logger logger = LoggerFactory.getLogger(SuperAdminController.class);

    @PostMapping(value = "/addEmployeeDetails", produces = {"application/json"})
    public ResponseEntity<EmployeeDto2> addEmployeeDetails(@RequestBody Employee employee) {
        logger.info("SuperAdminController || addEmployeeDetails || Adding the addEmployeeDetails ");
        return new ResponseEntity<>(employeeServices.addEmployee(employee), HttpStatus.OK);
    }
    @PostMapping(value = "/addProjectManagerDetails", produces = {"application/json"})
    public ResponseEntity<ProjectManagerDto2> addProjectManagerDetails(@RequestBody ProjectManager projectManager) {
        logger.info("SuperAdminController || addEmployeeDetails || Adding the addEmployeeDetails ");
        return new ResponseEntity<>(projectManagerServices.addProjectManager(projectManager), HttpStatus.OK);
    }
    @GetMapping(value = "/getEmployeeByAdmin", produces = { "application/json" })
    private List<EmployeeDto2>  getEmployeeByAdmin(){
        logger.info("SuperAdminController || getUserByAdmin || Getting list of details of User ");
        return superAdminService.getEmployeeDetails();
    }
    @GetMapping(value = "/getManagerByAdmin", produces = { "application/json" })
    private List<ProjectManagerDto2>  getManagerByAdmin(){
        logger.info("SuperAdminController || getUserByAdmin || Getting list of details of User ");
        return superAdminService.getProjectManagerDetails();
    }
}
