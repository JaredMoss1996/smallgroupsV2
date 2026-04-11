package com.jamsoftware.smallgroups.model;

public enum Gender {
    MALE("Men"),
    FEMALE("Women"),
    BOTH("Men and Women");

    private final String stringValue;

    Gender(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
