package com.timesheet.entity.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimesheetConstants {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z]+)*@qbrainx.com$";
    //    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@qbrainx.com$"
    public static final String MAILID_ALREADY_PRESENT = "User is already Registered";

    public static final String VERIFICATION_SUCCESS = "Verification Successful";

    public static final String INVALID_EMAIL = "EmailID is not valid";


}
