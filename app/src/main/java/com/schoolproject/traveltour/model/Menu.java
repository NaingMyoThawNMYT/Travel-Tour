package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
    private String imageUrl;
    private String id;
    private String countryId;
    private String type;
    private String title;
    private String description;
    private String base64ImageStr;
    private double latitude;
    private double longitude;
    private transient Country country;

    public Menu() {
    }

    public Menu(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getBase64ImageStr() {
        return base64ImageStr;
    }

    public void setBase64ImageStr(String base64ImageStr) {
        this.base64ImageStr = base64ImageStr;
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
        base64ImageStr = (String) map.get("base64ImageStr");
        latitude = (double) map.get("latitude");
        longitude = (double) map.get("longitude");
        country = Country.parse(map);
    }

    List<TitleAndDescription> convertToTitleAndDescriptionList(List<HashMap<String, String>> list) {
        List<TitleAndDescription> resultList = new ArrayList<>();

        if (list == null) {
            return resultList;
        }

        for (HashMap<String, String> map : list) {
            resultList.add(new TitleAndDescription(map.get("title"), map.get("title")));
        }

        return resultList;
    }
}
