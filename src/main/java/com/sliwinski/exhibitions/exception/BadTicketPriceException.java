package com.sliwinski.exhibitions.exception;

public class BadTicketPriceException extends CustomException{
    public BadTicketPriceException() { super("Bad ticket price");}
    @Override
    public String getMessageKey() {
        return "bad_price";
    }
}
