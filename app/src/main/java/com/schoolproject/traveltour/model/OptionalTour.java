package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.List;

public class OptionalTour extends Menu implements Serializable {
    private String titleNote;
    private List<String> benefits;
    private List<TitleAndDescription> prices;

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<TitleAndDescription> getPrices() {
        return prices;
    }

    public void setPrices(List<TitleAndDescription> prices) {
        this.prices = prices;
    }
}
