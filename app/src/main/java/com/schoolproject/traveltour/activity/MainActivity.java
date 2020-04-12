package com.schoolproject.traveltour.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.CountryRvAdapter;
import com.schoolproject.traveltour.model.Country;
import com.schoolproject.traveltour.model.WishList;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private ValueEventListener tableCountryEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataSet.countries = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                DataSet.countries.add(Country.parse((Map<String, Object>) snapshot.getValue()));
            }

            adapter.setDataSet(DataSet.countries);

            fetchWishList();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };
    private ValueEventListener tableWishListEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DataSet.wishLists = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                DataSet.wishLists.add(WishList.parse((Map<String, Object>) snapshot.getValue()));
            }

            fetchTourList();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };
    private ValueEventListener tableTourEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            progressDialog.dismiss();

            DataSet.tourDataSet = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                DataSet.tourDataSet.add((Map<String, Object>) snapshot.getValue());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
        }
    };

    private ProgressDialog progressDialog;
    private CountryRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        database = FirebaseDatabase.getInstance();

        fetchCountryList();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (DataSet.isAdmin) {
            menu.findItem(R.id.action_invoice).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_wishlist: {
                startActivity(new Intent(this, WishListActivity.class));
                break;
            }
            case R.id.action_invoice: {
                startActivity(new Intent(this, InvoicedActivity.class));
                break;
            }
        }
        return true;
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Popular Trip");
        }

        if (DataSet.isAdmin) {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTourTypeChooserDialog();
                }
            });

            FloatingActionButton fabCountry = findViewById(R.id.fab_country);
            fabCountry.setVisibility(View.VISIBLE);
            fabCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CountryEditActivity.class));
                }
            });
        }

        adapter = new CountryRvAdapter(new CountryRvAdapter.onListItemClickListener() {
            @Override
            public void onClick(Country country) {
                Intent i = new Intent(MainActivity.this, TourListActivity.class);
                i.putExtra(TourListActivity.PARAM_COUNTRY_ID, country.getId());
                startActivity(i);
            }

            @Override
            public void onLongClick(Country country) {
                if (DataSet.isAdmin) {
                    if (DataSet.hasChild(country.getId())) {
                        Toast.makeText(MainActivity.this,
                                "Please delete " + country.getName() + "'s tours first!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        showDeleteConfirmDialog(country);
                    }
                }
            }
        });

        RecyclerView rv = findViewById(R.id.main_rv);
        rv.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void showTourTypeChooserDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tour_type_chooser);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        RadioGroup rdgCountry = dialog.findViewById(R.id.rdg_tour_type);
        rdgCountry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Class secondClass;
                if (checkedId == R.id.rdb_package_tour) {
                    secondClass = NewPackageTourActivity.class;
                } else if (checkedId == R.id.rdb_optional_tour) {
                    secondClass = NewOptionalTourActivity.class;
                } else if (checkedId == R.id.rdb_sightseeing_tour) {
                    secondClass = NewSightseeingTourActivity.class;
                } else {
                    return;
                }

                dialog.dismiss();

                startActivity(new Intent(MainActivity.this, secondClass));
            }
        });

        dialog.show();
    }

    private void fetchCountryList() {
        progressDialog.show();
        database.getReference(Constants.TABLE_NAME_COUNTRY)
                .addValueEventListener(tableCountryEventListener);
    }

    private void fetchWishList() {
        database.getReference(Constants.TABLE_NAME_WISH_LIST)
                .child(currentUser.getUid())
                .addValueEventListener(tableWishListEventListener);
    }

    private void fetchTourList() {
        database.getReference(Constants.TABLE_NAME_TOUR)
                .addValueEventListener(tableTourEventListener);
    }

    private void showDeleteConfirmDialog(final Country country) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?")
                .setMessage("Delete " + country.getName())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCountry(country);
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

    private void deleteCountry(Country country) {
        progressDialog.show();
        database.getReference(Constants.TABLE_NAME_COUNTRY)
                .child(country.getId())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Deleted successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Failed to delete! Try again!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
