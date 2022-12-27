package com.timesheet.module.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeSheetErrorCodes {

    EXISTING_EMAIL("TS1001", "Email are already exists"),
    INVALID_EMAIL_ID("TS1002", "Invalid Email ID"),
    INVALID_REGISTRATION_LINK("TS1003", "Invalid Registration Link"),
    USER_ALREADY_VERIFIED("TS1004", "User Already Verified"),
    INVALID_LINK_FOR_FORGOT_PASSWORD("TS1005", "Forgot password link has been expired"),
    LINK_EXPIRED("TS1006", "Verification Link Expired"),

    NO_EMAIL("TS1007", "No email id found"),
    INVALID_OLD_PASSWORD("TS1008", "Old password invalid"),

    DATA_NOT_FOUND("TS1009", "Data not found against the email id"),

    INVALID_REQUEST("TS1010", "Invalid Request"),

    DATA_NOT_SAVED("TS1011", "Unable to save data"),

    MAILID_ALREADY_PRESENT("TS1012", "Candidate Details already exists"),
    EXPECTATION_FAILED("TS1013", "data not retrieved"),
    EXISTING_CANDIDATE("TS1014", "Candidate Details already exists"),
    PARAM_NOT_PROVIDED("TS1015", "Request Param null/invalid"),
    INVALID_PASSWORD("TS1016", "Invalid password"),
    INVALID_TIMESHEET_ID("TS1017","Timesheet id not found / Id not found"),
    INVALID_EMPLOYEE_ID("TS1018","Employee id not found / Id not found"),
    INVALID_PROJECT_MANAGER_ID("TS1019","ProjectManager id not found / Id not found"),
    STATUS_UPDATE_FAILED("TS1020","Failed to Update Timesheet Status "),
    INVALID_TIMESHEET_LOGS_ID("TS1021","Timesheet logs id not found / Id not found"),
    INVALID_TASK_ID("TS1022","Task id not found / Id not found"),
    UPDATE_FAILED("TS1023","Failed to update data"),
    INVALID_PROJECT_ID("TS1024","Project id not found / Id not found"),
    INVALID_CLIENT_ID("TS1025","Client id not found / Id not found"),
    IMAGE_UPDATE_FAILED("TS1026","Image update failed"),
    INVALID_TEAM_ID("TS1027","Team id not found / Id not found");

    private final String errorCode;

    private final String errorDesc;
    
}
