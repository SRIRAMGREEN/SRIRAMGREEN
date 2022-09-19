package com.timesheet.module.Employee.controller;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.Employee.entity.dto.EmployeeDto;
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
        logger.info("ClientController || getClientDetails || getting the Client details ");
        return new ResponseEntity<>(employeeService.getEmployeeData(employeeId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllEmployeeDetails")
    public ResponseEntity<List<EmployeeDto>> getAllClientDetail() {
        logger.info("ClientController || getAllClientDetails || getting the client Details ");
        return new ResponseEntity<>(employeeService.getAllEmployeeDetails(), HttpStatus.OK);
    }

    @PutMapping(value = "/updateClientDetails")
    public ResponseEntity<EmployeeDto> updateClientDetails(@RequestBody Employee employee) {
        logger.info("ClientController || updateClientDetails || Updating the client Info {} ", employee);
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteClient")
    public String deleteClientDetails(@RequestParam int employeeId) {
        logger.info("ClientController || deleteClientDetails || Deleting the client Info {} ", employeeId);
        employeeService.deleteUser(employeeId);
        return "Client Deleted Successfully";
    }
}