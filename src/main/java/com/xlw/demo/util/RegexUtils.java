package com.xlw.demo.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    private RegexUtils() {
    }

    public static boolean match(String pattern, String str) {
        if (StringUtils.isEmpty(pattern)) {
            return false;
        } else {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.matches();
        }
    }
}