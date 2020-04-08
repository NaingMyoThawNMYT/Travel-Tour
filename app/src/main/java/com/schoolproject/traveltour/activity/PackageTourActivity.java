package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.model.WishList;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.DataSet;

public class PackageTourActivity extends MainTourActivity {
    private PackageTour packageTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHomeBackButtonAndToolbarTitle("Package Tour");

        packageTour = (PackageTour) getParamTourOrFinishActivity();

        LinearLayout parent = findViewById(R.id.parent);

        int padding = (int) getResources().getDimension(R.dimen.medium_padding);

        if (!TextUtils.isEmpty(packageTour.getTitle())) {
            TextView packageTourTitle = new TextView(this);
            packageTourTitle.setPadding(padding, padding, 0, 0);
            packageTourTitle.setText(packageTour.getTitle());
            packageTourTitle.setTextColor(Color.BLACK);
            packageTourTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title));
            packageTourTitle.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(packageTourTitle);
        }

        if (!TextUtils.isEmpty(packageTour.getBase64ImageStr())) {
            View parentViewGroup = LayoutInflater.from(this)
                    .inflate(R.layout.image_view, parent, false);
            parentViewGroup.findViewById(R.id.location).setVisibility(View.GONE);
            ImageView img = parentViewGroup.findViewById(R.id.img_background);
            img.setImageBitmap(BitmapUtil.base64StringToBitmap(packageTour.getBase64ImageStr()));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToMapActivity(packageTour.getTitle(),
                            packageTour.getLatitude(),
                            packageTour.getLongitude());
                }
            });
            parentViewGroup.findViewById(R.id.img_add).setVisibility(View.GONE);
            parent.addView(parentViewGroup);
        }

        DataSet.setUpListTitleAndDescriptionValuesInParent(this, parent, packageTour.getPrice(), getString(R.string.price_for_package), padding);

        if (packageTour.getBrief() != null && !packageTour.getBrief().isEmpty()) {
            TextView briefTitle = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.title1, parent, false);
            briefTitle.setText(R.string.brief_itinerary);
            briefTitle.setPadding(padding, padding * 2, padding, 0);
            parent.addView(briefTitle);

            for (TitleAndDescription titleAndDescription : packageTour.getBrief()) {
                if (!TextUtils.isEmpty(titleAndDescription.getTitle())) {
                    TextView title = new TextView(this);
                    title.setPadding(padding * 3, padding, 0, 0);
                    title.setText(titleAndDescription.getTitle());
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    title.setTextColor(Color.BLUE);
                    parent.addView(title);

                    final TextView description = new TextView(this);
                    if (!TextUtils.isEmpty(titleAndDescription.getDescription())) {
                        description.setPadding(padding * 6, padding, 0, 0);
                        description.setText(titleAndDescription.getDescription());
                        description.setVisibility(View.GONE);
                        parent.addView(description);
                    }

                    title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            description.setVisibility(description.getVisibility() == View.VISIBLE
                                    ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
                }
            }
        }
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getInclude(), getString(R.string.package_include), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getNotInclude(), getString(R.string.package_not_include), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getChoice(), getString(R.string.choice_of_accommodations), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getHighLight(), getString(R.string.highlights), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getRoomType(), getString(R.string.room_types), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getAmenity(), getString(R.string.amenities), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getDining(), getString(R.string.dining), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getActivity(), getString(R.string.activities), padding);
        DataSet.setUpListStringValuesInParent(this, parent, packageTour.getHoneyMoonBenefit(), getString(R.string.honeymoon_benefits), padding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_wishlist);
        bookmark = DataSet.isNotIncludeInWishList(packageTour.getId());
        changeIcon(item);

        return true;
    }

    @Override
    void goToBookingActivity() {
        Intent i = new Intent(this, BookingActivity.class);
        i.putExtra(BookingActivity.PARAM_SELECTED_TOUR, packageTour);
        startActivity(i);
    }

    @Override
    void bookMark(BookMarkCallback callback) {
        WishList wishList = new WishList();
        wishList.setTourId(packageTour.getId());
        wishList.setTourType(TourType.PACKAGE_TOUR.getCode());
        wishList.setTourCountry(DataSet.selectedCountry);

        callback.saveOrRemoveBookmark(wishList);
    }
}
