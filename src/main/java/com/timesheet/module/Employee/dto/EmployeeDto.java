package com.timesheet.module.Employee.dto;


import com.timesheet.module.team.dto.TeamDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

    private int id;

    private String employeeName;

    private TeamDto teamDto;


}

