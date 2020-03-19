package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;
import com.schoolproject.traveltour.utils.UiUtil;

import java.util.ArrayList;

public class NewOptionalTourActivity extends BaseNewTourActivity {
    private DatabaseReference myRef;

    private OptionalTour optionalTour;

    private ProgressDialog progressDialog;

    private LinearLayout layoutBenefits, layoutPrice;
    private Button btnAddBenefits, btnAddPrice;
    private ImageView imageView;
    private TextInputEditText edtTourTitle, edtTourSubTitle, edtTourDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_optional_tour);

        Bundle b = getIntent().getExtras();
        Country country = DataSet.getCountryParam(b);
        if (country == null) {
            showErrorToast();
            finish();
            return;
        }

        optionalTour = new OptionalTour();
        progressDialog = new ProgressDialog(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_COUNTRY)
                .child(country.getCode())
                .child(Constants.TABLE_NAME_OPTIONAL_TOUR);

        initUI();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_TITLE);
            String description = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_DESCRIPTION);

            switch (requestCode) {
                case Constants.REQUEST_CODE_BENEFITS: {
                    if (optionalTour.getBenefits() == null) {
                        optionalTour.setBenefits(new ArrayList<String>());
                    }
                    optionalTour.getBenefits().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutBenefits,
                            description,
                            padding,
                            0);
                    break;
                }
                case Constants.REQUEST_CODE_PRICE: {
                    if (optionalTour.getPrices() == null) {
                        optionalTour.setPrices(new ArrayList<TitleAndDescription>());
                    }
                    TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    optionalTour.getPrices().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding);
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER: {
                    imageView.setImageBitmap(ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data));
                    break;
                }
            }
        }
    }

    @Override
    void saveNewTour() {
        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        optionalTour.setId(id);
        optionalTour.setTitle(UiUtil.getString(edtTourTitle));
        optionalTour.setSubTitle(UiUtil.getString(edtTourSubTitle));
        BitmapDrawable bd = ((BitmapDrawable) imageView.getDrawable());
        if (bd != null) {
            Bitmap bm = bd.getBitmap();
            optionalTour.setBase64ImageStr(BitmapUtil.bitmapToBase64String(bm));
        }
        optionalTour.setDescription(UiUtil.getString(edtTourDescription));

        // Saving to firebase
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        myRef.child(optionalTour.getId())
                .setValue(optionalTour).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to save! Try again!",
                Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
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
    }

    private void initListener() {
        btnAddBenefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_benefits), Constants.REQUEST_CODE_BENEFITS);
            }
        });

        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_price), Constants.REQUEST_CODE_PRICE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewOptionalTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER);
            }
        });
    }

    private void showErrorToast() {
        Toast.makeText(this, "Please choose country first!", Toast.LENGTH_SHORT).show();
    }
}
