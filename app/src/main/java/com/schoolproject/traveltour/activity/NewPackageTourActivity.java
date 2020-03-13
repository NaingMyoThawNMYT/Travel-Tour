package com.schoolproject.traveltour.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.utils.DataSet;

public class NewPackageTourActivity extends BaseSecondActivity {
    private Country selectedCountry;
    private String selectedTourType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_package_tour);
        setHomeBackButtonAndToolbarTitle("New Tour");

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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_tour_type_chooser);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        RadioGroup rdgCountry = dialog.findViewById(R.id.rdg_tour_type);
        rdgCountry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_package_tour) {
                    selectedTourType = DataSet.TOUR_LIST[0];
                } else if (checkedId == R.id.rdb_optional_tour) {
                    selectedTourType = DataSet.TOUR_LIST[1];
                } else if (checkedId == R.id.rdb_sightseeing_tour) {
                    selectedTourType = DataSet.TOUR_LIST[2];
                }

                Toast.makeText(NewPackageTourActivity.this, "Show Editor", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
