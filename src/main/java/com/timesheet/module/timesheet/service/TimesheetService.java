package com.timesheet.module.timesheet.service;

import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.entity.Timesheet;

import javax.mail.MessagingException;


public interface TimesheetService {

    TimesheetDto getTimesheetByEmployeeId(int id);
    TimesheetDto getTimesheetById(int id);
    TimesheetDto updateTimesheet(Timesheet timesheet) throws MessagingException;
    String deleteTimesheet(int timesheetId);

}
