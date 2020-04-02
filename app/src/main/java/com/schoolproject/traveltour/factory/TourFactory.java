package com.schoolproject.traveltour.factory;

import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;

public class TourFactory {

    public static Menu createNewTour(TourType tourType) {
        if (tourType == TourType.PACKAGE_TOUR) {
            return new PackageTour();
        }
        if (tourType == TourType.OPTIONAL_TOUR) {
            return new OptionalTour();
        }
        return new SightSeeingTour();
    }
}
