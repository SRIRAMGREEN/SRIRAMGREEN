package com.timesheet.module.registration.service;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.entity.dto.ChangePasswordDto;

import java.util.List;
import java.util.Map;

public interface TimesheetRegistrationService {

    Registration getLoginDetails(Registration registration);

    Boolean insertDetails(Registration registration);

    Registration verifyRegistration(Registration registration);

//    void sendMailForFailedEmployeesRegistration(Map<Integer, List<String>> failedMap);

    Registration verifyRegistrationForProjectManagerAndEmployee(String token);


    boolean changePassword(ChangePasswordDto changePasswordDto);

    boolean forgotPassword(String emailId);

    boolean verifyForgottenPassword(ChangePasswordDto changePasswordDto);


}
