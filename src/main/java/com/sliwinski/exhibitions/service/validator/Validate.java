package com.sliwinski.exhibitions.service.validator;

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

    public static boolean startEndDates (LocalDate start, LocalDate end) throws Exception{
        if(end.compareTo(start) < 0) throw new RuntimeException("start_end_date");
        return true;
    }
}
