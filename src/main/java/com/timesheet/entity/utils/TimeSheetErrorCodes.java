package com.timesheet.entity.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeSheetErrorCodes {

    DATA_NOT_FOUND("FC1015", "Data not found against the email id"),
    INVALID_REQUEST("FC1040","Invalid Request"),
    DATA_NOT_SAVED("FC1011", "Unable to save data"),
    INVALID_REGISTRATION_LINK("FC1017", "Invalid Registration Link"),

    MAILID_ALREADY_PRESENT("FC1013", "Candidate Details already exists");
    private final String errorCode;
    private final String errorDesc;

}
