package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.DataSet;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_wishlist) {
            startActivity(new Intent(this, WishlistActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Popular Trip");
        }

        rv = findViewById(R.id.main_rv);
        menuAdapter = new MenuAdapter(this, DataSet.getMenuList(), new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                startActivity(new Intent(MainActivity.this, TourListActivity.class));
            }
        });
        rv.setAdapter(menuAdapter);
    }
}