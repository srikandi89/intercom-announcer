package com.intercom.announcer.testutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader {
    public static String getStringFromFile(ClassLoader classLoader) throws IOException {
        InputStream is = classLoader.getResourceAsStream("customers.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp+"\n");
        }

        return sb.toString();
    }
}
