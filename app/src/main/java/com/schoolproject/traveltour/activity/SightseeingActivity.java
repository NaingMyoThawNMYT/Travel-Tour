package com.schoolproject.traveltour.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.DataSet;

public class SightseeingActivity extends AppCompatActivity {
    public static final String SIGHTSEEING_TOUR = "sightseeing_tour";
    private SightSeeingTour sightSeeingTour;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(SIGHTSEEING_TOUR)) {
            Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Sightseeing Tour");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
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
            View view = cv.findViewById(R.id.background);
            view.setBackgroundResource(getResources().getIdentifier(sightSeeingTour.getImageUrl(), "raw", getPackageName()));
            parent.addView(cv);
        }

        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getItinerary(), getString(R.string.itinerary), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getNoteList(), "", padding, Typeface.ITALIC);
        DataSet.setUpListTitleAndDescriptionValuesInParent(this, parent, sightSeeingTour.getPrice(), getString(R.string.price_of_tour), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getInclude(), getString(R.string.service_include), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getInclude(), getString(R.string.package_exclude), padding);
        DataSet.setUpListStringValuesInParent(this, parent, sightSeeingTour.getThingsToNote(), getString(R.string.thing_to_note), padding);
    }
}
