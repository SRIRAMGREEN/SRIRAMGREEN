package com.timesheet.module.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeSheetErrorCodes {

    DATA_NOT_FOUND("TS1015", "Data not found against the email id"),
    INVALID_REQUEST("TS1040", "Invalid Request"),
    DATA_NOT_SAVED("TS1011", "Unable to save data"),
    INVALID_REGISTRATION_LINK("TS1017", "Invalid Registration Link"),
    
    MAILID_ALREADY_PRESENT("TS1016", "Candidate Details already exists"),
    EXPECTATION_FAILED("TS1018", "data not retrieved"),
    INVALID_EMAIL_ID("TS1003", "Invalid Email ID"),
    EXISTING_CANDIDATE("TS1013", "Candidate Details already exists"),

    CONTROLLER_ISSUE("TS1014", "Error occurred in the controller"),
    PARAM_NOT_PROVIDED("TS1016", "Request Param null/invalid"),
    USER_ALREADY_VERIFIED("TS1018", "User Already Verified"),
    LINK_EXPIRED("TS1019", "Verification Link Expired"),
    
    INVALID_PASSWORD("TS1022", "Invalid password"),

    NO_EMAIL("TS1023", "No email id found"),
    
    EXISTING_EMAIL("TS1026", "Email are already exists"),
    INVALID_OLD_PASSWORD("TS1028", "Old password invalid"),
    INVALID_LINK_FOR_FORGOT_PASSWORD("TS1029", "Forgot password link has been expired");

    private final String errorCode;
    private final String errorDesc;

}
