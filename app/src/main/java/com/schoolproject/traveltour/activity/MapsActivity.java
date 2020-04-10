package com.schoolproject.traveltour.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
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
    public static final String PARAM_LAT = "PARAM_LAT";
    public static final String PARAM_LNG = "PARAM_LNG";
    public static final String PARAM_TITLE = "PARAM_TITLE";
    public static final String PARAM_SET_LONG_CLICK_LISTENER = "PARAM_SET_LONG_CLICK_LISTENER";
    public static final String RESULT_LAT_LNG = "RESULT_LAT_LNG";

    private double lat, lng;
    private String title;
    private boolean setLongClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            Toast.makeText(this, "Nothing to do", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        lat = b.getDouble(PARAM_LAT);
        lng = b.getDouble(PARAM_LNG);
        title = b.getString(PARAM_TITLE);
        setLongClickListener = b.getBoolean(PARAM_SET_LONG_CLICK_LISTENER);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location;
        float zoom;
        if (lat != 0 && lng != 0) {
            // Add a marker in location and move the camera with zoom in animation
            location = new LatLng(lat, lng);
            zoom = 15.0f;
        } else {
            // Add a default marker in location(Shwe Dagon Pagoda)
            // and move the camera with zoom in animation
            location = new LatLng(16.798625, 96.149513);
            zoom = 12.0f;
        }

        googleMap.addMarker(new MarkerOptions().position(location).title(title));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));

        if (setLongClickListener) {
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Intent i = new Intent();
                    i.putExtra(RESULT_LAT_LNG, latLng);
                    setResult(RESULT_OK, i);
                    finish();
                }
            });
        }
    }
}
