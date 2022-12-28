package com.timesheet.module.timesheet.controller;

import com.lowagie.text.DocumentException;
import com.timesheet.module.config.TimesheetPDFExporter;
import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.Timesheet;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import com.timesheet.module.timesheet.service.TimesheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ResponseEntity<TimesheetDto> updateTimesheetStatus(@RequestParam int timesheet_id,@RequestParam String status) throws MessagingException {
        logger.info("TimesheetController || updateTimesheet || Updating the timesheet Details ");
        return new ResponseEntity<>(timesheetService.updateTimesheetStatus(timesheet_id,status), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetByEmployeeId")
    public ResponseEntity<TimesheetDto> getTimesheetByEmployeeId(@RequestParam int id) {
        logger.info("TimesheetController || getTimesheetByEmployeeId || Getting the timesheet Details from the EmployeeId");
        return new ResponseEntity<>(timesheetService.getTimesheetByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetByTimesheetDate")
    public ResponseEntity<List<TimesheetDto>>getTimesheetByTimesheetDate (@RequestParam String timesheetStartDate, String timesheetEndDate) {
        logger.info("TimesheetController || getTaskByEmployeeId || Getting the timesheet Details from the EmployeeId");
        return new ResponseEntity<>(timesheetService.getTimesheetByTimesheetDate(timesheetStartDate,timesheetEndDate), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetByEmployeeIdAndDate")
    public ResponseEntity<List<TimesheetDto>> getTimesheetByEmployeeIdAndDate(@RequestParam int id,@RequestParam String timesheetStartDate,@RequestParam String timesheetEndDate ) {
        logger.info("TimesheetController || getTimesheetByEmployeeId and Date|| Getting the timesheet Details from the EmployeeId");
        return new ResponseEntity<>(timesheetService.getTimesheetByEmployeeIdAndDate(id,timesheetStartDate,timesheetEndDate), HttpStatus.OK);
    }

    @GetMapping(value = "/getTimesheetByProjectManagerId")
    public ResponseEntity<List<TimesheetDto>> getTimesheetByProjectManager(@RequestParam int id) {
        logger.info("TimesheetController || getTaskByEmployeeId || Getting the timesheet Details from the EmployeeId");
        return new ResponseEntity<>(timesheetService.getTimesheetByProjectManagerId(id), HttpStatus.OK);
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

    @GetMapping("/Timesheet_Download_Pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Timesheet_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Timesheet> listTimesheet = timesheetService.listAll();
        System.out.println(listTimesheet);

        TimesheetPDFExporter exporter = new TimesheetPDFExporter(listTimesheet);
        exporter.export(response);

    }
}
