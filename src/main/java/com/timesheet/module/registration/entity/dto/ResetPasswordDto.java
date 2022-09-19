package com.timesheet.module.registration.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

    private String emailId;

    private String newPassword;
}
