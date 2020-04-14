package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
    private String id;
    private String countryId;
    private String type;
    private String title;
    private String description;
    private List<String> imagesBase64;
    private double latitude;
    private double longitude;
    private transient Country country;

    public Menu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagesBase64() {
        return imagesBase64;
    }

    public void setImagesBase64(List<String> imagesBase64) {
        this.imagesBase64 = imagesBase64;
    }

    public void addImageBase64(String imageBase64) {
        if (imagesBase64 == null) {
            imagesBase64 = new ArrayList<>();
        }

        if (imagesBase64.size() <= 4) {
            imagesBase64.add(imageBase64);
        } else {
            imagesBase64.add(3, imageBase64);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void parse(Map<String, Object> map) {
        id = (String) map.get("id");
        countryId = (String) map.get("countryId");
        title = (String) map.get("title");
        description = (String) map.get("description");
        imagesBase64 = (List<String>) map.get("imagesBase64");
        latitude = getDouble(map.get("latitude"));
        longitude = getDouble(map.get("longitude"));
        country = Country.parse(map);
    }

    List<TitleAndDescription> convertToTitleAndDescriptionList(List<HashMap<String, String>> list) {
        List<TitleAndDescription> resultList = new ArrayList<>();

        if (list == null) {
            return resultList;
        }

        for (HashMap<String, String> map : list) {
            resultList.add(new TitleAndDescription(map.get("title"), map.get("description")));
        }

        return resultList;
    }

    private double getDouble(Object obj) {
        try {
            return (double) obj;
        } catch (ClassCastException e) {
            return (long) obj;
        }
    }
}
