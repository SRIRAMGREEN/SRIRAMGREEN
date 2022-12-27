package com.timesheet.module.timesheet.controller;

import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import com.timesheet.module.timesheet.service.TimesheetLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timesheetLogs")
public class TimesheetLogsController {

    @Autowired
    TimesheetLogsService timesheetLogsService;

    Logger logger = LoggerFactory.getLogger(TimesheetLogsController.class);

    @GetMapping(value = "/getLogsByTimesheetId")
    public ResponseEntity<List<TimesheetLogsDto>> getTimesheetLogs(@RequestParam int timesheetId) {
        logger.info("TimesheetLogsController || getTimesheetLogs || get TimesheetLogs");
        return new ResponseEntity<>(timesheetLogsService.getLogsByTimesheetId(timesheetId), HttpStatus.OK);
    }

    @PutMapping(value = "/updateTimesheetLogs")
    public ResponseEntity<TimesheetLogsDto> updateTimesheetLogs(@RequestBody TimesheetLogs timesheetLogs) {
        logger.info("TimesheetLogsController || updateTimesheetLogs || update TimesheetLog details");
        return new ResponseEntity<>(timesheetLogsService.updateTimesheetLogs(timesheetLogs), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTimesheetLogs")
    public void deleteTimesheetLogs(@RequestParam int logsId) {
        logger.info("TimesheetLogsController || deleteTimesheetLogs || delete TimesheetLog details");
        timesheetLogsService.deleteTimesheetLogs(logsId);
    }
}
