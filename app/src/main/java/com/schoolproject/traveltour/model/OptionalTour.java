package com.schoolproject.traveltour.model;

import java.util.List;
import java.util.Map;

public class OptionalTour extends Menu {
    private String subTitle;
    private List<String> benefits;
    private List<TitleAndDescription> prices;

    public OptionalTour() {
    }

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

    public void parse(Map<String, Object> map) {
        super.parse(map);

        subTitle = (String) map.get("subTitle");
        benefits = (List<String>) map.get("benefits");
        prices = (List<TitleAndDescription>) map.get("prices");
    }
}
