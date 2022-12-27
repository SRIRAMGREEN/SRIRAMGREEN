package com.timesheet.module.task.dto;

import com.timesheet.module.Employee.dto.EmployeeDto;

import com.timesheet.module.project.dto.ProjectDto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
public class TaskDto {

    private int taskId;

    private String taskName;

    private Date taskStartDate;

    private Date taskEndDate;

    private String taskEffort;

    private double percentageOfAllocation;

    private String taskDescription;

    private String status;

    private ProjectDto project;

    private EmployeeDto employee;

}
