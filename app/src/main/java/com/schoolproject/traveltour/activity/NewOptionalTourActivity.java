package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;

import java.util.ArrayList;

public class NewOptionalTourActivity extends AppCompatActivity {
    private OptionalTour optionalTour;
    private int padding;

    private TextView tvBenefitsTitle, tvPriceTitle;
    private LinearLayout layoutBenefits, layoutPrice;
    private Button btnAddBenefits, btnAddPrice;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_optional_tour);

        optionalTour = new OptionalTour();
        padding = (int) getResources().getDimension(R.dimen.medium_padding);

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

    private void initUI() {
        View benefits = findViewById(R.id.benefits);
        tvBenefitsTitle = benefits.findViewById(R.id.tv_title);
        layoutBenefits = benefits.findViewById(R.id.layout);
        btnAddBenefits = benefits.findViewById(R.id.btn_add);

        tvBenefitsTitle.setText(R.string.benefits);
        btnAddBenefits.setText(R.string.add_benefits);

        View price = findViewById(R.id.price);
        tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_of_tour);
        btnAddPrice.setText(R.string.add_price);

        imageView = findViewById(R.id.img_background);
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

    private void goToTitleAndDescriptionActivity(String title, int requestCode) {
        Intent intent = new Intent(this, TitleAndDescriptionActivity.class);
        intent.putExtra(TitleAndDescriptionActivity.ACTIVITY_TITLE, title);
        startActivityForResult(intent, requestCode);
    }
}
