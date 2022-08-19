package com.sliwinski.exhibitions.exception;

public class EmptyFieldException extends CustomException{
    public EmptyFieldException() { super("This field cannot be empty");}
    @Override
    public String getMessageKey() {
        return "empty_field";
    }
}
