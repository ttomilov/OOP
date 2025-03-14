package org.pizzeria;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

class Logger {
    private Logger() {
    }

    private static OutputStream out = null;

    static synchronized void setOutputStream(OutputStream out) {
        Logger.out = out;
    }

    static synchronized void write(String msg) {
        if (out == null) {
            setOutputStream(System.out);
        }

        try {
            out.write((new Date().toString() + " " + msg + "\n").getBytes());
        } catch (IOException ignored) {
        }
    }
}