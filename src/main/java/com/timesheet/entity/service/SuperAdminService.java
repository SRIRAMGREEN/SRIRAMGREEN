package com.timesheet.entity.service;

import com.timesheet.entity.model.SuperAdmin;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface SuperAdminService {

    SuperAdmin signUpUser(SuperAdmin superAdmin) throws MessagingException, UnsupportedEncodingException;

}
