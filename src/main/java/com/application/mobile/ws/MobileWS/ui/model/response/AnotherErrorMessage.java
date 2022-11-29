package com.application.mobile.ws.MobileWS.ui.model.response;

import java.util.Date;

public class AnotherErrorMessage {

    private Date timeStamp;
    private String message;

    public AnotherErrorMessage(Date timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
