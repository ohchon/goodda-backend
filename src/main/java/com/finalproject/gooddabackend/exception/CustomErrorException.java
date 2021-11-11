package com.finalproject.gooddabackend.exception;

public class CustomErrorException extends RuntimeException {
    public CustomErrorException(String msg) {
        super(msg);
    }
}
