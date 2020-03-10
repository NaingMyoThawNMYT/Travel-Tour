package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.schoolproject.traveltour.R;

public class BookingActivity extends BaseSecondActivity {
    public static final String PACKAGE_TOUR = "package_tour";
    public static final String OPTIONAL_TOUR = "optional_tour";
    public static final String SIGHTSEEING_TOUR = "sightseeing_tour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        setHomeBackButtonAndToolbarTitle("Booking");

        Bundle b = getIntent().getExtras();
        if (b == null) {
            Toast.makeText(this, "There is no param!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (b.getBoolean(PACKAGE_TOUR)) {
            Toast.makeText(this, "Package Tour", Toast.LENGTH_SHORT).show();
            // TODO: 10-Mar-20 book for package tour
        } else if (b.getBoolean(OPTIONAL_TOUR)) {
            Toast.makeText(this, "Optional Tour", Toast.LENGTH_SHORT).show();
            // TODO: 10-Mar-20 book for optional tour
        } else if (b.getBoolean(SIGHTSEEING_TOUR)) {
            Toast.makeText(this, "Sightseeing Tour", Toast.LENGTH_SHORT).show();
            // TODO: 10-Mar-20 book for sightseeing tour
        }
    }
}
