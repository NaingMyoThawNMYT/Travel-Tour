package com.schoolproject.traveltour.utils;

import com.schoolproject.traveltour.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private static List<Menu> menuList;
    private static List<Menu> tourList;
    public static final String[] TOUR_LIST = new String[]{"Package Tour", "Optional Tour", "Sightseeing Tour"};

    public static List<Menu> getMenuList() {
        menuList = new ArrayList<>();
        menuList.add(new Menu("maldives", "Maldives", null));
        menuList.add(new Menu("vietnam", "Vietnam", null));
        menuList.add(new Menu("myanmar", "Myanmar", null));
        menuList.add(new Menu("hongkong", "Hong Kong", null));
        return menuList;
    }

    public static List<Menu> getTourList(){
        tourList = new ArrayList<>();
        tourList.add(new Menu("maldives", "Maldives", null));
        tourList.add(new Menu("vietnam", "Vietnam", null));
        tourList.add(new Menu("myanmar", "Myanmar", null));
        tourList.add(new Menu("hongkong", "Hong Kong", null));
        return tourList;
    }
}
