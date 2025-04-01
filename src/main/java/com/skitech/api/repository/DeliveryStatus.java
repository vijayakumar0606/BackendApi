package com.skitech.api.repository;

public enum DeliveryStatus {
    PENDING(0),
    IN_PROGRESS(1),
    COMPLETED(2);

    private final int value;

    DeliveryStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DeliveryStatus fromInt(int status) {
        for (DeliveryStatus ds : DeliveryStatus.values()) {
            if (ds.getValue() == status) {
                return ds;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + status);
    }
}