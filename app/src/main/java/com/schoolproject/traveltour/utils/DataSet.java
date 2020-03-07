package com.schoolproject.traveltour.utils;

import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.TitleAndDescription;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private static List<Menu> menuList;
    private static List<Menu> tourList;
    public static final String[] TOUR_LIST = new String[]{"Package Tour", "Optional Tour", "Sightseeing Tour"};
    private static PackageTour packageTour;

    public static List<Menu> getMenuList() {
        menuList = new ArrayList<>();
        menuList.add(new Menu("maldives", "Maldives", null));
        menuList.add(new Menu("vietnam", "Vietnam", null));
        menuList.add(new Menu("myanmar", "Myanmar", null));
        menuList.add(new Menu("hongkong", "Hong Kong", null));
        return menuList;
    }

    public static List<Menu> getTourList() {
        tourList = new ArrayList<>();
        tourList.add(new Menu("maldives", "Maldives", null));
        tourList.add(new Menu("vietnam", "Vietnam", null));
        tourList.add(new Menu("myanmar", "Myanmar", null));
        tourList.add(new Menu("hongkong", "Hong Kong", null));
        return tourList;
    }

    public static PackageTour getPackageTour() {
        List<TitleAndDescription> list;
        packageTour = new PackageTour();
        packageTour.setTitle("3 Days Yangon Stopover");
        packageTour.setImageUrl("myanmar");

        list = new ArrayList<>();
        list.add(new TitleAndDescription("", "3D2N from MMK 560000"));
        packageTour.setPrice(list);

        list = new ArrayList<>();
        list.add(new TitleAndDescription("Day 01: Yangon arrival", "Day 01: Yangon arrival"));
        list.add(new TitleAndDescription("Day 02: Yangon sightseeing", "Day 02: Yangon sightseeing"));
        list.add(new TitleAndDescription("Day 03: Yangon departure", "Day 03: Yangon departure"));
        packageTour.setBrief(list);

        List<String> stringList = new ArrayList<>();
        stringList.add("Include 1");
        stringList.add("Include 2");
        stringList.add("Include 3");
        packageTour.setInclude(stringList);

        stringList = new ArrayList<>();
        stringList.add("Not Include 1");
        stringList.add("Not Include 2");
        stringList.add("Not Include 3");
        packageTour.setNotInclude(stringList);

        return packageTour;
    }
}
