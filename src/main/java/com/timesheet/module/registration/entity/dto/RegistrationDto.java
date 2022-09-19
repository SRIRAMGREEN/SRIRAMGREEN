package com.timesheet.module.registration.entity.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegistrationDto {

    public int id;

//    public String employeeName;

    public String email;

    public String loginId;

    public String verificationCode;

    private RolesDto rolesDto;

}


