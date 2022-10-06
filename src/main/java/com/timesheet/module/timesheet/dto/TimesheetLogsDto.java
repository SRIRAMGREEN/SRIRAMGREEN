package com.timesheet.module.timesheet.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimesheetLogsDto {

    private int id;

    private LocalDateTime date;

    private Long hours;

    private TimesheetDto timesheetDto;
}
