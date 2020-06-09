package com.apsdeveloperblog.apps.ws.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = -612795116094768680L;

    public MissingRequiredFieldException(String message) {
        super(message);
    }
}
