package edu.hw4.task19;

public enum ValidationError {
    WEIGHT_INVALID_VALUE("WEIGHT_INVALID_VALUE"),
    CAN_BITE("CAN_BITE"),
    TO_BIG("TO_BIG");

    private final String name;

    ValidationError(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
