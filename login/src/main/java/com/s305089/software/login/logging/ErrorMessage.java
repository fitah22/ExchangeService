package com.s305089.software.login.logging;

public class ErrorMessage implements Loggable {
    private final String email;
    private final String cause;

    public ErrorMessage(String email, String cause) {
        this.email = email;
        this.cause = cause;

    }

    public String getEmail() {
        return email;
    }

    public String getCause() {
        return cause;
    }
}
