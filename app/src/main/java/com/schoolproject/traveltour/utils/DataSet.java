package com.schoolproject.traveltour.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.model.TitleAndDescription;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public static final String[] TOUR_LIST = new String[]{"Package Tour", "Optional Tour", "Sightseeing Tour"};

    public static List<Menu> getMenuList() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("maldives", "Maldives", null));
        menuList.add(new Menu("vietnam", "Vietnam", null));
        menuList.add(new Menu("myanmar", "Myanmar", null));
        menuList.add(new Menu("hongkong", "Hong Kong", null));
        return menuList;
    }

    public static List<Menu> getTourList() {
        List<Menu> tourList = new ArrayList<>();
        tourList.add(new Menu("maldives", "Maldives", null));
        tourList.add(new Menu("vietnam", "Vietnam", null));
        tourList.add(new Menu("myanmar", "Myanmar", null));
        tourList.add(new Menu("hongkong", "Hong Kong", null));
        return tourList;
    }

    public static PackageTour getPackageTour() {
        List<TitleAndDescription> list;
        PackageTour packageTour = new PackageTour();
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

    public static OptionalTour getOptionalTour() {
        OptionalTour optionalTour = new OptionalTour();
        optionalTour.setTitle("Trekking in Pindaya");
        optionalTour.setSubTitle("Leave your footprints in Myanmar and take memories back!");
        optionalTour.setImageUrl("vietnam");
        optionalTour.setDescription("The easy way is to set the size programatically like that :" +
                "graphView.setLayoutParams(new LayoutParams(width, height));" +
                "This is fine if you know the exact size of the view. However, if you want a more flexible approach, you can override the onMeasure() method to measure the view more precisely depending on the space available and layout constraints (wrap_content, match_parent, or a fixed size)." +
                "You can find an example on how to override onMeasure() by looking at the android docs and the LabelView sample in your SDK directory.");

        List<String> stringList = new ArrayList<>();
        stringList.add("Benefit 1");
        stringList.add("Benefit 2");
        stringList.add("Benefit 3");
        optionalTour.setBenefits(stringList);

        List<TitleAndDescription> list = new ArrayList<>();
        list.add(new TitleAndDescription("Price of Tour", "One person for MMK 380000(All included)"));
        optionalTour.setPrices(list);

        return optionalTour;
    }

    public static SightSeeingTour getSightSeeingTour() {
        SightSeeingTour sightSeeingTour = new SightSeeingTour();
        sightSeeingTour.setTitle("Sightseeing Tour- Dream World");
        sightSeeingTour.setImageUrl("maldives");
        sightSeeingTour.setDescription("The easy way is to set the size programatically like that :" +
                "graphView.setLayoutParams(new LayoutParams(width, height));" +
                "This is fine if you know the exact size of the view. However, if you want a more flexible approach, you can override the onMeasure() method to measure the view more precisely depending on the space available and layout constraints (wrap_content, match_parent, or a fixed size)." +
                "You can find an example on how to override onMeasure() by looking at the android docs and the LabelView sample in your SDK directory.");

        List<String> stringList = new ArrayList<>();
        stringList.add("Hotel pick up");
        stringList.add("Dream World");
        stringList.add("Lunch");
        stringList.add("Return to hotel");
        sightSeeingTour.setItinerary(stringList);

        stringList = new ArrayList<>();
        stringList.add("Kindly check the availability with us first, and let us know where you stay");
        stringList.add("You may check flight and accommodation with us too");
        sightSeeingTour.setNoteList(stringList);

        List<TitleAndDescription> list = new ArrayList<>();
        list.add(new TitleAndDescription("Adult (About 9 years old)", "SGD $88/person"));
        list.add(new TitleAndDescription("Child (6~9 years old)", "SGD $70.4/person"));
        list.add(new TitleAndDescription("Child (3~5 years old)", "SGD $44/person"));
        list.add(new TitleAndDescription("Infant (under 3 years old)", "Free of charge"));
        sightSeeingTour.setPrice(list);

        stringList = new ArrayList<>();
        stringList.add("Sightseeing with professional English speaking local tour guide");
        stringList.add("Private air-con vehicle with drivers for transfers and excursions as mentioned in program");
        stringList.add("Entrance fees and zone fees for the visits mentioned in the program");
        stringList.add("Drinking water and towel during excursion");
        stringList.add("Meals included lunch as mentioned in the program");
        sightSeeingTour.setInclude(stringList);

        stringList = new ArrayList<>();
        stringList.add("Accommodations");
        stringList.add("Flight Tickets");
        stringList.add("Person Insurance");
        stringList.add("Porterage Fee");
        stringList.add("Visa Fee");
        stringList.add("All expense of purely personal nature");
        sightSeeingTour.setExclude(stringList);

        stringList = new ArrayList<>();
        stringList.add("Tour timing:7AM - 8PM Bangkok Time");
        sightSeeingTour.setThingsToNote(stringList);

        return sightSeeingTour;
    }

    public static void setUpListTitleAndDescriptionValuesInParent(Context context,
                                                                  LinearLayout parent,
                                                                  List<TitleAndDescription> list,
                                                                  String titleStr,
                                                                  int padding) {
        if (list != null && !list.isEmpty()) {
            if (!TextUtils.isEmpty(titleStr)) {
                TextView header = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.title1, parent, false);
                header.setText(titleStr);
                header.setPadding(padding, padding * 2, padding, 0);
                parent.addView(header);
            }

            for (TitleAndDescription titleAndDescription : list) {
                setUpTitleAndDescriptionValuesInParent(context,
                        parent,
                        titleAndDescription,
                        padding);
            }
        }
    }

    public static void setUpTitleAndDescriptionValuesInParent(Context context,
                                                              LinearLayout parent,
                                                              TitleAndDescription titleAndDescription,
                                                              int padding) {
        if (!TextUtils.isEmpty(titleAndDescription.getTitle())) {
            TextView title = new TextView(context);
            title.setPadding(padding, padding, 0, 0);
            title.setText(titleAndDescription.getTitle());
            title.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(title);
        }

        if (!TextUtils.isEmpty(titleAndDescription.getDescription())) {
            TextView description = new TextView(context);
            description.setPadding(padding * 3, padding, 0, 0);
            description.setText(titleAndDescription.getDescription());
            parent.addView(description);
        }
    }

    public static void setUpListStringValuesInParent(Context context, LinearLayout parent, List<String> list, String titleStr, int padding) {
        setUpListStringValuesInParent(context, parent, list, titleStr, padding, 0);
    }

    public static void setUpListStringValuesInParent(Context context,
                                                     LinearLayout parent,
                                                     List<String> list,
                                                     String titleStr,
                                                     int padding,
                                                     int textStyle) {
        if (list != null && !list.isEmpty()) {
            if (!TextUtils.isEmpty(titleStr)) {
                TextView title = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.title1, parent, false);
                title.setText(titleStr);
                title.setPadding(padding, padding * 2, padding, 0);
                parent.addView(title);
            }

            for (String str : list) {
                if (!TextUtils.isEmpty(str)) {
                    setUpStringValuesInParent(context,
                            parent,
                            str,
                            padding,
                            textStyle);
                }
            }
        }
    }

    public static void setUpStringValuesInParent(Context context,
                                                 LinearLayout parent,
                                                 String string,
                                                 int padding,
                                                 int textStyle) {
        TextView textView = new TextView(context);
        textView.setPadding(padding * 3, padding, 0, 0);
        textView.setText(String.format("*  %s", string));
        if (textStyle != 0) {
            textView.setTypeface(Typeface.defaultFromStyle(textStyle));
        }
        parent.addView(textView);
    }
}
