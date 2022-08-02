package com.sliwinski.exhibitions.service.validator;

public class Validate {
    public static boolean password(String password, String retypedPassword) throws Exception {
        if(password.length() < 8) throw new RuntimeException("short_password");
        if(!password.equals(retypedPassword)) throw new RuntimeException("wrong_retyped_password");

    return true;
    }
}
