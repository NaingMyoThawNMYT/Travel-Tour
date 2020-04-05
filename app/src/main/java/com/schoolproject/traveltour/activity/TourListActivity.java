package com.schoolproject.traveltour.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.factory.TourFactory;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourListActivity extends AppCompatActivity {
    public static final String PARAM_TOUR = "param_tour";

    private AppCompatSpinner tourListSpinner;
    private MenuAdapter menuAdapter;
    private ProgressDialog progressDialog;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        Country country = DataSet.getCountryParam(b);
        if (country == null) {
            Toast.makeText(this, "There is no selected country!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DataSet.selectedCountry = country.getCode();

        initUI();
        initListener();
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
            actionBar.setTitle("Tour List");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }
        tourListSpinner = findViewById(R.id.tour_spinner);
        edtSearch = findViewById(R.id.edt_search);
        RecyclerView recyclerView = findViewById(R.id.main_rv);

        findViewById(R.id.tour_spinner_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.edt_search).setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DataSet.TOUR_LIST);
        tourListSpinner.setAdapter(adapter);

        menuAdapter = new MenuAdapter(this, new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                Class detailsClass;
                if (tourListSpinner.getSelectedItemPosition() == 1) {
                    detailsClass = OptionalTourActivity.class;
                } else if (tourListSpinner.getSelectedItemPosition() == 2) {
                    detailsClass = SightseeingTourActivity.class;
                } else {
                    detailsClass = PackageTourActivity.class;
                }
                goToTourDetails(detailsClass, menu);
            }

            @Override
            public void onLongClick(Menu menu) {
                if (DataSet.isAdmin) {
                    showDeleteConfirmDialog(TourListActivity.this, menu);
                }
            }
        });
        recyclerView.setAdapter(menuAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void initListener() {
        tourListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshTourList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                menuAdapter.getFilter().filter(s);
            }
        });
    }

    private void goToTourDetails(Class detailsClass, Menu tour) {
        Intent i = new Intent(TourListActivity.this, detailsClass);
        i.putExtra(PARAM_TOUR, tour);
        startActivity(i);
    }

    private void refreshTourList(int position) {
        List<Menu> tours = new ArrayList<>();
        if (DataSet.tourDataSet != null && !DataSet.tourDataSet.isEmpty()) {
            TourType type = null;
            switch (position) {
                case 0: {
                    type = TourType.PACKAGE_TOUR;
                    break;
                }
                case 1: {
                    type = TourType.OPTIONAL_TOUR;
                    break;
                }
                case 2: {
                    type = TourType.SIGHTSEEING_TOUR;
                    break;
                }
            }

            if (type != null) {
                for (Map<String, Object> map : DataSet.tourDataSet) {
                    if (type.getCode().equals(map.get("type")) &&
                            DataSet.selectedCountry.equals(map.get("country"))) {
                        Menu tour = TourFactory.createNewTour(type);
                        tour.parse(map);
                        tours.add(tour);
                    }
                }
            }
        }

        menuAdapter.setDataSet(tours);
    }

    private void showDeleteConfirmDialog(Context context, final Menu menu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?")
                .setMessage("Delete " + menu.getTitle())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMenu(menu);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void deleteMenu(Menu menu) {
        progressDialog.show();
        FirebaseDatabase.getInstance()
                .getReference(Constants.TABLE_NAME_TOUR)
                .child(menu.getId())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(TourListActivity.this,
                                    "Deleted successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TourListActivity.this,
                                    "Failed to delete! Try again!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
