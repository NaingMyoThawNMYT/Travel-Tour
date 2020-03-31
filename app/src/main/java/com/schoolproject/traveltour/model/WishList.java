package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.Map;

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

    public void parse(Map<String, Object> map) {
        tourId = (String) map.get("tourId");
        tourCountry = (String) map.get("tourCountry");
        tourType = (String) map.get("tourType");
    }
}
