package org.exeption;

public class InvalidFormatJson extends RuntimeException {
    public InvalidFormatJson(String message) {
        super(message);
    }
}
