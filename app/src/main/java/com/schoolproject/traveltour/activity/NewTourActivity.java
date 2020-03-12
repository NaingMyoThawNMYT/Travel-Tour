package com.schoolproject.traveltour.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;

public class NewTourActivity extends AppCompatActivity {
    private Country selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);

        showCountryChooserDialog();
    }

    private void showCountryChooserDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_country_chooser);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        RadioGroup rdgCountry = dialog.findViewById(R.id.rdg_country);
        rdgCountry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_maldives) {
                    selectedCountry = Country.MALDIVES;
                } else if (checkedId == R.id.rdb_vietnam) {
                    selectedCountry = Country.VIETNAM;
                } else if (checkedId == R.id.rdb_myanmar) {
                    selectedCountry = Country.MYANMAR;
                } else if (checkedId == R.id.rdb_hong_kong) {
                    selectedCountry = Country.HONG_KONG;
                }

                showTourTypeChooserDialog();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showTourTypeChooserDialog() {
        Toast.makeText(this, "Show tour type chooser dialog", Toast.LENGTH_SHORT).show();
    }
}
