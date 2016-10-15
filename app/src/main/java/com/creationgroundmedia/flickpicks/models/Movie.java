package com.creationgroundmedia.flickpicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by geo on 10/12/16.
 */

public class Movie {

    private String mBackdropPath;
    private String mPosterPath;
    private String mOriginalTitle;
    private String mOverView;

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280%s", mBackdropPath);
    }

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
        mBackdropPath = jsonObject.getString("backdrop_path");
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
