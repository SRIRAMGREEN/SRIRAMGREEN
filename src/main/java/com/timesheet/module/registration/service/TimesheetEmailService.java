package com.timesheet.module.registration.service;

import com.timesheet.module.registration.entity.AbstractEmailContext;

import javax.mail.MessagingException;

public interface TimesheetEmailService {
    void sendMail(AbstractEmailContext email, String token) throws MessagingException;

    void forgotMail(AbstractEmailContext email, String forgotToken) throws MessagingException;

    void sendFailedMail(AbstractEmailContext email1) throws MessagingException;
}

