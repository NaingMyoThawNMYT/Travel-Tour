package com.schoolproject.traveltour.model;

import java.io.Serializable;

public class WishList implements Serializable {
    private String tourId;
    private String tourCountry;
    private String tourType;

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourCountry() {
        return tourCountry;
    }

    public void setTourCountry(String tourCountry) {
        this.tourCountry = tourCountry;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }
}
