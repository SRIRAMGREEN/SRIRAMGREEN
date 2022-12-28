package com.timesheet.module.Employee.controller;

import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(value = "/getEmployeeDetails")
    public ResponseEntity<EmployeeDto> getEmployeeDetails(@RequestParam int employeeId) {
        logger.info("EmployeeController || getEmployeeDetails || getting the employee details ");
        return new ResponseEntity<>(employeeService.getEmployeeData(employeeId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllEmployeeDetails")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeeDetails() {
        logger.info("ProjectController || getAllProjectDetails || Getting the ProjectDetails Details from the project");
        return new ResponseEntity<>(employeeService.getAllEmployeeDetails(), HttpStatus.OK);
    }

    @GetMapping(value = "/getEmployeeByTeamId")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByTeamId(int teamId) {
        logger.info("EmployeeController || getAllEmployeeByTaskId || getting the employee Details ");
        return new ResponseEntity<>(employeeService.getAllEmployeeByTeamId(teamId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateEmployeeDetails")
    public ResponseEntity<EmployeeDto> updateEmployeeDetails(@RequestBody Employee employee) {
        logger.info("EmployeeController || updateEmployeeDetails || Updating the employee Info {} ", employee);
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }

    @PutMapping(value = "/updateEmployeeTeamDetails")
    public ResponseEntity<EmployeeDto> updateEmployeeTeamDetails(@RequestParam int employeeId, @RequestParam int teamID) {
        logger.info("EmployeeController || updateEmployeeDetails || Updating the employee Info {} ", employeeId);
        return new ResponseEntity<>(employeeService.updateEmployeeTeam(employeeId, teamID), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteEmployee")
    public String deleteClientDetails(@RequestParam int employeeId) {
        logger.info("EmployeeController || deleteEmployee || Deleting the employee Info {} ", employeeId);
        employeeService.deleteUser(employeeId);
        return "Employee Deleted Successfully";
    }
}