package com.timesheet.module.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.projectmanager.dto.ProjectManagerDto;
import com.timesheet.module.task.dto.TaskDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class TimesheetDto {

    private int id;

    private Long totalHours;


    private String timesheetStartDate;


    private String timesheetEndDate;

    private EmployeeDto employee;

    private String timesheetStatus;

    private TaskDto task;

    private List<TimesheetLogsDto> timesheetLogsList;

    private ProjectManagerDto projectManager;

}
