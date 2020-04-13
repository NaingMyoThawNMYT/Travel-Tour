package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;
import com.schoolproject.traveltour.utils.UiUtil;

import java.util.ArrayList;

public class NewOptionalTourActivity extends BaseNewTourActivity {
    private OptionalTour newOptionalTour;

    private LinearLayout layoutBenefits, layoutPrice;
    private Button btnAddBenefits, btnAddPrice;
    private TextInputEditText edtTourSubTitle, edtTourDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_optional_tour);

        setHomeBackButtonAndToolbarTitle(getString(R.string.add_optional_tour));

        newOptionalTour = new OptionalTour();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_TOUR);

        initUI();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            final String title = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_TITLE);
            final String description = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_DESCRIPTION);

            if (requestCode != Constants.REQUEST_CODE_IMAGE_PICKER &&
                    TextUtils.isEmpty(description)) {
                return;
            }

            switch (requestCode) {
                case Constants.REQUEST_CODE_BENEFITS: {
                    if (newOptionalTour.getBenefits() == null) {
                        newOptionalTour.setBenefits(new ArrayList<String>());
                    }
                    newOptionalTour.getBenefits().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutBenefits,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newOptionalTour.getBenefits().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_PRICE: {
                    if (newOptionalTour.getPrices() == null) {
                        newOptionalTour.setPrices(new ArrayList<TitleAndDescription>());
                    }
                    final TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newOptionalTour.getPrices().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newOptionalTour.getPrices().remove(titleAndDescription);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER: {
                    Bitmap bm = BitmapUtil.resize(ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data));
                    imageView.setImageBitmap(bm);
                    newOptionalTour.addImageBase64(BitmapUtil.bitmapToBase64String(bm));
                    break;
                }
            }
        }
    }

    @Override
    void addImageBase64(String string) {
        newOptionalTour.addImageBase64(string);
    }

    @Override
    void onLocationMapSelected(LatLng latLng) {
        newOptionalTour.setLatitude(latLng.latitude);
        newOptionalTour.setLongitude(latLng.longitude);
    }

    @Override
    void saveNewTour() {
        final String title = UiUtil.getString(edtTourTitle);
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Enter title", Toast.LENGTH_SHORT).show();
            edtTourTitle.requestFocus();
            return;
        }

        if (newOptionalTour.getImagesBase64() == null || newOptionalTour.getImagesBase64().isEmpty()) {
            Toast.makeText(this, "Please add at least one photo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newOptionalTour.getPrices() == null || newOptionalTour.getPrices().isEmpty()) {
            Toast.makeText(this, "Please add at least one price", Toast.LENGTH_SHORT).show();
            return;
        }

        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        newOptionalTour.setId(id);
        newOptionalTour.setCountryId(selectedCountryId);
        newOptionalTour.setType(TourType.OPTIONAL_TOUR.getCode());
        newOptionalTour.setTitle(title);
        newOptionalTour.setSubTitle(UiUtil.getString(edtTourSubTitle));
        newOptionalTour.setDescription(UiUtil.getString(edtTourDescription));

        // Saving to firebase
        progressDialog.show();
        myRef.child(newOptionalTour.getId())
                .setValue(newOptionalTour).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                } else {
                    showFailToSaveToast();
                }
            }
        });
    }

    private void initUI() {
        spnCountry = findViewById(R.id.spn_country);

        View benefits = findViewById(R.id.benefits);
        TextView tvBenefitsTitle = benefits.findViewById(R.id.tv_title);
        layoutBenefits = benefits.findViewById(R.id.layout);
        btnAddBenefits = benefits.findViewById(R.id.btn_add);

        tvBenefitsTitle.setText(R.string.benefits);
        btnAddBenefits.setText(R.string.add_benefits);

        View price = findViewById(R.id.price);
        TextView tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_of_tour);
        btnAddPrice.setText(R.string.add_price);

        imageView = findViewById(R.id.img_background);

        edtTourTitle = findViewById(R.id.edt_tour_name);
        edtTourSubTitle = findViewById(R.id.edt_tour_sub_title);
        edtTourDescription = findViewById(R.id.edt_tour_description);

        initAddMapUI();

        initImagePickers();
    }

    private void initAddMapUI() {
        tvAddMap = findViewById(R.id.location);
        tvAddMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewOptionalTourActivity.this, MapsActivity.class);
                i.putExtra(MapsActivity.PARAM_LAT, newOptionalTour.getLatitude());
                i.putExtra(MapsActivity.PARAM_LNG, newOptionalTour.getLongitude());
                i.putExtra(MapsActivity.PARAM_SET_LONG_CLICK_LISTENER, true);
                startActivityForResult(
                        i,
                        REQUEST_CODE);
            }
        });
    }

    private void initListener() {
        btnAddBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_benefits),
                        false,
                        Constants.REQUEST_CODE_BENEFITS);
            }
        });

        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_price),
                        true,
                        Constants.REQUEST_CODE_PRICE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewOptionalTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER);
            }
        });

        initSpinnerListener();
    }
}
