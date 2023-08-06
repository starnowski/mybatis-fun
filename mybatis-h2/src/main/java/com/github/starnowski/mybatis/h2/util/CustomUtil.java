package com.github.starnowski.mybatis.h2.util;

public class CustomUtil {
    public static String esc(final String s) {
        return s.replace("'", "''");
    }
}