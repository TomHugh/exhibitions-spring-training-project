package com.sliwinski.exhibitions.controller;

import org.springframework.data.domain.Sort;

public enum SortType {DATE_ASC, DATE_DESC, THEME_ASC, THEME_DESC, TICKET_PRICE_ASC, TICKET_PRICE_DESC;
    public String field() {
        switch(this)
        {
            case DATE_ASC, DATE_DESC: default: return "startDate";
            case THEME_ASC, THEME_DESC: return "theme";
            case TICKET_PRICE_ASC, TICKET_PRICE_DESC: return "ticketPrice";

        }
    }

    public Sort.Direction direction() {
        switch(this)
        {
            case DATE_ASC,THEME_ASC, TICKET_PRICE_ASC: default: return Sort.Direction.ASC;
            case DATE_DESC, THEME_DESC, TICKET_PRICE_DESC: return Sort.Direction.DESC;
        }
    };
}