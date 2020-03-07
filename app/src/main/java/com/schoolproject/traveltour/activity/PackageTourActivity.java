package com.schoolproject.traveltour.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.Paint;
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
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.TitleAndDescription;

public class PackageTourActivity extends AppCompatActivity {
    public static final String PACKAGE_TOUR = "package_tour";
    private PackageTour packageTour;

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
        if (bundle == null || !bundle.containsKey(PACKAGE_TOUR)) {
            Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Package Tour");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }

        packageTour = (PackageTour) bundle.get(PACKAGE_TOUR);

        LinearLayout parent = findViewById(R.id.parent);

        int padding = (int) getResources().getDimension(R.dimen.medium_padding);

        TextView packageTourTitle = new TextView(this);
        packageTourTitle.setPadding(padding, padding, 0, 0);
        packageTourTitle.setText(packageTour.getTitle());
        packageTourTitle.setTextColor(Color.BLACK);
        packageTourTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title));
        packageTourTitle.setTypeface(Typeface.DEFAULT_BOLD);
        parent.addView(packageTourTitle);

        CardView cv = (CardView) LayoutInflater.from(this)
                .inflate(R.layout.image_view, parent, false);
        View view = cv.findViewById(R.id.background);
        view.setBackgroundResource(getResources().getIdentifier(packageTour.getImageUrl(), "raw", getPackageName()));
        parent.addView(cv);

        if (packageTour.getPrice() != null && !packageTour.getPrice().isEmpty()) {
            TextView tvPrice = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.title1, parent, false);
            tvPrice.setText(R.string.price_for_package);
            tvPrice.setPadding(padding, padding * 2, padding, 0);
            parent.addView(tvPrice);

            for (TitleAndDescription titleAndDescription : packageTour.getPrice()) {
                if (!TextUtils.isEmpty(titleAndDescription.getTitle())) {
                    TextView title = new TextView(this);
                    title.setPadding(padding, padding, 0, 0);
                    title.setText(titleAndDescription.getTitle());
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    parent.addView(title);
                }

                if (!TextUtils.isEmpty(titleAndDescription.getDescription())) {
                    TextView description = new TextView(this);
                    description.setPadding(padding * 3, padding, 0, 0);
                    description.setText(titleAndDescription.getDescription());
                    parent.addView(description);
                }
            }
        }

        if (packageTour.getBrief() != null && !packageTour.getBrief().isEmpty()) {
            TextView tvPrice = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.title1, parent, false);
            tvPrice.setText(R.string.brief_itinerary);
            tvPrice.setPadding(padding, padding * 2, padding, 0);
            parent.addView(tvPrice);

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

        if (packageTour.getInclude() != null && !packageTour.getInclude().isEmpty()) {
            TextView tvPrice = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.title1, parent, false);
            tvPrice.setText(R.string.package_include);
            tvPrice.setPadding(padding, padding * 2, padding, 0);
            parent.addView(tvPrice);

            for (String str : packageTour.getInclude()) {
                if (!TextUtils.isEmpty(str)) {
                    TextView title = new TextView(this);
                    title.setPadding(padding * 3, padding, 0, 0);
                    title.setText(String.format("*  %s", str));
                    parent.addView(title);
                }
            }
        }

        if (packageTour.getNotInclude() != null && !packageTour.getNotInclude().isEmpty()) {
            TextView tvPrice = (TextView) LayoutInflater.from(this)
                    .inflate(R.layout.title1, parent, false);
            tvPrice.setText(R.string.package_not_include);
            tvPrice.setPadding(padding, padding * 2, padding, 0);
            parent.addView(tvPrice);

            for (String str : packageTour.getNotInclude()) {
                if (!TextUtils.isEmpty(str)) {
                    TextView title = new TextView(this);
                    title.setPadding(padding * 3, padding, 0, 0);
                    title.setText(String.format("*  %s", str));
                    parent.addView(title);
                }
            }
        }
    }
}
