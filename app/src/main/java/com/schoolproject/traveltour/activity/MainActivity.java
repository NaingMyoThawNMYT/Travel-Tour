package com.schoolproject.traveltour.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

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

    private void initUI() {
        rv = findViewById(R.id.main_rv);
        menuAdapter = new MenuAdapter(this, DataSet.getMenuList(), new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                Toast.makeText(MainActivity.this, menu.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(menuAdapter);
    }
}