package org.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PizzeriaConfig {
    @JsonProperty
    private int ordersQueueSize;
    @JsonProperty
    private int warehouseSize;
    @JsonProperty
    private int workTime;

    public int getOrdersQueueSize() {
        return ordersQueueSize;
    }

    public int getWarehouseSize() {
        return warehouseSize;
    }

    public int getWorkTime() {
        return workTime;
    }
}
