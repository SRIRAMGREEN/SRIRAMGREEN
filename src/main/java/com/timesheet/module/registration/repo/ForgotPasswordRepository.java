package com.timesheet.module.registration.repo;

import com.timesheet.module.registration.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Integer> {

    ForgotPasswordToken findByForgotToken(String forgotToken);

}
