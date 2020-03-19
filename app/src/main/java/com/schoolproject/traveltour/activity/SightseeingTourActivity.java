package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.DataSet;

public class SightseeingTourActivity extends MainTourActivity {
    public static final String SIGHTSEEING_TOUR = "sightseeing_tour";
    private SightSeeingTour sightSeeingTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHomeBackButtonAndToolbarTitle("Sightseeing Tour");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(SIGHTSEEING_TOUR)) {
            Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        sightSeeingTour = (SightSeeingTour) bundle.get(SIGHTSEEING_TOUR);

        LinearLayout parent = findViewById(R.id.parent);

        int padding = (int) getResources().getDimension(R.dimen.medium_padding);

        if (!TextUtils.isEmpty(sightSeeingTour.getTitle())) {
            TextView packageTourTitle = new TextView(this);
            packageTourTitle.setPadding(padding, padding, 0, 0);
            packageTourTitle.setText(sightSeeingTour.getTitle());
            packageTourTitle.setTextColor(Color.BLACK);
            packageTourTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title));
            packageTourTitle.setTypeface(Typeface.DEFAULT_BOLD);
            parent.addView(packageTourTitle);
        }

        if (!TextUtils.isEmpty(sightSeeingTour.getImageUrl())) {
            CardView cv = (CardView) LayoutInflater.from(this)
                    .inflate(R.layout.image_view, parent, false);
            ImageView ima = cv.findViewById(R.id.img_background);
            ima.setImageBitmap(BitmapUtil.base64StringToBitmap(sightSeeingTour.getBase64ImageStr()));
            parent.addView(cv);
        }

        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getItinerary(), getString(R.string.itinerary), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getNoteList(), "Note Title", padding, Typeface.ITALIC);
        DataSet.setUpListTitleAndDescriptionValuesInParent(this, parent, sightSeeingTour.getPrice(), getString(R.string.price_of_tour), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getInclude(), getString(R.string.services_include), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getExclude(), getString(R.string.package_excludes), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getThingsToNote(), getString(R.string.thing_to_note), padding);
    }

    @Override
    void goToBookingActivity() {
        Intent i = new Intent(this, BookingActivity.class);
        i.putExtra(BookingActivity.SIGHTSEEING_TOUR, true);
        startActivity(i);
    }
}
