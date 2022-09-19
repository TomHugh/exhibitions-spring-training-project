package com.sliwinski.exhibitions.service.utility;

import org.springframework.data.domain.Sort;

/**
 * Sorting types and direction for Search.
 */

public enum SortType {DATE_ASC, DATE_DESC, THEME_ASC, THEME_DESC, TICKET_PRICE_ASC, TICKET_PRICE_DESC;
    public String field() {
        return switch(this)
        {
            case DATE_ASC, DATE_DESC: yield "startDate";
            case THEME_ASC, THEME_DESC: yield "theme";
            case TICKET_PRICE_ASC, TICKET_PRICE_DESC: yield "ticketPrice";
        };
    }

    public Sort.Direction direction() {
        return switch(this)
        {
            case DATE_ASC,THEME_ASC, TICKET_PRICE_ASC: yield Sort.Direction.ASC;
            case DATE_DESC, THEME_DESC, TICKET_PRICE_DESC: yield Sort.Direction.DESC;
        };
    }
}