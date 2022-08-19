package com.sliwinski.exhibitions.exception;

public class WrongRetypedPasswordException extends CustomException{
    public WrongRetypedPasswordException() { super("Retype password correctly");}
    @Override
    public String getMessageKey() {
        return "wrong_retyped_password";
    }
}
