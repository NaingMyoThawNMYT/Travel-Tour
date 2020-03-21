package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.PackageTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;
import com.schoolproject.traveltour.utils.UiUtil;

import java.util.ArrayList;

public class NewPackageTourActivity extends BaseNewTourActivity {
    private static final int REQUEST_CODE_PRICE = 101;
    private static final int REQUEST_CODE_ITINERARY = 102;
    private static final int REQUEST_CODE_PACKAGE_INCLUDE = 103;
    private static final int REQUEST_CODE_PACKAGE_NOT_INCLUDE = 104;
    private static final int REQUEST_CODE_IMAGE_PICKER = 105;
    private PackageTour newPackageTour;

    private LinearLayout layoutPrice, layoutItinerary, layoutPackageInclude, layoutPackageNotInclude;
    private Button btnAddPrice, btnAddItinerary, btnAddPackageInclude, btnAddPackageNotInclude;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_package_tour);
        setHomeBackButtonAndToolbarTitle("New Tour");

        Bundle b = getIntent().getExtras();
        Country country = DataSet.getCountryParam(b);
        if (country == null) {
            showErrorToast();
            finish();
            return;
        }

        setHomeBackButtonAndToolbarTitle(getString(R.string.add_package_tour));

        newPackageTour = new PackageTour();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_COUNTRY)
                .child(country.getCode())
                .child(Constants.TABLE_NAME_PACKAGE_TOUR);

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
                case REQUEST_CODE_PRICE: {
                    if (newPackageTour.getPrice() == null) {
                        newPackageTour.setPrice(new ArrayList<TitleAndDescription>());
                    }
                    final TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newPackageTour.getPrice().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newPackageTour.getPrice().remove(titleAndDescription);
                                }
                            });
                    break;
                }
                case REQUEST_CODE_ITINERARY: {
                    if (newPackageTour.getBrief() == null) {
                        newPackageTour.setBrief(new ArrayList<TitleAndDescription>());
                    }
                    final TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newPackageTour.getBrief().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutItinerary,
                            titleAndDescription,
                            padding,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newPackageTour.getBrief().remove(titleAndDescription);
                                }
                            });
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
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newPackageTour.getInclude().remove(description);
                                }
                            });
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
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newPackageTour.getNotInclude().remove(description);
                                }
                            });
                    break;
                }
                case REQUEST_CODE_IMAGE_PICKER: {
                    Bitmap bm = ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data);
                    imageView.setImageBitmap(bm);
                    newPackageTour.setBase64ImageStr(BitmapUtil.bitmapToBase64String(bm));
                    break;
                }
            }
        }
    }

    @Override
    void saveNewTour() {
        final String title = UiUtil.getString(edtTourTitle);
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Enter title", Toast.LENGTH_SHORT).show();
            edtTourTitle.requestFocus();
            return;
        }

        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        newPackageTour.setId(id);
        newPackageTour.setTitle(title);

        // Saving to firebase
        progressDialog.show();
        myRef.child(newPackageTour.getId())
                .setValue(newPackageTour).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        edtTourTitle = findViewById(R.id.edt_name);

        View price = findViewById(R.id.price);
        TextView tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_for_package);
        btnAddPrice.setText(R.string.add_price);

        View itinerary = findViewById(R.id.itinerary);
        TextView tvItineraryTitle = itinerary.findViewById(R.id.tv_title);
        layoutItinerary = itinerary.findViewById(R.id.layout);
        btnAddItinerary = itinerary.findViewById(R.id.btn_add);

        tvItineraryTitle.setText(R.string.itinerary);
        btnAddItinerary.setText(R.string.add_itinerary);

        View packageInclude = findViewById(R.id.package_include);
        TextView tvPackageIncludeTitle = packageInclude.findViewById(R.id.tv_title);
        layoutPackageInclude = packageInclude.findViewById(R.id.layout);
        btnAddPackageInclude = packageInclude.findViewById(R.id.btn_add);

        tvPackageIncludeTitle.setText(R.string.package_include);
        btnAddPackageInclude.setText(R.string.add_include);

        View packageNotInclude = findViewById(R.id.package_not_include);
        TextView tvPackageNotIncludeTitle = packageNotInclude.findViewById(R.id.tv_title);
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
                goToTitleAndDescriptionActivity(getString(R.string.add_price),
                        true,
                        REQUEST_CODE_PRICE);
            }
        });

        btnAddItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_itinerary),
                        true,
                        REQUEST_CODE_ITINERARY);
            }
        });

        btnAddPackageInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_include),
                        false,
                        REQUEST_CODE_PACKAGE_INCLUDE);
            }
        });

        btnAddPackageNotInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_not_include),
                        false,
                        REQUEST_CODE_PACKAGE_NOT_INCLUDE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewPackageTourActivity.this, REQUEST_CODE_IMAGE_PICKER);
            }
        });
    }
}
