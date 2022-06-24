package com.cnpm.workingspace.security.response;

import com.cnpm.workingspace.constants.ErrorCode;

public class ErrorResponse extends CommonResponse{
    public ErrorResponse(ErrorCode errorCode, Object data) {
        super(errorCode.errorCode, errorCode.msg, data);
    }
}
