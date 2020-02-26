package com.intercom.announcer.utilities;

public class StringUtility {
    public static String[] parseStrings(String raw) {
        String lines[] = raw.split("\\r?\\n");

        return lines;
    }
}
