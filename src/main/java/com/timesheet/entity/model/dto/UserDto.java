package com.timesheet.entity.model.dto;

import com.timesheet.entity.utils.DateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDto extends DateTime {

    public int id;

    public String employeeName;

    public String email;

    public String loginId;

    public String password;

    public String department;

}
