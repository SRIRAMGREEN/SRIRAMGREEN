package com.timesheet.module.timesheet.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timesheet.module.task.entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimesheetLogsDto {

    @JsonIgnore
    private int id;

    private LocalDateTime date;

    private Long hours;

    private Task task;
}
