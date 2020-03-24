package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;

public class BookingActivity extends BaseSecondActivity {
    public static final String PARAM_SELECTED_TOUR = "param_selected_tour";

    private PackageTour packageTour;
    private OptionalTour optionalTour;
    private SightSeeingTour sightSeeingTour;

    private TextInputEditText edtName, edtPassportNo, edtPhone, edtEmail, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        setHomeBackButtonAndToolbarTitle("Booking");

        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey(PARAM_SELECTED_TOUR)) {
            showErrorToast();
            return;
        }

        Menu tour = (Menu) b.get(PARAM_SELECTED_TOUR);
        if (tour == null) {
            showErrorToast();
            return;
        }

        if (tour instanceof PackageTour) {
            packageTour = (PackageTour) tour;
        } else if (tour instanceof OptionalTour) {
            optionalTour = (OptionalTour) tour;
        } else if (tour instanceof SightSeeingTour) {
            sightSeeingTour = (SightSeeingTour) tour;
        } else {
            showErrorToast();
            return;
        }

        initUI();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Please sign in first", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        edtEmail.setText(currentUser.getEmail());

        // TODO: 11-Mar-20 this is simple and will be delete in later
        findViewById(R.id.btn_book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingActivity.this, InvoiceActivity.class));
            }
        });
    }

    private void initUI() {
        edtName = findViewById(R.id.edt_name);
        edtPassportNo = findViewById(R.id.edt_nrc);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
    }

    private void showErrorToast() {
        Toast.makeText(this, "There is no param!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
