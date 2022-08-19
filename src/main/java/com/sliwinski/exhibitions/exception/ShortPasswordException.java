package com.sliwinski.exhibitions.exception;

public class ShortPasswordException extends CustomException{
    public ShortPasswordException() { super("Password should have at least 8 characters");}
    @Override
    public String getMessageKey() {
        return "short_password";
    }
}
