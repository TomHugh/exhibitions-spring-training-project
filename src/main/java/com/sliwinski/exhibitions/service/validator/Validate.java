package com.sliwinski.exhibitions.service.validator;

import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.exception.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class Validate {
    public boolean password(String password, String retypedPassword) {
        if(password.length() < 8) throw new ShortPasswordException();
        if(!password.equals(retypedPassword)) throw new WrongRetypedPasswordException();
    return true;
    }

    public boolean startEndDates (LocalDate start, LocalDate end) {
        if(start == null || end == null) throw new EmptyDateException();
        if(end.compareTo(start) < 0) throw new EndBeforeStartException();
        if(end.compareTo(LocalDate.now()) < 0) throw new EndBeforeNowException();
        return true;
    }

    public boolean openingClosingHours (LocalTime opening, LocalTime closing) {
        if(opening == null || closing == null) throw new EmptyTimeException();
        if(closing.compareTo(opening) < 0) throw new ClosingBeforeOpeningException();
        return true;
    }

    public boolean isFilled(String field) {
        if(field.equals("")) throw new EmptyFieldException();
        return true;
    }

    public boolean exhibition (Exhibition exhibition) {
        this.startEndDates(exhibition.getStartDate(), exhibition.getEndDate());
        if(exhibition.getLocations().size() == 0) throw new EmptyLocationsException();
        if(exhibition.getTheme().equals("")) throw new EmptyThemeException();
        this.openingClosingHours(exhibition.getOpeningHour(), exhibition.getClosingHour());
        if(exhibition.getTicketPrice() == null || exhibition.getTicketPrice() <= 0) throw new BadTicketPriceException();
        return true;
    }
}
