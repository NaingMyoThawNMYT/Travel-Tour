package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.WishList;
import com.schoolproject.traveltour.utils.DataSet;

public class OptionalTourActivity extends MainTourActivity {
    private OptionalTour optionalTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHomeBackButtonAndToolbarTitle("Optional Tour");

        optionalTour = (OptionalTour) TourListActivity.selectedTour;

        LinearLayout parent = findViewById(R.id.parent);

        int padding = (int) getResources().getDimension(R.dimen.medium_padding);

        if (!TextUtils.isEmpty(optionalTour.getTitle())) {
            TextView packageTourTitle = new TextView(this);
            packageTourTitle.setPadding(padding, padding, 0, 0);
            packageTourTitle.setText(optionalTour.getTitle());
            packageTourTitle.setTextColor(Color.BLACK);
            packageTourTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title));
            packageTourTitle.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(packageTourTitle);
        }

        if (!TextUtils.isEmpty(optionalTour.getSubTitle())) {
            TextView titleNote = new TextView(this);
            titleNote.setPadding(padding, padding, 0, 0);
            titleNote.setText(optionalTour.getSubTitle());
            titleNote.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_note));
            titleNote.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(titleNote);
        }

// TODO: 4/12/2020 create image array ui
//        if (!TextUtils.isEmpty(optionalTour.getBase64ImageStr())) {
//            View parentViewGroup = LayoutInflater.from(this)
//                    .inflate(R.layout.image_view, parent, false);
//            parentViewGroup.findViewById(R.id.location).setVisibility(View.GONE);
//            ImageView img = parentViewGroup.findViewById(R.id.img_background);
//            img.setImageBitmap(BitmapUtil.base64StringToBitmap(optionalTour.getBase64ImageStr()));
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    goToMapActivity(optionalTour.getTitle(),
//                            optionalTour.getLatitude(),
//                            optionalTour.getLongitude());
//                }
//            });
//            parentViewGroup.findViewById(R.id.img_add).setVisibility(View.GONE);
//            parent.addView(parentViewGroup);
//        }

        if (!TextUtils.isEmpty(optionalTour.getDescription())) {
            TextView description = new TextView(this);
            description.setPadding(padding, padding, 0, 0);
            description.setText(optionalTour.getDescription());
            parent.addView(description);
        }

        DataSet.setUpListStringValuesInParent(this, parent, optionalTour.getBenefits(), getString(R.string.benefits), padding);
        DataSet.setUpListTitleAndDescriptionValuesInParent(this, parent, optionalTour.getPrices(), getString(R.string.price_of_tour), padding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.action_wishlist);
        bookmark = DataSet.isNotIncludeInWishList(optionalTour.getId());
        changeIcon(item);

        return true;
    }

    @Override
    void goToBookingActivity() {
        Intent i = new Intent(this, BookingActivity.class);
        optionalTour.setImagesBase64(null);
        i.putExtra(BookingActivity.PARAM_SELECTED_TOUR, optionalTour);
        startActivity(i);
    }

    @Override
    void bookMark(BookMarkCallback callback) {
        WishList wishList = new WishList();
        wishList.setTourId(optionalTour.getId());
        wishList.setTourType(TourType.OPTIONAL_TOUR.getCode());
        wishList.setTourCountry(optionalTour.getCountryId());

        callback.saveOrRemoveBookmark(wishList);
    }
}
