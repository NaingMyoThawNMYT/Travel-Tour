package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.schoolproject.traveltour.R;

public abstract class MainTourActivity extends BaseSecondActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        Button btnBookNow = findViewById(R.id.btn_book_now);
        btnBookNow.setVisibility(View.VISIBLE);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBookingActivity();
            }
        });
    }

    abstract void goToBookingActivity();
}
