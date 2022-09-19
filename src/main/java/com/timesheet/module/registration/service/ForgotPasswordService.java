package com.timesheet.module.registration.service;

import com.timesheet.module.registration.entity.ForgotPasswordToken;
import org.springframework.stereotype.Service;

@Service
public interface ForgotPasswordService {

    ForgotPasswordToken createForgotPasswordToken();

    void saveToken(ForgotPasswordToken forgotToken);

    ForgotPasswordToken findByForgotToken(String forgotToken);

    void removeForgotToken(ForgotPasswordToken forgotToken);
}
