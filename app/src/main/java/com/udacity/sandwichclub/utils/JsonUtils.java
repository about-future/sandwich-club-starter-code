package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // Instantiate a JSON object so we can get data.
        JSONObject sandwichJson = new JSONObject(json);
        JSONObject sandwichNameObject = sandwichJson.getJSONObject(NAME);

        JSONArray alsoKnownJsonArray = sandwichNameObject.getJSONArray(AKA);
        // Create an instance of List<String>, populate it with aka names.
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownJsonArray.length(); i++) {
            alsoKnownAs.add(alsoKnownJsonArray.getString(i));
        }

        JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(INGREDIENTS);
        // Create an instance of List<String>, populate it with ingredients.
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(
                sandwichNameObject.getString(MAIN_NAME),
                alsoKnownAs,
                sandwichJson.getString(ORIGIN),
                sandwichJson.getString(DESCRIPTION),
                sandwichJson.getString(IMAGE),
                ingredients
        );
    }
}
