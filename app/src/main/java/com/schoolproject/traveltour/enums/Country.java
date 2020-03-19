package com.schoolproject.traveltour.enums;

public enum Country {
    MALDIVES("maldives", "Maldives"),
    VIETNAM("vietnam", "Vietnam"),
    MYANMAR("myanmar", "Myanmar"),
    HONG_KONG("hong_kong", "Hong Kong");

    private final String code;
    private final String name;

    Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
