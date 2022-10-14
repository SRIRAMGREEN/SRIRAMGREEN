package com.timesheet.module.timesheet.controller;

import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import com.timesheet.module.timesheet.service.TimesheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
public class TimesheetController {

    @Autowired
    TimesheetService timesheetService;
    Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    @PutMapping(value = "/updateTimesheet")
//    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<List<TimesheetLogsDto>> updateTimesheet(@RequestBody List<TimesheetLogs> timesheetLogs) throws MessagingException {
        logger.info("TimesheetController || updateTimesheet || Updating the timesheet Details ");
        return new ResponseEntity<>(timesheetService.updateTimesheet(timesheetLogs), HttpStatus.OK);
    }

    @PutMapping(value = "/updateTimesheetStatus")
//    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<TimesheetDto> updateTimesheetStatus(@RequestParam int timesheet_id,@RequestParam boolean status) throws MessagingException {
        logger.info("TimesheetController || updateTimesheet || Updating the timesheet Details ");
        return new ResponseEntity<>(timesheetService.updateTimesheetStatus(timesheet_id,status), HttpStatus.OK);
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
