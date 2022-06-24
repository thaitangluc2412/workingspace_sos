package com.cnpm.workingspace.security.response;

public class LoginResponse extends CommonResponse {
    private LoginAuthenticator data;
    public LoginResponse(String code, String message, LoginAuthenticator data) {
        super(code, message, data);
    }
    @Override
    public LoginAuthenticator getData() {
        return data;
    }

    public void setData(LoginAuthenticator data) {
        this.data = data;
    }
}
