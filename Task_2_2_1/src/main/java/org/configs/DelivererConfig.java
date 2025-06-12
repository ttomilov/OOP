package org.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DelivererConfig {
    @JsonProperty
    private int numDeliverers;
    @JsonProperty
    private int[] speeds;
    @JsonProperty
    private int bagSize;

    public int getNumDeliverers() {
        return numDeliverers;
    }

    public int[] getSpeeds() {
        return speeds;
    }

    public int getBagSize() {
        return bagSize;
    }
}
