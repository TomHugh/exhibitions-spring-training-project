package com.sliwinski.exhibitions.exception;

/**
 * Provides skeleton for custom application exceptions and message for the user.
 */

public abstract class CustomException extends RuntimeException{
    protected CustomException(String message) {
        super(message);
    }
    public abstract String getMessageKey();
}