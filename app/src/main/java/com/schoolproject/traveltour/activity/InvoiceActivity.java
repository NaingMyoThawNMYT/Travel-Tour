package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.Booking;
import com.schoolproject.traveltour.utils.DateUtil;

import java.util.Date;

public class InvoiceActivity extends BaseSecondActivity {
    public static final String PARAM_BOOKING = "param_booking";

    private TextView tvNo, tvDate, tvTourName, tvPrice, tvType, tvName, tvPassportNo, tvPhone, tvEmail, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        setHomeBackButtonAndToolbarTitle(getString(R.string.invoice));

        tvNo = findViewById(R.id.tv_no);
        tvDate = findViewById(R.id.tv_date);
        tvTourName = findViewById(R.id.tv_tour_name);
        tvPrice = findViewById(R.id.tv_price);
        tvType = findViewById(R.id.tv_type);
        tvName = findViewById(R.id.tv_name);
        tvPassportNo = findViewById(R.id.tv_passport_no);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvAddress = findViewById(R.id.tv_address);

        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey(PARAM_BOOKING)) {
            showErrorToast();
            return;
        }

        Booking booking = (Booking) b.get(PARAM_BOOKING);
        if (booking == null) {
            showErrorToast();
            return;
        }

        tvNo.setText(booking.getId());
        tvDate.setText(DateUtil.stdDateFormat(new Date(Long.parseLong(booking.getBookingDate()))));
        TourType tourType = TourType.getTourTypeByCode(booking.getTourType());
        tvType.setText(tourType == null ? null : tourType.getName());
        tvTourName.setText(booking.getPackageName());
        tvPrice.setText(booking.getPackagePrice());
        tvName.setText(booking.getUsername());
        tvPassportNo.setText(booking.getPassportNo());
        tvPhone.setText(booking.getPhone());
        tvEmail.setText(booking.getEmail());
        tvAddress.setText(booking.getAddress());
    }

    private void showErrorToast() {
        Toast.makeText(this, "There is no booking data to show!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onClickDone(View v) {
        onBackPressed();
    }
}
