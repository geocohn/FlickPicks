package com.creationgroundmedia.flickpicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by geo on 10/12/16.
 */

public class Movie {

    String mPosterPath;
    String mOriginalTitle;
    String mOverView;

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverView() {
        return mOverView;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", mPosterPath);
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        mPosterPath = jsonObject.getString("poster_path");
        mOriginalTitle = jsonObject.getString("original_title");
        mOverView = jsonObject.getString("overview");
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Movie(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
