package com.timesheet.module.timesheet.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class TimesheetLogsDto {

    private int id;

    private String date;

    private Long hours;

}
