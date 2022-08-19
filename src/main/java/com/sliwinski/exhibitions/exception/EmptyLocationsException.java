package com.sliwinski.exhibitions.exception;

public class EmptyLocationsException extends CustomException{
    public EmptyLocationsException() { super("Exhibition must have at least one location");}
    @Override
    public String getMessageKey() {
        return "empty_location";
    }
}
