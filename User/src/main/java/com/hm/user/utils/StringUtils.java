package com.hm.user.utils;

public class StringUtils {
    public static Boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }
}
