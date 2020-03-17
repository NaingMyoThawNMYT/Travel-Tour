package com.schoolproject.traveltour.model;

import java.io.Serializable;
import java.util.List;

public class OptionalTour extends Menu implements Serializable {
    private String subTitle;
    private List<String> benefits;
    private List<TitleAndDescription> prices;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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
