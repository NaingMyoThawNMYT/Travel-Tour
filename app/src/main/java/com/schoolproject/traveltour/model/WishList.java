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

    public static WishList parse(Map<String, Object> map) {
        WishList wishList = new WishList();
        wishList.setTourId((String) map.get("tourId"));
        wishList.setTourCountry((String) map.get("tourCountry"));
        wishList.setTourType((String) map.get("tourType"));
        return wishList;
    }
}
