package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageTour extends Menu implements Serializable {
    private List<TitleAndDescription> price;
    private List<TitleAndDescription> brief;
    private List<String> include;
    private List<String> notInclude;
    private List<String> choice;
    private List<String> highLight;
    private List<String> roomType;
    private List<String> amenity;
    private List<String> dining;
    private List<String> activity;
    private List<String> honeyMoonBenefit;

    public List<TitleAndDescription> getPrice() {
        return price;
    }

    public void setPrice(List<TitleAndDescription> price) {
        this.price = price;
    }

    public List<TitleAndDescription> getBrief() {
        return brief;
    }

    public void setBrief(List<TitleAndDescription> brief) {
        this.brief = brief;
    }

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    public List<String> getNotInclude() {
        return notInclude;
    }

    public void setNotInclude(List<String> notInclude) {
        this.notInclude = notInclude;
    }

    public List<String> getChoice() {
        return choice;
    }

    public void setChoice(List<String> choice) {
        this.choice = choice;
    }

    public List<String> getHighLight() {
        return highLight;
    }

    public void setHighLight(List<String> highLight) {
        this.highLight = highLight;
    }

    public List<String> getRoomType() {
        return roomType;
    }

    public void setRoomType(List<String> roomType) {
        this.roomType = roomType;
    }

    public List<String> getAmenity() {
        return amenity;
    }

    public void setAmenity(List<String> amenity) {
        this.amenity = amenity;
    }

    public List<String> getDining() {
        return dining;
    }

    public void setDining(List<String> dining) {
        this.dining = dining;
    }

    public List<String> getActivity() {
        return activity;
    }

    public void setActivity(List<String> activity) {
        this.activity = activity;
    }

    public List<String> getHoneyMoonBenefit() {
        return honeyMoonBenefit;
    }

    public void setHoneyMoonBenefit(List<String> honeyMoonBenefit) {
        this.honeyMoonBenefit = honeyMoonBenefit;
    }

    public void parse(Map<String, Object> map) {
        super.parse(map);

        price = convertToTitleAndDescriptionList((List<HashMap<String, String>>) map.get("price"));
        brief = convertToTitleAndDescriptionList((List<HashMap<String, String>>) map.get("brief"));
        include = (List<String>) map.get("include");
        notInclude = (List<String>) map.get("notInclude");
        choice = (List<String>) map.get("choice");
        highLight = (List<String>) map.get("highLight");
        roomType = (List<String>) map.get("roomType");
        amenity = (List<String>) map.get("amenity");
        dining = (List<String>) map.get("dining");
        activity = (List<String>) map.get("activity");
        honeyMoonBenefit = (List<String>) map.get("honeyMoonBenefit");
    }
}
