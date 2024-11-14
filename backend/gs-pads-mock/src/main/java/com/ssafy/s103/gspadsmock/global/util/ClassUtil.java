package com.ssafy.s103.gspadsmock.global.util;

import java.lang.reflect.Field;

public class ClassUtil {
    public static <T> String[] getClassField(T t, boolean toSnake) {
        String className = t.getClass().getName();

        Class targetClass = null;
        try {
            targetClass = Class.forName(className);
            Field[] fields = targetClass.getDeclaredFields();
            String[] s = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                s[i] = toSnake ? toSnakeCase(fields[i].getName()) : fields[i].getName();
            }
            return s;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toSnakeCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append("_");
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
