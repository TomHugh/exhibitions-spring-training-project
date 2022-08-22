package com.sliwinski.exhibitions.exception;

public class NoSuchExhibitionException extends CustomException{
    public NoSuchExhibitionException() { super("Exhibition not found");}
    @Override
    public String getMessageKey() {
        return "exhibition_not_found";
    }
}
