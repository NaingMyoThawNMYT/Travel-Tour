package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Country;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.ImageChooserUtil;

public class CountryEditActivity extends BaseSecondActivity {
    public static final String PARAM_COUNTRY = "PARAM_COUNTRY";

    private Country country;

    private DatabaseReference myRef;

    private EditText edtCountryName;
    private ImageView img;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_edit);

        setHomeBackButtonAndToolbarTitle(getString(R.string.country_editor));

        findViewById(R.id.extra_images).setVisibility(View.GONE);

        edtCountryName = findViewById(R.id.edt_country_name);
        img = findViewById(R.id.img_background);

        findViewById(R.id.location).setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        Bundle b = getIntent().getExtras();
        if (b == null || !b.containsKey(PARAM_COUNTRY)) {
            country = new Country();
        } else {
            country = (Country) b.get(PARAM_COUNTRY);

            if (country != null) {
                img.setImageBitmap(BitmapUtil.base64StringToBitmap(country.getImageBase64()));
                edtCountryName.setText(country.getName());
            }
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(CountryEditActivity.this,
                        Constants.REQUEST_CODE_IMAGE_PICKER);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_COUNTRY);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveCountry();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                data != null &&
                requestCode == Constants.REQUEST_CODE_IMAGE_PICKER) {
            Bitmap bm = BitmapUtil.resize(ImageChooserUtil.getBitmapFromIntent(
                    this,
                    data));
            img.setImageBitmap(bm);
            country.setImageBase64(BitmapUtil.bitmapToBase64String(bm));
        }
    }

    private void saveCountry() {
        String countryName = edtCountryName.getText().toString().trim();
        if (TextUtils.isEmpty(countryName)) {
            Toast.makeText(this, "Enter country name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(country.getImageBase64())) {
            Toast.makeText(this, "Please choose photo!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(country.getId())) {
            final String id = myRef.push().getKey();
            if (TextUtils.isEmpty(id)) {
                showFailToSaveToast();
                return;
            }
            country.setId(id);
        }

        country.setName(countryName);

        progressDialog.show();
        myRef.child(country.getId())
                .setValue(country).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to save! Try again!",
                Toast.LENGTH_SHORT).show();
    }
}
