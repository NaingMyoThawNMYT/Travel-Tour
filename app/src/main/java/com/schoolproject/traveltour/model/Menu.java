package com.schoolproject.traveltour.model;

import java.io.Serializable;

public class Menu implements Serializable {
    // TODO: 17-Mar-20 delete imageUrl later. It is just for demo.
    private String imageUrl;
    private String title;
    private String description;
    private String base64ImageStr;

    Menu() {
    }

    public Menu(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
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
}
