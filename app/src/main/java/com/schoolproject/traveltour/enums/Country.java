package com.schoolproject.traveltour.enums;

public enum Country {
    MALDIVES("Maldives"),
    VIETNAM("Vietnam"),
    MYANMAR("Myanmar"),
    HONG_KONG("Hong Kong");

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
