package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.enums.Country;
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
    private SightSeeingTour sightSeeingTour;

    private LinearLayout layoutItinerary, layoutNote, layoutPrice, layoutServices, layoutExcludes, layoutThingsToNote;
    private Button btnAddItinerary, btnAddNote, btnAddPrice, btnAddServices, btnAddExcludes, btnAddThingsToNote;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sight_seeing_tour);

        Bundle b = getIntent().getExtras();
        Country country = DataSet.getCountryParam(b);
        if (country == null) {
            showErrorToast();
            finish();
            return;
        }

        setHomeBackButtonAndToolbarTitle(getString(R.string.add_sightseeing_tour));

        sightSeeingTour = new SightSeeingTour();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constants.TABLE_NAME_TOUR);

        initUI();
        initListener();
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
                    if (sightSeeingTour.getItinerary() == null) {
                        sightSeeingTour.setItinerary(new ArrayList<String>());
                    }
                    sightSeeingTour.getItinerary().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutItinerary,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getItinerary().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_NOTE: {
                    if (sightSeeingTour.getNoteList() == null) {
                        sightSeeingTour.setNoteList(new ArrayList<String>());
                    }
                    sightSeeingTour.getNoteList().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutNote,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getNoteList().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_PRICE: {
                    if (sightSeeingTour.getPrice() == null) {
                        sightSeeingTour.setPrice(new ArrayList<TitleAndDescription>());
                    }
                    final TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    sightSeeingTour.getPrice().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getPrice().remove(titleAndDescription);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_SERVICES: {
                    if (sightSeeingTour.getInclude() == null) {
                        sightSeeingTour.setInclude(new ArrayList<String>());
                    }
                    sightSeeingTour.getInclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutServices,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getInclude().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_EXCLUDES: {
                    if (sightSeeingTour.getExclude() == null) {
                        sightSeeingTour.setExclude(new ArrayList<String>());
                    }
                    sightSeeingTour.getExclude().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutExcludes,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getExclude().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_THINGS_TO_NOTE: {
                    if (sightSeeingTour.getThingsToNote() == null) {
                        sightSeeingTour.setThingsToNote(new ArrayList<String>());
                    }
                    sightSeeingTour.getThingsToNote().add(description);
                    DataSet.setUpStringValuesInParent(this,
                            layoutThingsToNote,
                            description,
                            padding,
                            0,
                            new DataSet.OnClearClickListener() {
                                @Override
                                public void onClear() {
                                    sightSeeingTour.getThingsToNote().remove(description);
                                }
                            });
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER: {
                    Bitmap bm = ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data);
                    imageView.setImageBitmap(bm);
                    sightSeeingTour.setBase64ImageStr(BitmapUtil.bitmapToBase64String(bm));
                    break;
                }
            }
        }
    }

    @Override
    void saveNewTour() {
        final String title = UiUtil.getString(edtTourTitle);
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Enter title", Toast.LENGTH_SHORT).show();
            edtTourTitle.requestFocus();
            return;
        }

        final String id = myRef.push().getKey();
        if (TextUtils.isEmpty(id)) {
            showFailToSaveToast();
            return;
        }

        sightSeeingTour.setId(id);
        sightSeeingTour.setCountry(DataSet.selectedCountry);
        sightSeeingTour.setType(TourType.SIGHTSEEING_TOUR.getCode());
        sightSeeingTour.setTitle(title);

        // Saving to firebase
        progressDialog.show();
        myRef.child(sightSeeingTour.getId())
                .setValue(sightSeeingTour).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }
}
