package com.timesheet.module.registration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

    private String emailId;

    private String newPassword;
}
