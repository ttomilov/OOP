package org.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;

class SetUpper {
    @JsonProperty
    private int numBakers;
    @JsonProperty
    private int numDeliverers;
    @JsonProperty
    private int bagSize;
    @JsonProperty
    private int warehouseSize;
    @JsonProperty
    private int timeWork;
    @JsonProperty
    private int queueSize;
    @JsonProperty
    private int[] bakerSpeed;

    int getNumBakers() {
        return numBakers;
    }

    int getNumDeliverers() {
        return numDeliverers;
    }

    int getBagSize() {
        return bagSize;
    }

    int getWarehouseSize() {
        return warehouseSize;
    }

    int getTimeWork() {
        return timeWork;
    }

    int getQueueSize() {
        return queueSize;
    }

    int[] getBakerSpeed() {
        return bakerSpeed;
    }
}
