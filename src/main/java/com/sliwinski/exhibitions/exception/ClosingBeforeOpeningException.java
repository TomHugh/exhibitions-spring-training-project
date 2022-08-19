package com.sliwinski.exhibitions.exception;

public class ClosingBeforeOpeningException extends CustomException{
    public ClosingBeforeOpeningException() { super("Closing hour cannot be before opening");}
    @Override
    public String getMessageKey() {
        return "opening_closing_time";
    }
}
