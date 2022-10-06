package com.timesheet.module.registration.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegistrationDto {

    private int id;

    private String employeeName;

    private String email;

    private String loginId;

    private String verificationCode;

    private RolesDto rolesDto;

}


