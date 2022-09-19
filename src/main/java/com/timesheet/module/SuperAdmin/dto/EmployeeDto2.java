package com.timesheet.module.SuperAdmin.dto;

import com.timesheet.module.utils.DateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmployeeDto2 extends DateTime {

    public int id;

    public String employeeName;

    public String email;

    public String loginId;

    public String department;

    private String status;

}

