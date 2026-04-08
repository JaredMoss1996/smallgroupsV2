package com.jamsoftware.smallgroups.model;

public enum Age {
    CHILDREN("CHILDREN"),
    ALL_ADULT_AGES("ALL ADULTS"),
    TWENTIES("40"),
    THIRTIES("50"),
    FORTIES("40"),
    FIFTIES("50"),
    SIXTIES("60"),
    SEVENTIES_AND_UP("70+");

    private final String stringValue;

    Age(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
