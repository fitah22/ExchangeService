package com.s305089.software.history.error;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String cause;
    private Date timestamp = new Date();

    public ErrorMessage() {
    }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
