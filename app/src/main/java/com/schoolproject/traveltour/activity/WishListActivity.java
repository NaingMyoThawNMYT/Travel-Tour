package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.OptionalTour;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.utils.DataSet;

public class WishListActivity extends BaseSecondActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setHomeBackButtonAndToolbarTitle(getString(R.string.wishlist));

        RecyclerView rv = findViewById(R.id.rv);
        MenuAdapter menuAdapter = new MenuAdapter(DataSet.getBookmarkedTours(),
                new MenuAdapter.MenuClickListener() {
                    @Override
                    public void onClick(Menu menu) {
                        TourListActivity.selectedTour = menu;

                        Class detailsClass;
                        if (menu instanceof OptionalTour) {
                            detailsClass = OptionalTourActivity.class;
                        } else if (menu instanceof SightSeeingTour) {
                            detailsClass = SightseeingTourActivity.class;
                        } else {
                            detailsClass = PackageTourActivity.class;
                        }
                        startActivity(new Intent(WishListActivity.this, detailsClass));
                    }

                    @Override
                    public void onLongClick(Menu menu) {
                    }
                });
        rv.setAdapter(menuAdapter);
    }
}
