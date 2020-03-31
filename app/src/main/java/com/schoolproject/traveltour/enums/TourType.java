package com.schoolproject.traveltour.enums;

import com.schoolproject.traveltour.utils.Constants;

public enum TourType {
    PACKAGE_TOUR(Constants.TABLE_NAME_PACKAGE_TOUR, "Package Tour"),
    OPTIONAL_TOUR(Constants.TABLE_NAME_OPTIONAL_TOUR, "Optional Tour"),
    SIGHTSEEING_TOUR(Constants.TABLE_NAME_SIGHTSEEING_TOUR, "Sightseeing Tour");

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
