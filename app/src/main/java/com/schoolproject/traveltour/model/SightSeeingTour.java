package com.schoolproject.traveltour.model;

import android.util.Pair;

import java.io.Serializable;
import java.util.List;

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
}
