package com.schoolproject.traveltour.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.adapter.MenuAdapter;
import com.schoolproject.traveltour.enums.Country;
import com.schoolproject.traveltour.model.Menu;
import com.schoolproject.traveltour.model.WishList;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String PARAM_COUNTRY = "param_country";

    private Country selectedCountry;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(Constants.TABLE_NAME_WISH_LIST)
                .child(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        DataSet.wishLists = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            WishList wishList = new WishList();
                            wishList.parse((Map<String, Object>) snapshot.getValue());
                            DataSet.wishLists.add(wishList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
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

        if (SignInActivity.isAdmin) {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCountryChooserDialog();
                }
            });
        }

        RecyclerView rv = findViewById(R.id.main_rv);
        MenuAdapter menuAdapter = new MenuAdapter(this, DataSet.getMenuList(), new MenuAdapter.MenuClickListener() {
            @Override
            public void onClick(Menu menu) {
                Intent i = new Intent(MainActivity.this, TourListActivity.class);

                Country country;
                if (menu.getTitle().equals(DataSet.getMenuList().get(0).getTitle())) {
                    country = Country.MALDIVES;
                } else if (menu.getTitle().equals(DataSet.getMenuList().get(1).getTitle())) {
                    country = Country.VIETNAM;
                } else if (menu.getTitle().equals(DataSet.getMenuList().get(2).getTitle())) {
                    country = Country.MYANMAR;
                } else {
                    country = Country.HONG_KONG;
                }

                i.putExtra(PARAM_COUNTRY, country);
                startActivity(i);
            }
        });
        rv.setAdapter(menuAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void showCountryChooserDialog() {
        selectedCountry = null;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_country_chooser);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        RadioGroup rdgCountry = dialog.findViewById(R.id.rdg_country);
        rdgCountry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdb_maldives) {
                    selectedCountry = Country.MALDIVES;
                } else if (checkedId == R.id.rdb_vietnam) {
                    selectedCountry = Country.VIETNAM;
                } else if (checkedId == R.id.rdb_myanmar) {
                    selectedCountry = Country.MYANMAR;
                } else if (checkedId == R.id.rdb_hong_kong) {
                    selectedCountry = Country.HONG_KONG;
                }

                showTourTypeChooserDialog();

                dialog.dismiss();
            }
        });

        dialog.show();
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

                Intent i = new Intent(MainActivity.this, secondClass);
                i.putExtra(PARAM_COUNTRY, selectedCountry);
                startActivity(i);
            }
        });

        dialog.show();
    }
}
