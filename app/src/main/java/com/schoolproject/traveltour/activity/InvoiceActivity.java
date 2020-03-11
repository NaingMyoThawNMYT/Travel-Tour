package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.schoolproject.traveltour.R;

import java.util.Date;

public class InvoiceActivity extends BaseSecondActivity {
    private TextView tvNo, tvDate, tvTourName, tvPrice, tvType, tvName, tvPhone, tvAddress;

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
        tvPhone = findViewById(R.id.tv_phone);
        tvAddress = findViewById(R.id.tv_address);

        // TODO: 11-Mar-20 get data from param
        tvNo.setText("123456");
        tvDate.setText(new Date().toString());
        tvType.setText("Package Tour");
        tvTourName.setText("3D2N Bagan & Balloon");
        tvPrice.setText("500,000 MMK");
        tvName.setText("Naing Myo Thaw");
        tvPhone.setText("0900000");
        tvAddress.setText("Thaketa, Yangon");
    }
}
