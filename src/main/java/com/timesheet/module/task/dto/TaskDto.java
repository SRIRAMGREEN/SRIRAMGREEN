package com.timesheet.module.task.dto;

import lombok.Getter;
import lombok.Setter;

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

}
