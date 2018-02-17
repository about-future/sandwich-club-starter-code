package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // Instantiate a JSON object and a Sandwich object so we can get and set data.
        JSONObject sandwichJson = new JSONObject(json);
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichNameObject = sandwichJson.getJSONObject("name");
        //Get the main name of the sandwich from JSON and set it for sandwich object.
        sandwich.setMainName(sandwichNameObject.getString("mainName"));

        JSONArray alsoKnownJsonArray = sandwichNameObject.getJSONArray("alsoKnownAs");
        // Create an instance of List<String>, populate it with aka names and set it
        // in our sandwich object.
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownJsonArray.length(); i++) {
            alsoKnownAs.add(alsoKnownJsonArray.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        //Get the place of origin, description and image url of the sandwich from JSON and
        // set them in the sandwich object.
        sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
        sandwich.setDescription(sandwichJson.getString("description"));
        sandwich.setImage(sandwichJson.getString("image"));

        JSONArray ingredientsJsonArray = sandwichJson.getJSONArray("ingredients");
        // Create an instance of List<String>, populate it with ingredients and set it
        // in our sandwich object.
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.getString(i));
        }
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}
