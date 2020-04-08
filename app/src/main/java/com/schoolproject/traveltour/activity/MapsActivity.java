package com.schoolproject.traveltour.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.schoolproject.traveltour.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String PARAM_LAT = "param_lat";
    public static final String PARAM_LNG = "param_lng";
    public static final String PARAM_TITLE = "param_title";

    private double lat, lng;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey(PARAM_LAT) || !b.containsKey(PARAM_LNG)) {
            Toast.makeText(this, "Invalid Location", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        lat = b.getDouble(PARAM_LAT);
        lng = b.getDouble(PARAM_LNG);
        title = b.getString(PARAM_TITLE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in location and move the camera with zoom in animation
        LatLng sydney = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(sydney).title(title));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
    }
}
