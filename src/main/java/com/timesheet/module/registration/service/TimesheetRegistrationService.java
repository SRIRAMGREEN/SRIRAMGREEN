package com.timesheet.module.registration.service;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.dto.ChangePasswordDto;

public interface TimesheetRegistrationService {

    Registration getLoginDetails(Registration registration);

    Registration insertDetails(Registration registration);

    Registration verifyRegistration(Registration registration);

    Registration verifyRegistrationForProjectManagerAndEmployee(String token);

    boolean changePassword(ChangePasswordDto changePasswordDto);

    boolean forgotPassword(String emailId);

    boolean verifyForgottenPassword(ChangePasswordDto changePasswordDto);


}
