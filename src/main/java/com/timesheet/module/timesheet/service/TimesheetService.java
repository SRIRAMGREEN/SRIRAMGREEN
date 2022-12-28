package com.timesheet.module.timesheet.service;

import com.timesheet.module.timesheet.dto.TimesheetDto;
import com.timesheet.module.timesheet.dto.TimesheetLogsDto;
import com.timesheet.module.timesheet.entity.Timesheet;
import com.timesheet.module.timesheet.entity.TimesheetLogs;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;


public interface TimesheetService {

    TimesheetDto getTimesheetByEmployeeId(int id);

    TimesheetDto getTimesheetById(int id);

    List<TimesheetLogsDto> updateTimesheet(List<TimesheetLogs> timesheetLogs) throws MessagingException;



    List<TimesheetDto> getTimesheetByTimesheetDate(String timesheetStartDate, String timesheetEndDate);

    List<TimesheetDto> getTimesheetByEmployeeIdAndDate(int id, String timesheetStartDate, String timesheetEndDate);

    List<TimesheetDto> getTimesheetByProjectManagerId(int id);

    String deleteTimesheet(int timesheetId);

    TimesheetDto updateTimesheetStatus(int timesheet_id, String status) throws MessagingException;


    @Transactional
    List<Timesheet> listAll();
}

