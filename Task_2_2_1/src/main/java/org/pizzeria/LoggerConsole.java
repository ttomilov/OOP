package org.pizzeria;

import java.io.*;
import java.util.Date;

class LoggerConsole {
    static void write(String msg){
        try {
            System.out.write((new Date().toString() + " " + msg + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}