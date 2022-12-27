package com.timesheet.module.timesheet.dto;

import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.task.dto.TaskDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TimesheetDto {

    private int id;

    private Long totalHours;

    private EmployeeDto employeeDto;

    private TaskDto taskDtoList;

    private TimesheetLogsDto timesheetLogsListDto;

}
