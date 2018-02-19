package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_IMAGE_URL = "image";
    private static final String SANDWICH_ORIGIN = "origin";
    private static final String SANDWICH_AKA = "also_known_as";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_INGREDIENTS = "ingredients";

    private String mImageUrl;
    private TextView mOriginTv;
    private TextView mAlsoKnownTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView sandwichIv = findViewById(R.id.image_iv);
        mOriginTv = findViewById(R.id.origin_tv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);

        // If the saveInstanceState bundle is not NULL, populate all fields in the layout
        // accordingly with the saved data.
        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getString(SANDWICH_NAME));
            mImageUrl = savedInstanceState.getString(SANDWICH_IMAGE_URL);
            Picasso.with(this)
                    .load(mImageUrl)
                    .into(sandwichIv);
            mOriginTv.setText(savedInstanceState.getString(SANDWICH_ORIGIN));
            mAlsoKnownTv.setText(savedInstanceState.getString(SANDWICH_AKA));
            mDescriptionTv.setText(savedInstanceState.getString(SANDWICH_DESCRIPTION));
            mIngredientsTv.setText(savedInstanceState.getString(SANDWICH_INGREDIENTS));
        } else {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];

            try {
                Sandwich sandwich = JsonUtils.parseSandwichJson(json);
                if (sandwich == null) {
                    // Sandwich data unavailable
                    closeOnError();
                    return;
                }

                populateUI(sandwich);
                mImageUrl = sandwich.getImage();
                Picasso.with(this)
                        .load(mImageUrl)
                        .into(sandwichIv);

                setTitle(sandwich.getMainName());
            } catch (JSONException e) {
                Log.e("DetailActivity.java", "Error parsing Sandwich JSON: ", e);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
        mAlsoKnownTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        mDescriptionTv.setText(sandwich.getDescription());
        mIngredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put the contents of the Views into the outState Bundle.
        outState.putString(SANDWICH_NAME, getTitle().toString());
        outState.putString(SANDWICH_IMAGE_URL, mImageUrl);
        outState.putString(SANDWICH_ORIGIN, mOriginTv.getText().toString());
        outState.putString(SANDWICH_AKA, mAlsoKnownTv.getText().toString());
        outState.putString(SANDWICH_DESCRIPTION, mDescriptionTv.getText().toString());
        outState.putString(SANDWICH_INGREDIENTS, mIngredientsTv.getText().toString());
    }
}
