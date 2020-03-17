package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.utils.DataSet;

public class OptionalTourActivity extends MainTourActivity {
    public static final String OPTIONAL_TOUR = "optional_tour";
    private OptionalTour optionalTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHomeBackButtonAndToolbarTitle("Optional Tour");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(OPTIONAL_TOUR)) {
            Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        optionalTour = (OptionalTour) bundle.get(OPTIONAL_TOUR);

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

        if (!TextUtils.isEmpty(optionalTour.getImageUrl())) {
            CardView cv = (CardView) LayoutInflater.from(this)
                    .inflate(R.layout.image_view, parent, false);
            View view = cv.findViewById(R.id.background);
            view.setBackgroundResource(getResources().getIdentifier(optionalTour.getImageUrl(), "raw", getPackageName()));
            parent.addView(cv);
        }

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
    void goToBookingActivity() {
        Intent i = new Intent(this, BookingActivity.class);
        i.putExtra(BookingActivity.OPTIONAL_TOUR, true);
        startActivity(i);
    }
}
