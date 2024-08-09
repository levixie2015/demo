package com.xlw.demo.util.xml;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;


public class StringUtil {
    public static String string(byte[] b, Charset charset) {
        try {
            return new String(b, charset.name());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }


    public static String string(byte[] b, int offset, int len, Charset charset) {
        try {
            return new String(b, offset, len, charset.name());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }


    public static byte[] stringToBytes(String s, Charset charset) {
        try {
            return s.getBytes(charset.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }


    public static boolean isEmpty(String str) {
        return !(str != null && str.trim().length() != 0);
    }


    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}