package com.flaviopessini.springbootapigateway.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private Date timestamp;
    @Getter @Setter
    private String message;
    @Getter @Setter
    private String details;

    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
