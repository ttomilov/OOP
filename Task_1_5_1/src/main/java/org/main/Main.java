package org.main;

import org.markdown.*;

public class Main {
    public static void main(String[] args) {
        Code code = new Code.Builder()
                .setContent("System.out.println(\"Hello World\");")
                .setLanguage("java")
                .build();
        System.out.println(code.serialize());
    }
}
