package com.shubh.rest.webServices.restfulwebservices.exceptions;

import java.util.Date;

public class customException {
    private Date timeStamp;
    private String message;
    private String details;

    public Date getDate() {
        return timeStamp;
    }

    public void setDate(Date date) {
        this.timeStamp = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }



    public customException(Date date, String message, String details) {
        this.timeStamp = date;
        this.message = message;
        this.details = details;
    }
}
