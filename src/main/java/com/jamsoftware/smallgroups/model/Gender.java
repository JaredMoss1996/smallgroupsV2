package com.jamsoftware.smallgroups.model;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    BOTH("BOTH");

    private final String stringValue;

    Gender(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
