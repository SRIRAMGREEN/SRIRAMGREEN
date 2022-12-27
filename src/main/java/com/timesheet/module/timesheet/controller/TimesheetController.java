package com.timesheet.module.timesheet.controller;

import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.entity.Timesheet;
import com.timesheet.module.timesheet.service.TimesheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class TimesheetController {

    @Autowired
    TimesheetService timesheetService;
    Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    @PutMapping(value = "/updateTimesheet")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<TimesheetDto> updateTimesheet(@RequestBody Timesheet timesheet) throws MessagingException {
        logger.info("TimesheetController || updateTimesheet || Updating the timesheet Details ");
        return new ResponseEntity<>(timesheetService.updateTimesheet(timesheet), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetByEmployeeId")
    public ResponseEntity<TimesheetDto> getTimesheetByEmployeeId(@RequestParam int id) {
        logger.info("TimesheetController || getTaskByEmployeeId || Getting the timesheet Details from the EmployeeId");
        return new ResponseEntity<>(timesheetService.getTimesheetByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetById")
    public ResponseEntity<TimesheetDto> getTimesheetById(@RequestParam int timesheetId) {
        logger.info("TimesheetController || getTaskByEmployeeId || Getting the timesheet Details ");
        return new ResponseEntity<>(timesheetService.getTimesheetById(timesheetId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTimesheetData")
    public String deleteTimesheet(@RequestParam int timesheetId) {
        logger.info("TimesheetController || deleteTimesheet || Deleting the Timesheet Details ");
        timesheetService.deleteTimesheet(timesheetId);
        return "Timesheet Deleted Successfully";
    }
}
