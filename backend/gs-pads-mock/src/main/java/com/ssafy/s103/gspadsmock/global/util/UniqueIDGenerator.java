package com.ssafy.s103.gspadsmock.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UniqueIDGenerator {
    public static String generateUniqueId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        int randomValue = new Random().nextInt(10000);
        return timestamp + String.format("%04d", randomValue);
    }
}
