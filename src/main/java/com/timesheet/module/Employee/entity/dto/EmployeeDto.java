package com.timesheet.module.Employee.entity.dto;

import com.timesheet.module.utils.DateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeDto extends DateTime {

    public int id;

    public String employeeName;

    public String email;

    public String loginId;

    public String department;

    private String status;

}

