package com.timesheet.entity.controller;

import com.timesheet.entity.model.SuperAdmin;
import com.timesheet.entity.serviceimpl.SuperAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
public class SuperAdminController {
    @Autowired
    SuperAdminServiceImpl superAdminServiceImpl;

    public SuperAdmin register(@RequestBody SuperAdmin superAdmin) throws MessagingException, UnsupportedEncodingException {
        superAdminServiceImpl.signUpUser(superAdmin);
        return superAdmin;
    }

}
