package com.timesheet.module.task.entity.dto;

import com.timesheet.module.project.dto.ProjectDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskDto {

    public int taskId;

    public String taskName;

    public String taskStartDate;

    public String taskEffort;

    public String percentageOfAllocation;

    public ProjectDto projectDto;

}
