package com.nixdocs.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";


    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password);
        return matcher.matches();
    }
}
