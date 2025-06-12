package org.main;

public class Logger {
    synchronized static void log(String message) {
        System.out.println(message);
    }
}
