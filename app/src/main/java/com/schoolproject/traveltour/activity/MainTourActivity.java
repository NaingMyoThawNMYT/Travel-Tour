package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.schoolproject.traveltour.R;

public abstract class MainTourActivity extends BaseSecondActivity {
    boolean bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        if (!SignInActivity.isAdmin) {
            Button btnBookNow = findViewById(R.id.btn_book_now);
            btnBookNow.setVisibility(View.VISIBLE);
            btnBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToBookingActivity();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wishlist) {
            bookmark = !bookmark;

            item.setIcon(getResources().getDrawable(bookmark
                    ? R.drawable.ic_bookmark_white_24dp
                    : R.drawable.ic_bookmark_border_white_24dp));

            Toast.makeText(this,
                    bookmark
                            ? "Added to wishlist"
                            : "Removed from wishlist",
                    Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public com.schoolproject.traveltour.model.Menu getParamTourOrFinishActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey(TourListActivity.PARAM_TOUR)) {
            showErrorToastAndFinishActivity();
            return null;
        }

        com.schoolproject.traveltour.model.Menu tour =
                (com.schoolproject.traveltour.model.Menu) bundle.get(TourListActivity.PARAM_TOUR);
        if (tour == null) {
            showErrorToastAndFinishActivity();
            return null;
        }

        return tour;
    }

    private void showErrorToastAndFinishActivity() {
        Toast.makeText(this, "Nothing to show!", Toast.LENGTH_SHORT).show();
        finish();
    }

    abstract void goToBookingActivity();
}
