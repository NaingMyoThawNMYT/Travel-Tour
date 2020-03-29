package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourListActivity extends AppCompatActivity {
    public static final String PARAM_TOUR = "param_tour";

    private DatabaseReference packageRef, optionalRef, sightseeingRef;
    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            progressDialog.dismiss();
            List<Menu> dataSet = new ArrayList<>();

            if (tourListSpinner.getSelectedItemPosition() == 1) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OptionalTour tour = new OptionalTour();
                    tour.parse((Map<String, Object>) snapshot.getValue());
                    dataSet.add(tour);
                }
            } else if (tourListSpinner.getSelectedItemPosition() == 1) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SightSeeingTour tour = new SightSeeingTour();
                    tour.parse((Map<String, Object>) snapshot.getValue());
                    dataSet.add(tour);
                }
            } else {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PackageTour tour = new PackageTour();
                    tour.parse((Map<String, Object>) snapshot.getValue());
                    dataSet.add(tour);
                }
            }

            menuAdapter.setDataSet(dataSet);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    private ProgressDialog progressDialog;
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

        BookingActivity.selectedCountry = country.getCode();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.TABLE_NAME_COUNTRY)
                .child(country.getCode());
        packageRef = myRef.child(Constants.TABLE_NAME_PACKAGE_TOUR);
        optionalRef = myRef.child(Constants.TABLE_NAME_OPTIONAL_TOUR);
        sightseeingRef = myRef.child(Constants.TABLE_NAME_SIGHTSEEING_TOUR);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        initUI();
        initListener();
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
                progressDialog.show();
                switch (position) {
                    case 0: {
                        packageRef.addValueEventListener(valueEventListener);
                        optionalRef.removeEventListener(valueEventListener);
                        sightseeingRef.removeEventListener(valueEventListener);
                        break;
                    }
                    case 1: {
                        packageRef.removeEventListener(valueEventListener);
                        optionalRef.addValueEventListener(valueEventListener);
                        sightseeingRef.removeEventListener(valueEventListener);
                        break;
                    }
                    case 2: {
                        packageRef.removeEventListener(valueEventListener);
                        optionalRef.removeEventListener(valueEventListener);
                        sightseeingRef.addValueEventListener(valueEventListener);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void goToTourDetails(Class detailsClass, Menu tour) {
        Intent sightseeingTourIntent = new Intent(TourListActivity.this, detailsClass);
        sightseeingTourIntent.putExtra(PARAM_TOUR, tour);
        startActivity(sightseeingTourIntent);
    }
}
