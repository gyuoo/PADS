package com.ssafy.s103.gspadsmock.global.util;

import java.lang.reflect.Field;

public class ClassUtil {
    public static <T> String[] getClassField(T t) {
        String className = t.getClass().getName();

        Class targetClass = null;
        try {
            targetClass = Class.forName(className);
            Field[] fields = targetClass.getDeclaredFields();
            String[] s = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                s[i] = fields[i].getName();
            }
            return s;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
