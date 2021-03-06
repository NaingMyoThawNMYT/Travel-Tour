package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.CountryArrayAdapter;
import com.schoolproject.traveltour.model.Country;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;

import java.util.List;

public abstract class BaseNewTourActivity extends BaseSecondActivity {
    public static final int REQUEST_CODE = 1;
    public static final String PARAM_TOUR = "PARAM_TOUR";

    public int padding;
    public String selectedCountryId;

    public DatabaseReference myRef;
    public ProgressDialog progressDialog;
    public TextInputEditText edtTourTitle;
    public TextView tvAddMap;
    public AppCompatSpinner spnCountry;
    public ImageView imageView, img1, img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        padding = (int) getResources().getDimension(R.dimen.medium_padding);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveNewTour();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE: {
                    if (data.getExtras() == null) {
                        break;
                    }
                    checkLocationPicker();
                    onLocationMapSelected(
                            (LatLng) data.getExtras().get(MapsActivity.RESULT_LAT_LNG));
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER_1: {
                    handleImagePick(data, img1);
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER_2: {
                    handleImagePick(data, img2);
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER_3: {
                    handleImagePick(data, img3);
                    break;
                }
            }

        }
    }

    private void handleImagePick(Intent data, ImageView img) {
        Bitmap bm = BitmapUtil.resize(ImageChooserUtil.getBitmapFromIntent(
                this,
                data));
        img.setImageBitmap(bm);
        addImageBase64(BitmapUtil.bitmapToBase64String(bm));
    }

    public void goToTitleAndDescriptionActivity(String title, boolean showTitle, int requestCode) {
        Intent intent = new Intent(this, TitleAndDescriptionActivity.class);
        intent.putExtra(TitleAndDescriptionActivity.PARAM_ACTIVITY_TITLE, title);
        intent.putExtra(TitleAndDescriptionActivity.PARAM_SHOW_TITLE, showTitle);
        startActivityForResult(intent, requestCode);
    }

    public void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to save! Try again!",
                Toast.LENGTH_SHORT).show();
    }

    public void initSpinnerListener() {
        ArrayAdapter<Country> adapter = new CountryArrayAdapter(this, DataSet.countries);
        spnCountry.setAdapter(adapter);
        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountryId = DataSet.countries.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initImagePickers() {
        img1 = findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(BaseNewTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER_1);
            }
        });

        img2 = findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(BaseNewTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER_2);
            }
        });

        img3 = findViewById(R.id.img3);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(BaseNewTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER_3);
            }
        });
    }

    void checkLocationPicker() {
        tvAddMap.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_yellow_24dp,
                0,
                R.drawable.ic_check_green_24dp,
                0);
    }

    void setupImageViews(List<String> imagesBase64) {
        if (imagesBase64 == null || imagesBase64.isEmpty()) {
            return;
        }

        displayImage(imagesBase64.get(0), R.id.img_background);

        if (imagesBase64.size() > 1) {
            displayImage(imagesBase64.get(1), R.id.img1);
        }

        if (imagesBase64.size() > 2) {
            displayImage(imagesBase64.get(2), R.id.img2);
        }

        if (imagesBase64.size() > 3) {
            displayImage(imagesBase64.get(3), R.id.img3);
        }
    }

    void displayImage(String imageBase64, int imageViewId) {
        ImageView img = findViewById(imageViewId);
        img.setImageBitmap(BitmapUtil.base64StringToBitmap(imageBase64));
    }

    void setSelectedCountry(String countryId) {
        spnCountry.setSelection(DataSet.getCountryIndex(countryId));
    }

    abstract void addImageBase64(String string);

    abstract void onLocationMapSelected(LatLng latLng);

    abstract void saveNewTour();
}
