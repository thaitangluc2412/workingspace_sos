package com.cnpm.workingspace.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    SOMETHING_WRONG("E001", "Something wrong !"),
    SUCCESS("200", "Success !"),
    ERROR("E002", "Error !"),
    UNAUTHENTICATED("E401", "Unauthorized"),
    USERNAME_ALREADY_EXISTS("E402", "Username already exists"),
    NOT_FOUND("E404", "Not found"),
    INVALID_TOKEN("E501", "Invalid token"),
    INCORRECT_TOKEN("E502", "Incorrect token"),
    MISSING_AUTHORIZATION("E503", "Missing authorization"),
    USERNAME_OR_PASSWORD_INCORRECT("E504", "Username or password incorrect"),
    INTERNAL_SERVER_ERROR("E500", "Internal Server Error");
    public final String errorCode;
    public final String msg;
}
