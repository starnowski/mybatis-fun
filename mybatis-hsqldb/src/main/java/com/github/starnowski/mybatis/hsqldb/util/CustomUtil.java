package com.github.starnowski.mybatis.hsqldb.util;

public class CustomUtil {
    public static String esc(final String s) {
        return s.replace("'", "''");
    }
}