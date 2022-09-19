package com.sliwinski.exhibitions.service.validator;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.exception.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Utility class with validation methods for input data. Throws exception if input is incorrect.
 */

@Component
public class Validate {
    /**
     * Basic password validation
     * @param password Password
     * @param retypedPassword Retyped password
     * @return true if correct
     */
    public boolean password(String password, String retypedPassword) {
        if(password.length() < 8) throw new ShortPasswordException();
        if(!password.equals(retypedPassword)) throw new WrongRetypedPasswordException();
    return true;
    }

    /**
     * Exhibition start, end date validation
     * @param start Exhibition start date
     * @param end Exhibition end date
     * @return true if correct
     */
    public boolean startEndDates (LocalDate start, LocalDate end) {
        if(start == null || end == null) throw new EmptyDateException();
        if(end.compareTo(start) < 0) throw new EndBeforeStartException();
        if(end.compareTo(LocalDate.now()) < 0) throw new EndBeforeNowException();
        return true;
    }

    /**
     * Exhibition hours validation
     * @param opening Exhibition opening hour
     * @param closing Exhibition closing hour
     * @return true if correct
     */
    public boolean openingClosingHours (LocalTime opening, LocalTime closing) {
        if(opening == null || closing == null) throw new EmptyTimeException();
        if(closing.compareTo(opening) < 0) throw new ClosingBeforeOpeningException();
        return true;
    }

    /**
     * General non-empty string field validation
     * @param field String field to validate
     * @return true if not empty
     */
    public boolean isFilled(String field) {
        if(field.equals("")) throw new EmptyFieldException();
        return true;
    }

    /**
     * Exhibition creation validation
     * @param exhibition Exhibition to validate
     * @return true if correct
     */
    public boolean exhibition (Exhibition exhibition) {
        this.startEndDates(exhibition.getStartDate(), exhibition.getEndDate());
        if(exhibition.getLocations().size() == 0) throw new EmptyLocationsException();
        if(exhibition.getTheme().equals("")) throw new EmptyThemeException();
        this.openingClosingHours(exhibition.getOpeningHour(), exhibition.getClosingHour());
        if(exhibition.getTicketPrice() == null || exhibition.getTicketPrice() <= 0) throw new BadTicketPriceException();
        return true;
    }
}
