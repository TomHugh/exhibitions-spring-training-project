package com.sliwinski.exhibitions.exception;

public class UserExistsException extends CustomException{
    public UserExistsException() { super("This username already exists");}
    @Override
    public String getMessageKey() {
        return "user_exists";
    }
}
