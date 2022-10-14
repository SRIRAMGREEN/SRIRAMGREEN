package com.timesheet.module.task.dto;

import com.timesheet.module.Employee.entity.Employee;
import com.timesheet.module.projectmanager.entity.ProjectManager;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class TaskDto {

    private int taskId;

    private String taskName;

    private LocalDateTime taskStartDate;

    private LocalDateTime taskEndDate;

    private String taskEffort;

    private double percentageOfAllocation;

    private String taskDescription;

    private String status;

    private Employee employee;

    private ProjectManager projectManager;
}
