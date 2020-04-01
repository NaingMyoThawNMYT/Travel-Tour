package com.schoolproject.traveltour.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.List;

public class WishListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        List<Menu> tours = DataSet.getBookmarkedTours();

        Toast.makeText(this, tours.size() + "", Toast.LENGTH_SHORT).show();
    }
}
