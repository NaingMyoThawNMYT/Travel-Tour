package com.schoolproject.traveltour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.schoolproject.traveltour.R;

public class TitleAndDescriptionActivity extends BaseSecondActivity {
    public static final String ACTIVITY_TITLE = "activity_title";
    public static final String EDIT_TEXT_TITLE = "title";
    public static final String EDIT_TEXT_DESCRIPTION = "description";
    private EditText title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_and_description);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ACTIVITY_TITLE)) {
            setHomeBackButtonAndToolbarTitle(bundle.getString(ACTIVITY_TITLE));
        }

        title = findViewById(R.id.edit_title);
        description = findViewById(R.id.edit_description);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EDIT_TEXT_TITLE, title.getText().toString());
        intent.putExtra(EDIT_TEXT_DESCRIPTION, description.getText().toString());
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}
