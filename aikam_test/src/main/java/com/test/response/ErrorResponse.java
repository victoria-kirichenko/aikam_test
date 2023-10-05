package com.test.response;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(String error) {
        super(error);
    }

}
