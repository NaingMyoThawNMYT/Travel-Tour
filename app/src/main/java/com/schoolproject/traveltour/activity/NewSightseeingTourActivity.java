package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.TourType;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.BitmapUtil;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;
import com.schoolproject.traveltour.utils.UiUtil;

import java.util.ArrayList;

public class NewSightseeingTourActivity extends BaseNewTourActivity {
    private SightSeeingTour newSightSeeingTour;

    private LinearLayout layoutItinerary, layoutNote, layoutPrice, layoutServices, layoutExcludes, layoutThingsToNote;
    private Button btnAddItinerary, btnAddNote, btnAddPrice, btnAddServices, btnAddExcludes, btnAddThingsToNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sight_seeing_tour);

        setHomeBackButtonAndToolbarTitle(getString(R.string.add_sightseeing_tour));

        Bundle b = getIntent().getExtras();
        if (b != null && b.getBoolean(PARAM_TOUR)) {
            newSightSeeingTour = (SightSeeingTour) TourListActivity.selectedTour;
        } else {
            newSightSeeingTour = new SightSeeingTour();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_TOUR);

        initUI();
        initListener();

        if (!TextUtils.isEmpty(newSightSeeingTour.getId())) {
            fillForms();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            final String title = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_TITLE);
            final String description = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_DESCRIPTION);

            if (requestCode != Constants.REQUEST_CODE_IMAGE_PICKER &&
                    TextUtils.isEmpty(description)) {
                return;
            }

            switch (requestCode) {
                case Constants.REQUEST_CODE_ITINERARY: {
                    if (newSightSeeingTour.getItinerary() == null) {
                        newSightSeeingTour.setItinerary(new ArrayList<String>());
                    }
                    newSightSeeingTour.getItinerary().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutItinerary,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getItinerary().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_NOTE: {
                    if (newSightSeeingTour.getNoteList() == null) {
                        newSightSeeingTour.setNoteList(new ArrayList<String>());
                    }
                    newSightSeeingTour.getNoteList().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutNote,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getNoteList().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_PRICE: {
                    if (newSightSeeingTour.getPrice() == null) {
                        newSightSeeingTour.setPrice(new ArrayList<TitleAndDescription>());
                    }
                    final TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    newSightSeeingTour.getPrice().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getPrice().remove(titleAndDescription);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_SERVICES: {
                    if (newSightSeeingTour.getInclude() == null) {
                        newSightSeeingTour.setInclude(new ArrayList<String>());
                    }
                    newSightSeeingTour.getInclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutServices,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getInclude().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_EXCLUDES: {
                    if (newSightSeeingTour.getExclude() == null) {
                        newSightSeeingTour.setExclude(new ArrayList<String>());
                    }
                    newSightSeeingTour.getExclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutExcludes,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getExclude().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_THINGS_TO_NOTE: {
                    if (newSightSeeingTour.getThingsToNote() == null) {
                        newSightSeeingTour.setThingsToNote(new ArrayList<String>());
                    }
                    newSightSeeingTour.getThingsToNote().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutThingsToNote,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    newSightSeeingTour.getThingsToNote().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER: {
                    Bitmap bm = BitmapUtil.resize(ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data));
                    imageView.setImageBitmap(bm);
                    newSightSeeingTour.addImageBase64(BitmapUtil.bitmapToBase64String(bm));
                    break;
                }
            }
        }
    }

    @Override
    void addImageBase64(String string) {
        newSightSeeingTour.addImageBase64(string);
    }

    @Override
    void onLocationMapSelected(LatLng latLng) {
        newSightSeeingTour.setLatitude(latLng.latitude);
        newSightSeeingTour.setLongitude(latLng.longitude);
    }

    @Override
    void saveNewTour() {
        final String title = UiUtil.getString(edtTourTitle);
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Enter title", Toast.LENGTH_SHORT).show();
            edtTourTitle.requestFocus();
            return;
        }

        if (newSightSeeingTour.getImagesBase64() == null || newSightSeeingTour.getImagesBase64().isEmpty()) {
            Toast.makeText(this, "Please add at least one photo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newSightSeeingTour.getPrice() == null || newSightSeeingTour.getPrice().isEmpty()) {
            Toast.makeText(this, "Please add at least one price", Toast.LENGTH_SHORT).show();
            return;
        }

        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        newSightSeeingTour.setId(id);
        newSightSeeingTour.setCountryId(selectedCountryId);
        newSightSeeingTour.setType(TourType.SIGHTSEEING_TOUR.getCode());
        newSightSeeingTour.setTitle(title);

        // Saving to firebase
        progressDialog.show();
        myRef.child(newSightSeeingTour.getId())
                .setValue(newSightSeeingTour).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                } else {
                    showFailToSaveToast();
                }
            }
        });
    }

    private void initUI() {
        spnCountry = findViewById(R.id.spn_country);

        edtTourTitle = findViewById(R.id.edt_name);

        View itinerary = findViewById(R.id.itinerary);
        TextView tvItineraryTitle = itinerary.findViewById(R.id.tv_title);
        layoutItinerary = itinerary.findViewById(R.id.layout);
        btnAddItinerary = itinerary.findViewById(R.id.btn_add);

        tvItineraryTitle.setText(R.string.itinerary);
        btnAddItinerary.setText(R.string.add_itinerary);

        View note = findViewById(R.id.note);
        TextView tvNoteTitle = note.findViewById(R.id.tv_title);
        layoutNote = note.findViewById(R.id.layout);
        btnAddNote = note.findViewById(R.id.btn_add);

        tvNoteTitle.setText(R.string.note);
        btnAddNote.setText(R.string.add_note);

        View price = findViewById(R.id.price);
        TextView tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_of_tour);
        btnAddPrice.setText(R.string.add_price);

        View services = findViewById(R.id.services);
        TextView tvServicesTitle = services.findViewById(R.id.tv_title);
        layoutServices = services.findViewById(R.id.layout);
        btnAddServices = services.findViewById(R.id.btn_add);

        tvServicesTitle.setText(R.string.our_services_include);
        btnAddServices.setText(R.string.add_service);

        View excludes = findViewById(R.id.excludes);
        TextView tvExcludesTitle = excludes.findViewById(R.id.tv_title);
        layoutExcludes = excludes.findViewById(R.id.layout);
        btnAddExcludes = excludes.findViewById(R.id.btn_add);

        tvExcludesTitle.setText(R.string.package_excludes);
        btnAddExcludes.setText(R.string.add_exclude);

        View thingsToNote = findViewById(R.id.things_to_note);
        TextView tvThingsToNoteTitle = thingsToNote.findViewById(R.id.tv_title);
        layoutThingsToNote = thingsToNote.findViewById(R.id.layout);
        btnAddThingsToNote = thingsToNote.findViewById(R.id.btn_add);

        tvThingsToNoteTitle.setText(R.string.things_to_note);
        btnAddThingsToNote.setText(R.string.add_things_to_note);

        imageView = findViewById(R.id.img_background);

        initAddMapUI();

        initImagePickers();
    }

    private void initAddMapUI() {
        tvAddMap = findViewById(R.id.location);
        tvAddMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewSightseeingTourActivity.this, MapsActivity.class);
                i.putExtra(MapsActivity.PARAM_LAT, newSightSeeingTour.getLatitude());
                i.putExtra(MapsActivity.PARAM_LNG, newSightSeeingTour.getLongitude());
                i.putExtra(MapsActivity.PARAM_SET_LONG_CLICK_LISTENER, true);
                startActivityForResult(
                        i,
                        REQUEST_CODE);
            }
        });
    }

    private void initListener() {
        btnAddItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_itinerary),
                        false,
                        Constants.REQUEST_CODE_ITINERARY);
            }
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_note),
                        false,
                        Constants.REQUEST_CODE_NOTE);
            }
        });

        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_price),
                        true,
                        Constants.REQUEST_CODE_PRICE);
            }
        });

        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_service),
                        false,
                        Constants.REQUEST_CODE_SERVICES);
            }
        });

        btnAddExcludes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_exclude),
                        false,
                        Constants.REQUEST_CODE_EXCLUDES);
            }
        });

        btnAddThingsToNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_things_to_note),
                        false,
                        Constants.REQUEST_CODE_THINGS_TO_NOTE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewSightseeingTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER);
            }
        });

        initSpinnerListener();
    }

    private void fillForms() {
        setSelectedCountry(newSightSeeingTour.getCountryId());

        edtTourTitle.setText(newSightSeeingTour.getTitle());

        setupImageViews(newSightSeeingTour.getImagesBase64());

        if (newSightSeeingTour.getLatitude() != 0 || newSightSeeingTour.getLongitude() != 0) {
            checkLocationPicker();
        }

        if (newSightSeeingTour.getItinerary() != null) {
            for (int i = 0; i < newSightSeeingTour.getItinerary().size(); i++) {
                final String description = newSightSeeingTour.getItinerary().get(i);
                DataSet.setUpStringValuesInParent(this,
                        layoutItinerary,
                        description,
                        padding,
                        0,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getItinerary().remove(description);
                            }
                        });
            }
        }

        if (newSightSeeingTour.getNoteList() != null) {
            for (int i = 0; i < newSightSeeingTour.getNoteList().size(); i++) {
                final String description = newSightSeeingTour.getNoteList().get(i);
                DataSet.setUpStringValuesInParent(this,
                        layoutNote,
                        description,
                        padding,
                        0,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getNoteList().remove(description);
                            }
                        });
            }
        }

        if (newSightSeeingTour.getPrice() != null) {
            for (int i = 0; i < newSightSeeingTour.getPrice().size(); i++) {
                final TitleAndDescription titleAndDescription = newSightSeeingTour.getPrice().get(i);
                DataSet.setUpTitleAndDescriptionValuesInParent(this,
                        layoutPrice,
                        titleAndDescription,
                        padding,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getPrice().remove(titleAndDescription);
                            }
                        });
            }
        }

        if (newSightSeeingTour.getInclude() != null) {
            for (int i = 0; i < newSightSeeingTour.getInclude().size(); i++) {
                final String description = newSightSeeingTour.getInclude().get(i);
                DataSet.setUpStringValuesInParent(this,
                        layoutServices,
                        description,
                        padding,
                        0,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getInclude().remove(description);
                            }
                        });
            }
        }

        if (newSightSeeingTour.getExclude() != null) {
            for (int i = 0; i < newSightSeeingTour.getExclude().size(); i++) {
                final String description = newSightSeeingTour.getExclude().get(i);
                DataSet.setUpStringValuesInParent(this,
                        layoutExcludes,
                        description,
                        padding,
                        0,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getExclude().remove(description);
                            }
                        });
            }
        }

        if (newSightSeeingTour.getThingsToNote() != null) {
            for (int i = 0; i < newSightSeeingTour.getThingsToNote().size(); i++) {
                final String description = newSightSeeingTour.getThingsToNote().get(i);
                DataSet.setUpStringValuesInParent(this,
                        layoutThingsToNote,
                        description,
                        padding,
                        0,
                        new DataSet.OnClearClickListener() {
                            @Override
                            public void onClear() {
                                newSightSeeingTour.getThingsToNote().remove(description);
                            }
                        });
            }
        }
    }
}
