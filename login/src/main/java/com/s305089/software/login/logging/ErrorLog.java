package com.s305089.software.login.logging;

public class ErrorLog implements Loggable {
    private final String cause;
    private final String email;

    public ErrorLog(String email, String cause) {
        this.email = email;
        this.cause = cause;

    }

    public String getCause() {
        return cause;
    }

    public String getEmail() {
        return email;
    }
}
