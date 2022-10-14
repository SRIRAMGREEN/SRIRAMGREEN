package com.timesheet.module.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.module.Employee.dto.EmployeeDto;
import com.timesheet.module.task.dto.TaskDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class TimesheetDto {

    private int id;

    private Long totalHours;

    @JsonIgnore
    private LocalDateTime timesheetStartDate;

    @JsonIgnore
    private LocalDateTime timesheetEndDate;

    private EmployeeDto employeeDto;

    private Boolean timesheetStatus;

    @JsonIgnore
    private TaskDto taskDto;

    private List<TimesheetLogsDto> timesheetLogsListDto;

}
