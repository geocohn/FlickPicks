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
    private String mId;
    private String mPosterPath;
    private String mOriginalTitle;
    private String mOverView;
    private String mPopularity;
    private String mVoteAverage;

    public Movie(String backdropPath,
                 String id,
                 String originalTitle,
                 String overView,
                 String popularity,
                 String posterPath,
                 String voteAverage) {
        mBackdropPath = backdropPath;
        mId = id;
        mOriginalTitle = originalTitle;
        mOverView = overView;
        mPopularity = popularity;
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
    }

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

    public String getId() {
        return mId;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        mBackdropPath = jsonObject.getString("backdrop_path");
        mId = jsonObject.getString("id");
        mOriginalTitle = jsonObject.getString("original_title");
        mOverView = jsonObject.getString("overview");
        mPopularity = jsonObject.getString("popularity");
        mPosterPath = jsonObject.getString("poster_path");
        mVoteAverage = jsonObject.getString("vote_average");
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
