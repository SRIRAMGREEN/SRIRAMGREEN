package com.timesheet.module.timesheet.service;

import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.TimesheetLogs;

import java.util.Date;
import java.util.List;


public interface TimesheetLogsService {

//    TimesheetLogsDto addTimesheetLogs(TimesheetLogs timesheetLogs);

    List<TimesheetLogsDto> getLogsByTimesheetId(int id);

    TimesheetLogsDto updateTimesheetLogs(TimesheetLogs timesheetLogs);


    void deleteTimesheetLogs(int timesheetLogsId);

}
