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
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.Booking;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.UiUtil;

import java.util.Date;

public class BookingActivity extends BaseSecondActivity {
    public static final String PARAM_SELECTED_TOUR = "param_selected_tour";

    private String tourId;
    private String tourType;
    private String packageName;
    private String packagePrice;
    private String countryId;

    private DatabaseReference myRef;

    private TextInputEditText edtName, edtPassportNo, edtPhone, edtEmail, edtAddress;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        hideToolbar();

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
            PackageTour packageTour = (PackageTour) tour;
            tourId = packageTour.getId();
            tourType = TourType.PACKAGE_TOUR.getCode();
            packageName = packageTour.getTitle();
            packagePrice = packageTour.getPrice().get(0).getDescription();
            countryId = packageTour.getCountryId();
        } else if (tour instanceof OptionalTour) {
            OptionalTour optionalTour = (OptionalTour) tour;
            tourId = optionalTour.getId();
            tourType = TourType.OPTIONAL_TOUR.getCode();
            packageName = optionalTour.getTitle();
            packagePrice = optionalTour.getPrices().get(0).getDescription();
            countryId = optionalTour.getCountryId();
        } else if (tour instanceof SightSeeingTour) {
            SightSeeingTour sightSeeingTour = (SightSeeingTour) tour;
            tourId = sightSeeingTour.getId();
            tourType = TourType.SIGHTSEEING_TOUR.getCode();
            packageName = sightSeeingTour.getTitle();
            packagePrice = sightSeeingTour.getPrice().get(0).getDescription();
            countryId = sightSeeingTour.getCountryId();
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

    public void bookTour(View v) {
        final String username = UiUtil.getString(edtName);
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String passportNo = UiUtil.getString(edtPassportNo);
        if (TextUtils.isEmpty(passportNo)) {
            Toast.makeText(this, "Please enter passport!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String phoneNo = UiUtil.getString(edtPhone);
        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(this, "Please enter phone!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String address = UiUtil.getString(edtAddress);
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter address!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        final Booking booking = new Booking();
        booking.setId(id);
        booking.setBookingDate(String.valueOf(new Date().getTime()));
        booking.setTourId(tourId);
        booking.setCountryId(countryId);
        booking.setTourType(tourType);
        booking.setPackageName(packageName);
        booking.setPackagePrice(packagePrice);
        booking.setUsername(username);
        booking.setPassportNo(passportNo);
        booking.setPhone(phoneNo);
        booking.setEmail(UiUtil.getString(edtEmail));
        booking.setAddress(address);

        progressDialog.show();
        myRef.child(id)
                .setValue(booking)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent i = new Intent(BookingActivity.this, InvoiceActivity.class);
                            i.putExtra(InvoiceActivity.PARAM_BOOKING, booking);
                            startActivity(i);
                            finish();
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
