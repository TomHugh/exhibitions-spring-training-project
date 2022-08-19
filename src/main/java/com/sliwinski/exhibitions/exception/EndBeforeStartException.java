package com.sliwinski.exhibitions.exception;

public class EndBeforeStartException extends CustomException{
    public EndBeforeStartException() { super("End date cannot be before start date");}
    @Override
    public String getMessageKey() {
        return "start_end_date";
    }
}
