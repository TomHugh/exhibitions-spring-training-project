package com.sliwinski.exhibitions.exception;

public abstract class CustomException extends RuntimeException{
    protected CustomException(String message) {
        super(message);
    }
    public abstract String getMessageKey();
}