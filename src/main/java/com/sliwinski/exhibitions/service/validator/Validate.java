package com.sliwinski.exhibitions.service.validator;

import java.time.LocalDate;

public class Validate {
    private Validate() {
    }

    public static boolean password(String password, String retypedPassword) throws Exception {
        if(password.length() < 8) throw new RuntimeException("short_password");
        if(!password.equals(retypedPassword)) throw new RuntimeException("wrong_retyped_password");
    return true;
    }

    public static boolean startEndDates (LocalDate start, LocalDate end) throws Exception{
        if(end.compareTo(start) < 0) throw new RuntimeException("start_end_date");
        return true;
    }
}
