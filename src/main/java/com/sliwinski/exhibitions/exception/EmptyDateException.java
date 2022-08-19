package com.sliwinski.exhibitions.exception;

public class EmptyDateException extends CustomException{
    public EmptyDateException() { super("Start and end date cannot be empty");}
    @Override
    public String getMessageKey() {
        return "empty_date";
    }
}
