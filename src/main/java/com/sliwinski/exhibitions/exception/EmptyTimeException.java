package com.sliwinski.exhibitions.exception;

public class EmptyTimeException extends CustomException{
    public EmptyTimeException() { super("Opening and closing hours cannot be empty");}
    @Override
    public String getMessageKey() {
        return "empty_time";
    }
}
