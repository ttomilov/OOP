package org.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Vector;

public class MenuConfig {
    @JsonProperty
    private Vector<String> menu;

    public Vector<String> getMenu() {
        return menu;
    }
}
