package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SightSeeingTour extends Menu implements Serializable {
    private List<String> itinerary;
    private List<String> noteList;
    private List<TitleAndDescription> price;
    private List<String> include;
    private List<String> exclude;
    private List<String> thingsToNote;

    public List<String> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<String> itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<String> noteList) {
        this.noteList = noteList;
    }

    public List<TitleAndDescription> getPrice() {
        return price;
    }

    public void setPrice(List<TitleAndDescription> price) {
        this.price = price;
    }

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }

    public List<String> getThingsToNote() {
        return thingsToNote;
    }

    public void setThingsToNote(List<String> thingsToNote) {
        this.thingsToNote = thingsToNote;
    }

    public void parse(Map<String, Object> map) {
        super.parse(map);

        itinerary = (List<String>) map.get("itinerary");
        noteList = (List<String>) map.get("noteList");
        price = convertToTitleAndDescriptionList((List<HashMap<String, String>>) map.get("price"));
        include = (List<String>) map.get("include");
        exclude = (List<String>) map.get("exclude");
        thingsToNote = (List<String>) map.get("thingsToNote");
    }
}
