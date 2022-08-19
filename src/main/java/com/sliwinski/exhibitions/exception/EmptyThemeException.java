package com.sliwinski.exhibitions.exception;

public class EmptyThemeException extends CustomException{
    public EmptyThemeException() { super("Exhibition must have theme");}
    @Override
    public String getMessageKey() {
        return "empty_theme";
    }
}
