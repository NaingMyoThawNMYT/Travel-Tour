package com.schoolproject.traveltour.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.schoolproject.traveltour.R;

public abstract class BaseNewTourActivity extends BaseSecondActivity {
    public int padding;

    public DatabaseReference myRef;
    public ProgressDialog progressDialog;
    public TextInputEditText edtTourTitle, edtLat, edtLng;

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

    public void goToTitleAndDescriptionActivity(String title, boolean showTitle, int requestCode) {
        Intent intent = new Intent(this, TitleAndDescriptionActivity.class);
        intent.putExtra(TitleAndDescriptionActivity.PARAM_ACTIVITY_TITLE, title);
        intent.putExtra(TitleAndDescriptionActivity.PARAM_SHOW_TITLE, showTitle);
        startActivityForResult(intent, requestCode);
    }

    public void showErrorToast() {
        Toast.makeText(this, "Please choose country first!", Toast.LENGTH_SHORT).show();
    }

    public void showFailToSaveToast() {
        Toast.makeText(this,
                "Fail to save! Try again!",
                Toast.LENGTH_SHORT).show();
    }

    abstract void saveNewTour();
}
