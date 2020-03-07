package com.schoolproject.traveltour.utils;

import com.schoolproject.traveltour.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private static List<Menu> menuList;

    public static List<Menu> getMenuList() {
        menuList = new ArrayList<>();
        menuList.add(new Menu("maldives", "Maldives", null));
        menuList.add(new Menu("vietnam", "Vietnam", null));
        menuList.add(new Menu("myanmar", "Myanmar", null));
        menuList.add(new Menu("hongkong", "Hong Kong", null));
        return menuList;
    }
}
