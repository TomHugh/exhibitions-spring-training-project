package com.sliwinski.exhibitions.service.validator;

import com.sliwinski.exhibitions.exception.EndBeforeNowException;
import com.sliwinski.exhibitions.exception.EndBeforeStartException;
import com.sliwinski.exhibitions.exception.ShortPasswordException;
import com.sliwinski.exhibitions.exception.WrongRetypedPasswordException;

import java.time.LocalDate;

public class Validate {
    private Validate() {
    }

    public static boolean password(String password, String retypedPassword) {
        if(password.length() < 8) throw new ShortPasswordException();
        if(!password.equals(retypedPassword)) throw new WrongRetypedPasswordException();
    return true;
    }

    public static boolean startEndDates (LocalDate start, LocalDate end) {
        if(end.compareTo(start) < 0) throw new EndBeforeStartException();
        if(end.compareTo(LocalDate.now()) < 0) throw new EndBeforeNowException();
        return true;
    }
}
