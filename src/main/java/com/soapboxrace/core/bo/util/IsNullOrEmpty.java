package com.soapboxrace.core.bo.util;

public class IsNullOrEmpty {
    public static Boolean check(String string) {
        if (string == null || string.trim().isEmpty()) { 
            return true;
        }

        return false;
    }
}
