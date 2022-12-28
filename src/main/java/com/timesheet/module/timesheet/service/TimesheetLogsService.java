package com.timesheet.module.timesheet.service;

import com.timesheet.module.timesheet.dto.TimesheetLogsDto;

import com.timesheet.module.timesheet.entity.TimesheetLogs;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TimesheetLogsService {

//    TimesheetLogsDto addTimesheetLogs(TimesheetLogs timesheetLogs);

   List<TimesheetLogsDto> getLogsByTimesheetId(int id);

    TimesheetLogsDto updateTimesheetLogs(TimesheetLogs timesheetLogs);

    String deleteTimesheetLogs(int timesheetLogsId);

}
