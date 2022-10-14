package com.timesheet.module.timesheet.service;

import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.TimesheetLogs;

import javax.mail.MessagingException;
import java.util.List;


public interface TimesheetService {

    TimesheetDto getTimesheetByEmployeeId(int id);
    TimesheetDto getTimesheetById(int id);
    List<TimesheetLogsDto> updateTimesheet(List<TimesheetLogs> timesheetLogs) throws MessagingException;
    String deleteTimesheet(int timesheetId);

    TimesheetDto updateTimesheetStatus(int timesheet_id, boolean status) throws MessagingException;
}

