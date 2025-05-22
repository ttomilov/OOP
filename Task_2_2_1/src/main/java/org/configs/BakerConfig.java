package org.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BakerConfig {
    @JsonProperty
    private int numBakers;
    @JsonProperty
    private int[] speeds;

    public int getNumBakers() {
        return numBakers;
    }

    public int[] getSpeeds() {
        return speeds;
    }
}
