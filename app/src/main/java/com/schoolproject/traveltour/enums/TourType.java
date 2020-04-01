package com.schoolproject.traveltour.enums;

public enum TourType {
    PACKAGE_TOUR("package_tour", "Package Tour"),
    OPTIONAL_TOUR("optional_tour", "Optional Tour"),
    SIGHTSEEING_TOUR("sightseeing_tour", "Sightseeing Tour");

    private final String code;
    private final String name;

    TourType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TourType getTourTypeByCode(String code) {
        if (PACKAGE_TOUR.code.equals(code)) {
            return PACKAGE_TOUR;
        } else if (OPTIONAL_TOUR.code.equals(code)) {
            return OPTIONAL_TOUR;
        } else if (SIGHTSEEING_TOUR.code.equals(code)) {
            return SIGHTSEEING_TOUR;
        }
        return null;
    }
}
