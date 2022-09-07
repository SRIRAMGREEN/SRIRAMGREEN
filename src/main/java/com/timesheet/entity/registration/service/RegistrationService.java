package com.timesheet.entity.registration.service;

import com.timesheet.entity.registration.entity.dto.RegistrationDto;
import com.timesheet.entity.registration.entity.Registration;


import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface RegistrationService {

    RegistrationDto insertDetails(Registration registration, String siteURL) throws MessagingException, UnsupportedEncodingException;

    Registration sendRegistrationConfirmationEmail(Registration registration, String siteURL) throws MessagingException, UnsupportedEncodingException;

    String getRandom();
    RegistrationDto verifyUser(Registration registration);

    RegistrationDto login(String loginId, String password);

    RegistrationDto setPassword(Registration registration);

}
