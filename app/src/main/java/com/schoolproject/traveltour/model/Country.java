package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.Map;

public class Country implements Serializable {
    private String id;
    private String name;
    private String imageBase64;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public static Country parse(Map<String, Object> map) {
        Country country = new Country();
        country.setId((String) map.get("id"));
        country.setName((String) map.get("name"));
        country.setImageBase64((String) map.get("imageBase64"));
        return country;
    }
}
