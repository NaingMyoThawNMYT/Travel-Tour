package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Booking;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.UiUtil;

public class BookingActivity extends BaseSecondActivity {
    public static final String PARAM_SELECTED_TOUR = "param_selected_tour";
    public static String selectedCountry = null;

    private DatabaseReference myRef;

    private PackageTour packageTour;
    private OptionalTour optionalTour;
    private SightSeeingTour sightSeeingTour;

    private TextInputEditText edtName, edtPassportNo, edtPhone, edtEmail, edtAddress;
    private ProgressDialog progressDialog;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_BOOKING);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        edtEmail.setText(currentUser.getEmail());

        // TODO: 11-Mar-20 this is simple and will be delete in later
        findViewById(R.id.btn_book_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTour();
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

    private void bookTour() {
        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        String tourId;
        String tourType;

        if (packageTour != null) {
            tourId = packageTour.getId();
            tourType = Constants.TABLE_NAME_PACKAGE_TOUR;
        } else if (optionalTour != null) {
            tourId = optionalTour.getId();
            tourType = Constants.TABLE_NAME_OPTIONAL_TOUR;
        } else {
            tourId = sightSeeingTour.getId();
            tourType = Constants.TABLE_NAME_SIGHTSEEING_TOUR;
        }

        Booking booking = new Booking();
        booking.setId(id);
        booking.setTourId(tourId);
        booking.setTourCountry(selectedCountry);
        booking.setTourType(tourType);
        booking.setUsername(UiUtil.getString(edtName));
        booking.setPassportNo(UiUtil.getString(edtPassportNo));
        booking.setPhone(UiUtil.getString(edtPhone));
        booking.setEmail(UiUtil.getString(edtEmail));
        booking.setAddress(UiUtil.getString(edtAddress));

        progressDialog.show();
        myRef.child(id)
                .setValue(booking)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(BookingActivity.this, InvoiceActivity.class));
                        } else {
                            showFailToSaveToast();
                        }
                    }
                });
    }

    public void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to save! Try again!",
                Toast.LENGTH_SHORT).show();
    }
}
