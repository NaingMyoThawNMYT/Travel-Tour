package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.DataSet;

public class WishListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        RecyclerView rv = findViewById(R.id.rv);
        MenuAdapter menuAdapter = new MenuAdapter(this,
                DataSet.getBookmarkedTours(),
                new MenuAdapter.MenuClickListener() {
                    @Override
                    public void onClick(Menu menu) {
                        Class detailsClass;
                        if (menu instanceof OptionalTour) {
                            detailsClass = OptionalTourActivity.class;
                        } else if (menu instanceof SightSeeingTour) {
                            detailsClass = SightseeingTourActivity.class;
                        } else {
                            detailsClass = PackageTourActivity.class;
                        }
                        goToTourDetails(detailsClass, menu);
                    }
                });
        rv.setAdapter(menuAdapter);
    }

    private void goToTourDetails(Class detailsClass, Menu tour) {
        Intent i = new Intent(this, detailsClass);
        i.putExtra(TourListActivity.PARAM_TOUR, tour);
        startActivity(i);
    }
}
