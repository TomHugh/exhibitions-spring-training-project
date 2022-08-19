package com.sliwinski.exhibitions.exception;

public class EndBeforeNowException extends CustomException{
    public EndBeforeNowException() { super("End date cannot be at past");}
    @Override
    public String getMessageKey() {
        return "past_end_date";
    }
}
