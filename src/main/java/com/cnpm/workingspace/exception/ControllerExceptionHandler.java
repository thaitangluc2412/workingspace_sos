package com.cnpm.workingspace.exception;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.security.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, e.getStackTrace()), HttpStatus.OK);
    }
}
