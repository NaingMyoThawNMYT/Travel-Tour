package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourListActivity extends AppCompatActivity {
    public static final String PARAM_TOUR = "param_tour";

    private AppCompatSpinner tourListSpinner;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        Country country = DataSet.getCountryParam(b);
        if (country == null) {
            Toast.makeText(this, "There is no selected country!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initUI();
        initListener();

        DataSet.selectedCountry = country.getCode();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Tour List");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }
        tourListSpinner = findViewById(R.id.tour_spinner);
        RecyclerView recyclerView = findViewById(R.id.main_rv);

        findViewById(R.id.tour_spinner_layout).setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DataSet.TOUR_LIST);
        tourListSpinner.setAdapter(adapter);

        menuAdapter = new MenuAdapter(this, new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                Class detailsClass;
                if (tourListSpinner.getSelectedItemPosition() == 1) {
                    detailsClass = OptionalTourActivity.class;
                } else if (tourListSpinner.getSelectedItemPosition() == 2) {
                    detailsClass = SightseeingTourActivity.class;
                } else {
                    detailsClass = PackageTourActivity.class;
                }
                goToTourDetails(detailsClass, menu);
            }
        });
        recyclerView.setAdapter(menuAdapter);
    }

    private void initListener() {
        tourListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshTourList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void goToTourDetails(Class detailsClass, Menu tour) {
        Intent i = new Intent(TourListActivity.this, detailsClass);
        i.putExtra(PARAM_TOUR, tour);
        startActivity(i);
    }

    private void refreshTourList(int position) {
        List<Menu> tours = new ArrayList<>();
        if (DataSet.tourDataSet != null && !DataSet.tourDataSet.isEmpty()) {
            Menu tour = null;
            TourType type = null;
            switch (position) {
                case 0: {
                    tour = new PackageTour();
                    type = TourType.PACKAGE_TOUR;
                    break;
                }
                case 1: {
                    tour = new OptionalTour();
                    type = TourType.OPTIONAL_TOUR;
                    break;
                }
                case 2: {
                    tour = new SightSeeingTour();
                    type = TourType.SIGHTSEEING_TOUR;
                    break;
                }
            }

            if (tour != null) {
                for (Map<String, Object> map : DataSet.tourDataSet) {
                    if (type.getCode().equals(map.get("type")) &&
                            DataSet.selectedCountry.equals(map.get("country"))) {
                        tour.parse(map);
                        tours.add(tour);
                    }
                }
            }
        }

        menuAdapter.setDataSet(tours);
    }
}
