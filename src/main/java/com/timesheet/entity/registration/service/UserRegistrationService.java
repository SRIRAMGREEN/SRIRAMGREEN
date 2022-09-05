package com.timesheet.entity.registration.service;

import com.timesheet.entity.registration.entity.User;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserRegistrationService {

    void  signUpUser(User user, String siteURL) throws MessagingException, UnsupportedEncodingException ;

    void sendRegistrationConfirmationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void getRandom();
    User verifyUser(User user);
    User  login(String loginId, String password);
    User  setPassword(User user);

}
