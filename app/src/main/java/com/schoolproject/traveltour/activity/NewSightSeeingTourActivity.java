package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.schoolproject.traveltour.R;
import com.schoolproject.traveltour.model.SightSeeingTour;
import com.schoolproject.traveltour.model.TitleAndDescription;
import com.schoolproject.traveltour.utils.Constants;
import com.schoolproject.traveltour.utils.DataSet;
import com.schoolproject.traveltour.utils.ImageChooserUtil;

import java.util.ArrayList;

public class NewSightSeeingTourActivity extends BaseNewTourActivity {
    private SightSeeingTour sightSeeingTour;

    private TextView tvItineraryTitle, tvNoteTitle, tvPriceTitle, tvServicesTitle, tvExcludesTitle, tvThingsToNoteTitle;
    private LinearLayout layoutItinerary, layoutNote, layoutPrice, layoutServices, layoutExcludes, layoutThingsToNote;
    private Button btnAddItinerary, btnAddNote, btnAddPrice, btnAddServices, btnAddExcludes, btnAddThingsToNote;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sight_seeing_tour);

        sightSeeingTour = new SightSeeingTour();

        initUI();
        initListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_TITLE);
            String description = data.getStringExtra(TitleAndDescriptionActivity.EDIT_TEXT_DESCRIPTION);

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
                            0);
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
                            0);
                    break;
                }
                case Constants.REQUEST_CODE_PRICE: {
                    if (sightSeeingTour.getPrice() == null) {
                        sightSeeingTour.setPrice(new ArrayList<TitleAndDescription>());
                    }
                    TitleAndDescription titleAndDescription = new TitleAndDescription(title, description);
                    sightSeeingTour.getPrice().add(titleAndDescription);
                    DataSet.setUpTitleAndDescriptionValuesInParent(this,
                            layoutPrice,
                            titleAndDescription,
                            padding);
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
                            0);
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
                            0);
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
                            0);
                    break;
                }
                case Constants.REQUEST_CODE_IMAGE_PICKER: {
                    imageView.setImageBitmap(ImageChooserUtil.getBitmapFromIntent(
                            this,
                            data));
                    break;
                }
            }
        }
    }

    @Override
    void saveNewTour() {
        // TODO: 17-Mar-20 save to firebase
    }

    private void initUI() {
        View itinerary = findViewById(R.id.itinerary);
        tvItineraryTitle = itinerary.findViewById(R.id.tv_title);
        layoutItinerary = itinerary.findViewById(R.id.layout);
        btnAddItinerary = itinerary.findViewById(R.id.btn_add);

        tvItineraryTitle.setText(R.string.itinerary);
        btnAddItinerary.setText(R.string.add_itinerary);

        View note = findViewById(R.id.note);
        tvNoteTitle = note.findViewById(R.id.tv_title);
        layoutNote = note.findViewById(R.id.layout);
        btnAddNote = note.findViewById(R.id.btn_add);

        tvNoteTitle.setText(R.string.note);
        btnAddNote.setText(R.string.add_note);

        View price = findViewById(R.id.price);
        tvPriceTitle = price.findViewById(R.id.tv_title);
        layoutPrice = price.findViewById(R.id.layout);
        btnAddPrice = price.findViewById(R.id.btn_add);

        tvPriceTitle.setText(R.string.price_of_tour);
        btnAddPrice.setText(R.string.add_price);

        View services = findViewById(R.id.services);
        tvServicesTitle = services.findViewById(R.id.tv_title);
        layoutServices = services.findViewById(R.id.layout);
        btnAddServices = services.findViewById(R.id.btn_add);

        tvServicesTitle.setText(R.string.our_services_include);
        btnAddServices.setText(R.string.add_service);

        View excludes = findViewById(R.id.excludes);
        tvExcludesTitle = excludes.findViewById(R.id.tv_title);
        layoutExcludes = excludes.findViewById(R.id.layout);
        btnAddExcludes = excludes.findViewById(R.id.btn_add);

        tvExcludesTitle.setText(R.string.our_services_include);
        btnAddExcludes.setText(R.string.add_service);

        View thingsToNote = findViewById(R.id.things_to_note);
        tvThingsToNoteTitle = thingsToNote.findViewById(R.id.tv_title);
        layoutThingsToNote = thingsToNote.findViewById(R.id.layout);
        btnAddThingsToNote = thingsToNote.findViewById(R.id.btn_add);

        tvThingsToNoteTitle.setText(R.string.things_to_note);
        btnAddThingsToNote.setText(R.string.add_note);

        imageView = findViewById(R.id.img_background);
    }

    private void initListener() {
        btnAddItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_itinerary), Constants.REQUEST_CODE_ITINERARY);
            }
        });

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_note), Constants.REQUEST_CODE_NOTE);
            }
        });

        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_price), Constants.REQUEST_CODE_PRICE);
            }
        });

        btnAddServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_service), Constants.REQUEST_CODE_SERVICES);
            }
        });

        btnAddExcludes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.package_excludes), Constants.REQUEST_CODE_EXCLUDES);
            }
        });

        btnAddThingsToNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTitleAndDescriptionActivity(getString(R.string.add_note), Constants.REQUEST_CODE_THINGS_TO_NOTE);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooserUtil.showImageChooser(NewSightSeeingTourActivity.this, Constants.REQUEST_CODE_IMAGE_PICKER);
            }
        });
    }
}
