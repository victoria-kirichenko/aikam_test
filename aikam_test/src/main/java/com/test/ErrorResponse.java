package com.test;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(String error) {
        super(error);
    }

}
