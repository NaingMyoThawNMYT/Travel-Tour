package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.schoolproject.traveltour.R;

public class BaseNewTourActivity extends BaseSecondActivity {
    public int padding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        padding = (int) getResources().getDimension(R.dimen.medium_padding);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToTitleAndDescriptionActivity(String title, int requestCode) {
        Intent intent = new Intent(this, TitleAndDescriptionActivity.class);
        intent.putExtra(TitleAndDescriptionActivity.ACTIVITY_TITLE, title);
        startActivityForResult(intent, requestCode);
    }
}
