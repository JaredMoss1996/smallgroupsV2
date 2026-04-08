package com.jamsoftware.smallgroups.model;

public enum Frequency {
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY"),
    VARIES("VARIES");

    private final String stringValue;

    Frequency(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
