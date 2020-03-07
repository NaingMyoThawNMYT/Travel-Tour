package com.schoolproject.traveltour.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.DataSet;

public class TourListActivity extends AppCompatActivity {
    private AppCompatSpinner tourListSpinner;
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initListener();
    }

    private void initListener() {
        tourListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TourListActivity.this, DataSet.TOUR_LIST[position], Toast.LENGTH_SHORT).show();
                menuAdapter.setDataSet(DataSet.getTourList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Tour List Activity");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }
        tourListSpinner = findViewById(R.id.tour_spinner);
        recyclerView = findViewById(R.id.main_rv);

        findViewById(R.id.tour_spinner_layout).setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DataSet.TOUR_LIST);
        tourListSpinner.setAdapter(adapter);

        menuAdapter = new MenuAdapter(this, DataSet.getTourList(), new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                Toast.makeText(TourListActivity.this, menu.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(menuAdapter);
    }
}
