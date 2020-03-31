package com.schoolproject.traveltour.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.activity.MainActivity;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.model.WishList;

import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public static final String[] TOUR_LIST = new String[]{"Package Tour", "Optional Tour", "Sightseeing Tour"};

    public static List<WishList> wishLists = null;

    public static List<Menu> getMenuList() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu("maldives", "Maldives", null));
        menuList.add(new Menu("vietnam", "Vietnam", null));
        menuList.add(new Menu("myanmar", "Myanmar", null));
        menuList.add(new Menu("hongkong", "Hong Kong", null));
        return menuList;
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
                        padding,
                        null);
            }
        }
    }

    public static void setUpTitleAndDescriptionValuesInParent(Context context,
                                                              final LinearLayout parent,
                                                              TitleAndDescription titleAndDescription,
                                                              int padding,
                                                              final OnClearClickListener onRemoveListener) {
        final TextView title = new TextView(context);
        if (!TextUtils.isEmpty(titleAndDescription.getTitle())) {
            title.setPadding(padding, padding, 0, 0);
            title.setText(titleAndDescription.getTitle());
            title.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(title);
        }

        if (!TextUtils.isEmpty(titleAndDescription.getDescription())) {
            TextView description = new TextView(context);
            description.setPadding(padding * 3, padding, 0, 0);
            description.setText(titleAndDescription.getDescription());

            if (onRemoveListener != null) {
                description.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                        null,
                        context.getResources().getDrawable(R.drawable.ic_close_gray_24dp),
                        null);
                description.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parent.removeView(title);
                        parent.removeView(v);

                        onRemoveListener.onClear();
                    }
                });
            }

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
                            textStyle,
                            null);
                }
            }
        }
    }

    public static void setUpStringValuesInParent(Context context,
                                                 final LinearLayout parent,
                                                 String string,
                                                 int padding,
                                                 int textStyle,
                                                 final OnClearClickListener onRemoveListener) {
        final TextView textView = new TextView(context);
        textView.setPadding(padding * 3, padding, 0, 0);
        textView.setText(String.format("*  %s", string));
        if (textStyle != 0) {
            textView.setTypeface(Typeface.defaultFromStyle(textStyle));
        }

        if (onRemoveListener != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null,
                    null,
                    context.getResources().getDrawable(R.drawable.ic_close_gray_24dp),
                    null);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.removeView(v);

                    onRemoveListener.onClear();
                }
            });
        }

        parent.addView(textView);
    }

    public static Country getCountryParam(Bundle b) {
        if (b == null || !b.containsKey(MainActivity.PARAM_COUNTRY)) {
            return null;
        }
        return (Country) b.get(MainActivity.PARAM_COUNTRY);
    }

    public interface OnClearClickListener {
        void onClear();
    }

    public static boolean isIncludeInWishList(String tourId) {
        if (wishLists == null || wishLists.isEmpty()) {
            return false;
        }

        for (WishList w : wishLists) {
            if (w.getTourId().equals(tourId)) {
                return true;
            }
        }

        return false;
    }
}
