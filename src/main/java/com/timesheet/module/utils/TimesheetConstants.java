package com.timesheet.module.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TimesheetConstants {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z]+)*@qbrainx.com$";
    //    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@qbrainx.com$"
//    public static final String MAILID_ALREADY_PRESENT = "User is already Registered";

    public static final String VERIFICATION_SUCCESS = "Verification Successful";

    public static final String INVALID_EMAIL = "EmailID is not valid";

    public static final String IMAGE_UPDATE_FAILED = "Unable to find Image";

 // Regex to check valid image file extension.
    public static final String IMAGE_REGEX = ("[^\\s]+(.*?)\\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$");
    public static final String SAVING_USER = "Saving User Info in Repository";

    public static final String USER_SAVED = "User Successfully Registered";

    public static final String MAIL_SEND = "Mail send successfully";
    public static final String VERIFY_EMPLOYEE = "/verifyEmployee";
    public static final String VERIFY_PROJECTMANAGER = "/verifyProjectManager";

    public static final String MAILID_ALREADY_PRESENT = "User is already Registered";
    public static final String MAIL_NOT_SENT = "Mail not sent";
}
