package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.schoolproject.traveltour.R;

public class BookingActivity extends BaseSecondActivity {
    public static final String PACKAGE_TOUR = "package_tour";
    public static final String OPTIONAL_TOUR = "optional_tour";
    public static final String SIGHTSEEING_TOUR = "sightseeing_tour";

    private FirebaseAuth mAuth;

    private TextInputEditText edtName, edtPassportNo, edtPhone, edtEmail, edtAddress;

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

        initUI();

        mAuth = FirebaseAuth.getInstance();
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
}
