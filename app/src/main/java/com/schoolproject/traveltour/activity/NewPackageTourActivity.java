package com.schoolproject.traveltour.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;

import java.util.ArrayList;

public class NewPackageTourActivity extends BaseSecondActivity {
    private static final int REQUEST_CODE_PRICE = 101;
    private static final int REQUEST_CODE_ITINERARY = 102;
    private static final int REQUEST_CODE_PACKAGE_INCLUDE = 103;
    private static final int REQUEST_CODE_PACKAGE_NOT_INCLUDE = 104;
    private static final int REQUEST_CODE_IMAGE_PICKER = 105;
    private int padding;
    private Country selectedCountry;
    private String selectedTourType;
    private PackageTour newPackageTour;

    private TextView tvPriceTitle, tvItineraryTitle, tvPackageIncludeTitle, tvPackageNotIncludeTitle;
    private LinearLayout layoutPrice, layoutItinerary, layoutPackageInclude, layoutPackageNotInclude;
    private Button btnAddPrice, btnAddItinerary, btnAddPackageInclude, btnAddPackageNotInclude;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_package_tour);
        setHomeBackButtonAndToolbarTitle("New Tour");

        newPackageTour = new PackageTour();
        padding = (int) getResources().getDimension(R.dimen.medium_padding);

        initUI();
        initListener();

        showCountryChooserDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_TITLE);
            String description = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_DESCRIPTION);

            switch (requestCode) {
                case REQUEST_CODE_PRICE: {
                    if (newPackageTour.getPrice() == null) {
                        newPackageTour.setPrice(new ArrayList<TitleAndDescription>());
                    }
                    TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newPackageTour.getPrice().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding);
                    break;
                }
                case REQUEST_CODE_ITINERARY: {
                    if (newPackageTour.getBrief() == null) {
                        newPackageTour.setBrief(new ArrayList<TitleAndDescription>());
                    }
                    TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newPackageTour.getBrief().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutItinerary,
                            titleAndDescription,
                            padding);
                    break;
                }
                case REQUEST_CODE_PACKAGE_INCLUDE: {
                    if (newPackageTour.getInclude() == null) {
                        newPackageTour.setInclude(new ArrayList<String>());
                    }
                    newPackageTour.getInclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutPackageInclude,
                            description,
                            padding,
                            0);
                    break;
                }
                case REQUEST_CODE_PACKAGE_NOT_INCLUDE: {
                    if (newPackageTour.getNotInclude() == null) {
                        newPackageTour.setNotInclude(new ArrayList<String>());
                    }
                    newPackageTour.getNotInclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutPackageNotInclude,
                            description,
                            padding,
                            0);
                    break;
                }
                case REQUEST_CODE_IMAGE_PICKER: {
                    imageView.setImageBitmap(ImageChooserUtil.getBitmapFromIntent(
                            NewPackageTourActivity.this,
                            data));
                    break;
                }
            }
        }
    }

    private void initUI() {
        View price = findViewById(R.id.price);
        tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_for_package);
        btnAddPrice.setText(R.string.add_price);

        View itinerary = findViewById(R.id.itinerary);
        tvItineraryTitle = itinerary.findViewById(R.id.tv_title);
        layoutItinerary = itinerary.findViewById(R.id.layout);
        btnAddItinerary = itinerary.findViewById(R.id.btn_add);

        tvItineraryTitle.setText(R.string.itinerary);
        btnAddItinerary.setText(R.string.add_itinerary);

        View packageInclude = findViewById(R.id.package_include);
        tvPackageIncludeTitle = packageInclude.findViewById(R.id.tv_title);
        layoutPackageInclude = packageInclude.findViewById(R.id.layout);
        btnAddPackageInclude = packageInclude.findViewById(R.id.btn_add);

        tvPackageIncludeTitle.setText(R.string.package_include);
        btnAddPackageInclude.setText(R.string.add_include);

        View packageNotInclude = findViewById(R.id.package_not_include);
        tvPackageNotIncludeTitle = packageNotInclude.findViewById(R.id.tv_title);
        layoutPackageNotInclude = packageNotInclude.findViewById(R.id.layout);
        btnAddPackageNotInclude = packageNotInclude.findViewById(R.id.btn_add);

        tvPackageNotIncludeTitle.setText(R.string.package_not_include);
        btnAddPackageNotInclude.setText(R.string.add_not_include);

        imageView = findViewById(R.id.img_background);
    }

    private void initListener() {
        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_price), REQUEST_CODE_PRICE);
            }
        });

        btnAddItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_itinerary), REQUEST_CODE_ITINERARY);
            }
        });

        btnAddPackageInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_include), REQUEST_CODE_PACKAGE_INCLUDE);
            }
        });

        btnAddPackageNotInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_not_include), REQUEST_CODE_PACKAGE_NOT_INCLUDE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewPackageTourActivity.this, REQUEST_CODE_IMAGE_PICKER);
            }
        });
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

    private void goToTitleAndDescriptionActivity(String title, int requestCode) {
        Intent intent = new Intent(NewPackageTourActivity.this, TitleAndDescriptionActivity.class);
        intent.putExtra(TitleAndDescriptionActivity.ACTIVITY_TITLE, title);
        startActivityForResult(intent, requestCode);
    }
}
